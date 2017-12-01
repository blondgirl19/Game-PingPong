package code.ui.game_settings;

import code.core.Presenter;
import code.data.InMemoryStore;
import code.data.pojo.players.BasePlayer;

public class GameSettingsPresenter extends Presenter<GameSettingsContract.IGameSettingsView> implements GameSettingsContract.IGameSettingsPresenter {
    private InMemoryStore inMemoryStore;

    public GameSettingsPresenter() {
        this.inMemoryStore = new InMemoryStore();
    }

    @Override
    public void savePlayers(BasePlayer leftPlayer, BasePlayer rightPlayer) {
        inMemoryStore.savePlayers(leftPlayer, rightPlayer);

        view().onPlayersSaved();
    }

    @Override
    public void loadPlayers() {
        view().onPlayersLoaded(inMemoryStore.getLeftPlayer(), inMemoryStore.getRightPlayer());
    }
}
