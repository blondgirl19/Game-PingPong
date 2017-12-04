package code.ui.game;

import code.data.pojo.players.BasePlayer;

public interface GameContract {
    interface IGameView {
        void onPlayersLoaded(BasePlayer leftPlayer, BasePlayer rightPlayer);
        void updateScreen();
    }

    interface IGamePresenter {
        void loadPlayers();
        void pauseGame();
        void resumeGame();
        void exitGame();
    }
}
