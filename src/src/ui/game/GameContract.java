package src.ui.game;

import src.data.pojo.controllers.PlayerController;
import src.data.pojo.game.Ball;
import src.data.pojo.game.Player;

public interface GameContract {
    interface IGameView {
        void onGameParamsLoaded(PlayerController leftController, PlayerController rightController, Ball ball, int scoresToWin);
        void updateScreen();
        void respawnBall();
        void onGameRestarted();
        void onGamePaused();
        void onGameResumed();
        void onTimerTick(String time);
    }

    interface IGamePresenter {
        void loadGameParams();
        void exitGame();
        void onGoalEvent();
        void onControlButtonClicked();
        void restartGame();
        void endGame(Player winner);
    }
}