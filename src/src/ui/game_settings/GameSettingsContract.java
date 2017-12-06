package src.ui.game_settings;

import src.data.pojo.game.Player;

public interface GameSettingsContract {
    interface IGameSettingsView {
        void onPlayersLoaded(Player leftPlayer, Player rightPlayer);
        void onPlayersSaved();
    }

    interface IGameSettingsPresenter {
        void savePlayers(Player leftPlayer, Player rightPlayer);
        void loadPlayers();
    }
}
