package code.ui.game;

import code.core.Presenter;
import code.data.InMemoryStore;
import code.data.pojo.players.BasePlayer;

public class GamePresenter extends Presenter<GameContract.IGameView> implements GameContract.IGamePresenter {
    private InMemoryStore inMemoryStore;
    private Thread gameUpdateThread;
    private volatile boolean isPause, isExit;

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
                    Thread.sleep(TIME_BETWEEN_SCREEN_UPDATE_IN_MILLIS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        gameUpdateThread.start();
    }

    @Override
    public void loadPlayers() {
        BasePlayer leftPlayer = inMemoryStore.getLeftPlayer();
        BasePlayer rightPlayer = inMemoryStore.getRightPlayer();

        view().onPlayersLoaded(leftPlayer, rightPlayer);
    }

    @Override
    public void pauseGame() {
        isPause = true;
    }

    @Override
    public void stopGame() {
        pauseGame();
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

    private synchronized void checkPause(){
        while (isPause)
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public static final long TIME_BETWEEN_SCREEN_UPDATE_IN_MILLIS = 5;
}
