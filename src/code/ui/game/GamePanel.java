package code.ui.game;

import code.core.BasePanel;
import code.data.pojo.players.BasePlayer;
import code.ui.game_settings.GameSettingsPanel;
import resources.strings;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends BasePanel implements GameContract.IGameView {
    private GamePresenter presenter;

    @Override
    protected void providePresenter() {
        presenter = new GamePresenter();
        presenter.bindView(this);

        presenter.loadPlayers();
    }

    @Override
    protected void fillTitle() {
        titlePanel.setTitle(strings.GAME);
        titlePanel.setBackButtonVisible(true);
        titlePanel.setBackButtonClickListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                gotoPanel(new GameSettingsPanel());
            }
        });
    }

    @Override
    protected void fillScreenContent() {
        screenContentPanel.setLayout(new BorderLayout());
    }

    @Override
    public void onPlayersLoaded(BasePlayer leftPlayer, BasePlayer rightPlayer) {

    }
}
