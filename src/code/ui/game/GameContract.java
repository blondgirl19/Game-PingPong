package code.ui.game;

import code.data.pojo.controllers.PlayerController;
import code.data.pojo.game.Ball;

public interface GameContract {
    interface IGameView {
        void onGameParamsLoaded(PlayerController leftController, PlayerController rightController, Ball ball, int scoresToWin);
        void updateScreen();

        void respawnBall();
    }

    interface IGamePresenter {
        void loadGameParams();
        void pauseGame();
        void resumeGame();
        void exitGame();
        void onGoalEvent();
        void onGameEnd();
    }
}
