package code.ui.game_settings;

import code.data.pojo.GameParams;
import code.data.pojo.game.Player;

public interface GameSettingsContract {
    interface IGameSettingsView {
        void onPreviousParamsLoaded(Player leftPlayer, Player rightPlayer, GameParams gameParams);
        void onPlayersSaved();
    }

    interface IGameSettingsPresenter {
        void saveGameParams(Player leftPlayer, Player rightPlayer, GameParams gameParams);
        void loadParams();
    }
}
