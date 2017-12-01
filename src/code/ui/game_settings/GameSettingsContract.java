package code.ui.game_settings;

import code.data.pojo.players.BasePlayer;

public interface GameSettingsContract {
    interface IGameSettingsView {
        void onPlayersLoaded(BasePlayer leftPlayer, BasePlayer rightPlayer);
        void onPlayersSaved();
    }

    interface IGameSettingsPresenter {
        void savePlayers(BasePlayer leftPlayer, BasePlayer rightPlayer);
        void loadPlayers();
    }
}
