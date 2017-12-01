package code.ui.game;

import code.core.Presenter;
import code.data.InMemoryStore;
import code.data.pojo.players.BasePlayer;

public class GamePresenter extends Presenter<GameContract.IGameView> implements GameContract.IGamePresenter {
    private InMemoryStore inMemoryStore;

    public GamePresenter() {
        inMemoryStore = new InMemoryStore();
    }

    @Override
    public void loadPlayers() {
        BasePlayer leftPlayer = inMemoryStore.getLeftPlayer();
        BasePlayer rightPlayer = inMemoryStore.getRightPlayer();

        view().onPlayersLoaded(leftPlayer, rightPlayer);
    }
}
