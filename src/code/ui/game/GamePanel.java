package code.ui.game;

import code.core.BasePanel;
import code.data.pojo.players.BasePlayer;
import code.ui.components.ScoresPanel;
import code.ui.components.TablePanel;
import code.ui.game_settings.GameSettingsPanel;
import resources.strings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends BasePanel implements GameContract.IGameView {
    private GamePresenter presenter;
    private TablePanel tablePanel;

    @Override
    protected void providePresenter() {
        presenter = new GamePresenter();
        presenter.bindView(this);
    }

    @Override
    protected void fillTitle() {
        titlePanel.setTitle(strings.GAME);
        titlePanel.setBackButtonVisible(true);
        titlePanel.setBackButtonClickListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                presenter.exitGame();
                gotoPanel(new GameSettingsPanel());
            }
        });
    }

    @Override
    protected void fillScreenContent() {
        screenContentPanel.setLayout(new BorderLayout());
        tablePanel = new TablePanel();
        screenContentPanel.add(tablePanel, BorderLayout.CENTER);
        presenter.loadPlayers();
    }

    @Override
    public void onPlayersLoaded(BasePlayer leftPlayer, BasePlayer rightPlayer) {
        ScoresPanel scoresPanel = new ScoresPanel(leftPlayer, rightPlayer);
        screenContentPanel.add(scoresPanel, BorderLayout.SOUTH);

        presenter.resumeGame();
    }

    @Override
    public void updateScreen() {
        System.out.println("update");
    }
}
