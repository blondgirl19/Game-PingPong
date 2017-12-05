package code.ui.game;

import code.core.Presenter;
import code.data.InMemoryStore;
import code.data.pojo.GameParams;
import code.data.pojo.controllers.PlayerController;
import code.data.pojo.game.Ball;
import code.data.pojo.game.Player;

public class GamePresenter extends Presenter<GameContract.IGameView> implements GameContract.IGamePresenter {
    private InMemoryStore inMemoryStore;
    private Thread gameUpdateThread;
    private volatile boolean isPause, isExit;
    private int screenUpdateDelay;

    public GamePresenter() {
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

    @Override
    public void pauseGame() {
        isPause = true;
    }

    @Override
    public synchronized void resumeGame() {
        isPause = false;
        notify();
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
    }

    @Override
    public void onGameEnd() {
        pauseGame();
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
