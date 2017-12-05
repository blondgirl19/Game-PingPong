package code.ui.game;

import code.core.Presenter;
import code.data.InMemoryStore;

import static resources.constants.SCREEN_UPDATE_DELAY;

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
                    Thread.sleep(SCREEN_UPDATE_DELAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        gameUpdateThread.start();
    }

    @Override
    public void loadControllers() {
        view().onControllersLoaded(inMemoryStore.getLeftPlayerController(), inMemoryStore.getRightPlayerController());
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

    private synchronized void checkPause(){
        while (isPause)
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
