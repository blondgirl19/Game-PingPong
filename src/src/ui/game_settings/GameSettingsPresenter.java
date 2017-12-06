package src.ui.game_settings;

import src.core.Presenter;
import src.data.InMemoryStore;
import src.data.pojo.GameParams;
import src.data.pojo.game.Player;

public class GameSettingsPresenter extends Presenter<GameSettingsContract.IGameSettingsView> implements GameSettingsContract.IGameSettingsPresenter {
    private InMemoryStore inMemoryStore;

    public GameSettingsPresenter() {
        this.inMemoryStore = new InMemoryStore();
    }

    @Override
    public void saveGameParams(Player leftPlayer, Player rightPlayer, GameParams gameParams) {
        inMemoryStore.savePlayers(leftPlayer, rightPlayer);
        inMemoryStore.saveGameParams(gameParams);
        view().onPlayersSaved();
    }

    @Override
    public void loadParams() {
        view().onPreviousParamsLoaded(inMemoryStore.getLeftPlayer(), inMemoryStore.getRightPlayer(), inMemoryStore.getGameParams());
    }
}
