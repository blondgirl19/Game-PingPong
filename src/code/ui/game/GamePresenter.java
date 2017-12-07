package code.ui.game;

import code.core.Presenter;
import code.data.InMemoryStore;
import code.data.pojo.GameParams;
import code.data.pojo.controllers.PlayerController;
import code.data.pojo.game.Ball;
import code.data.pojo.game.Player;

import static resources.constants.*;

public class GamePresenter extends Presenter<GameContract.IGameView> implements GameContract.IGamePresenter {
    private InMemoryStore inMemoryStore;
    private Thread gameUpdateThread;
    private volatile boolean isPause, isExit;
    private int screenUpdateDelay;
    private int gameState;

    public GamePresenter() {
        gameState = STATE_NOTHING;
        inMemoryStore = new InMemoryStore();
        setupRunnable();
    }

    private void setupRunnable() {
        pauseGame();

        gameUpdateThread = new Thread(() -> {
            while (!isExit){
                checkPause();
                view().updateScreen();

                try {
                    Thread.sleep(screenUpdateDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        gameUpdateThread.start();
    }

    @Override
    public void loadGameParams() {
        GameParams gameParams = inMemoryStore.getGameParams();

        PlayerController leftController = inMemoryStore.getLeftPlayerController();
        Player leftPlayer = leftController.getPlayer();
        leftPlayer.setRacket(gameParams.createRacket());

        PlayerController rightController = inMemoryStore.getRightPlayerController();
        Player rightPlayer = rightController.getPlayer();
        rightPlayer.setRacket(gameParams.createRacket());

        Ball ball = gameParams.createBall();
        screenUpdateDelay = gameParams.getScreenUpdateDelayMillis();
        int scoresToWin = gameParams.getScoresToWin();

        view().onGameParamsLoaded(leftController, rightController, ball, scoresToWin);
    }

    private void pauseGame() {
        pauseThread();
        gameState = STATE_PAUSED;
        if (hasView()) {
            view().onGamePaused();
        }
    }

    private void pauseThread() {
        isPause = true;
    }

    private synchronized void resumeGame() {
        gameState = STATE_GAME;
        isPause = false;
        view().onGameResumed();
        notify();
    }

    public void restartGame() {
        pauseThread();
        view().onGameRestarted();
    }

    @Override
    public void exitGame(){
        isExit = true;
        if (isPause) {
            resumeGame();
        }
    }

    @Override
    public void onGoalEvent() {
        view().respawnBall();
        isPause = true;
        if (gameState != STATE_GAME_ENDED) {
            runTimerThread();
        }
    }

    private void runTimerThread() {
        new Thread(() -> {
            gameState = STATE_TIMER;

            for (int i = TIMER_TICKS; i > 0; i--) {
                if (!isExit) {
                    view().onTimerTick(String.valueOf(i));

                    try {
                        Thread.sleep(TIMER_DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    return;
                }
            }

            resumeGame();
        }).start();
    }

    @Override
    public void onControlButtonClicked() {
        switch (gameState) {
            case STATE_NOTHING:
            case STATE_GAME_ENDED:
            case STATE_PAUSED:
                resumeGame();
                break;

            case STATE_GAME:
                pauseGame();
                break;
        }
    }

    @Override
    public void endGame(Player winner) {
        //todo save winner in db
        gameState = STATE_GAME_ENDED;
        pauseThread();
    }

    private synchronized void checkPause(){
        while (isPause)
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
