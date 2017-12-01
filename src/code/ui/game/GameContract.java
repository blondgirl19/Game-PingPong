package code.ui.game;

import code.data.pojo.players.BasePlayer;

public interface GameContract {
    interface IGameView {
        void onPlayersLoaded(BasePlayer leftPlayer, BasePlayer rightPlayer);
    }

    interface IGamePresenter {
        void loadPlayers();
    }
}
