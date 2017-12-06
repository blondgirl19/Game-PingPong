package src.ui.game_settings;

import src.core.Presenter;
import src.data.InMemoryStore;
import src.data.pojo.game.Player;

public class GameSettingsPresenter extends Presenter<GameSettingsContract.IGameSettingsView> implements GameSettingsContract.IGameSettingsPresenter {
    private InMemoryStore inMemoryStore;

    public GameSettingsPresenter() {
        this.inMemoryStore = new InMemoryStore();
    }

    @Override
    public void savePlayers(Player leftPlayer, Player rightPlayer) {
        inMemoryStore.savePlayers(leftPlayer, rightPlayer);

        view().onPlayersSaved();
    }

    @Override
    public void loadPlayers() {
        view().onPlayersLoaded(inMemoryStore.getLeftPlayer(), inMemoryStore.getRightPlayer());
    }
}
