package src.ui.game;

import src.core.Presenter;
import src.data.InMemoryStore;
import src.data.pojo.GameParams;
import src.data.pojo.controllers.PlayerController;
import src.data.pojo.game.Ball;
import src.data.pojo.game.Player;

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
        isPause = true;
        if (hasView()) {
            view().onGamePaused();
        }
    }

    private synchronized void resumeGame() {
        isPause = false;
        view().onGameResumed();
        notify();
    }

    public void restartGame() {
        pauseGame();
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
            gameState = STATE_TIMER;
            runTimerThread();
        }
    }

    private void runTimerThread() {
        new Thread(() -> {
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

            gameState = STATE_GAME;
            resumeGame();
        }).start();
    }

    @Override
    public void onControlButtonClicked() {
        switch (gameState) {
            case STATE_GAME_ENDED:
            case STATE_PAUSED:
                gameState = STATE_GAME;
                resumeGame();
                break;

            case STATE_NOTHING:
                gameState = STATE_GAME;
                resumeGame();
                break;

            case STATE_GAME:
                gameState = STATE_PAUSED;
                pauseGame();
                break;
        }
    }

    @Override
    public void endGame(Player winner) {
        //todo save winner in db
        isPause = true;
        gameState = STATE_GAME_ENDED;
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
