package code.ui.game;

import code.data.pojo.controllers.BasePlayerController;

public interface GameContract {
    interface IGameView {
        void onControllersLoaded(BasePlayerController leftController, BasePlayerController rightController);
        void updateScreen();

        void respawnBall();
    }

    interface IGamePresenter {
        void loadControllers();
        void pauseGame();
        void resumeGame();
        void exitGame();

        void onGoalEvent();
    }
}
