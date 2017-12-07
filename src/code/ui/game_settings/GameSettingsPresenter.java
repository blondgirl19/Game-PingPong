package code.ui.game_settings;

import code.core.Presenter;
import code.data.InMemoryStore;
import code.data.pojo.GameParams;
import code.data.pojo.game.Player;

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
