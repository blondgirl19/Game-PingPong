package code.ui.game_settings;

import code.core.BasePanel;
import code.data.pojo.game.Player;
import code.ui.components.PlayerSelectionPanel;
import code.ui.game.GamePanel;
import code.ui.main_menu.MainMenuPanel;
import resources.constants;
import resources.strings;
import resources.styles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameSettingsPanel extends BasePanel implements GameSettingsContract.IGameSettingsView {
    private GameSettingsPresenter presenter;
    private PlayerSelectionPanel leftPlayerPanel, rightPlayerPanel;

    @Override
    protected void fillScreenContent() {
        screenContentPanel.setLayout(new BorderLayout());

        presenter.loadPlayers();
        initStartButton();
    }

    private void initStartButton() {
        JLabel startButton = new JLabel(strings.START);
        styles.OrangeButtonStyle(startButton);

        screenContentPanel.add(startButton, BorderLayout.SOUTH);

        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (leftPlayerPanel.checkIsDataValid() && rightPlayerPanel.checkIsDataValid()) {
                    Player leftPlayer = leftPlayerPanel.getPlayer();
                    Player rightPlayer = rightPlayerPanel.getPlayer();

                    presenter.savePlayers(leftPlayer, rightPlayer);
                }
            }
        });
    }

    @Override
    protected void providePresenter() {
        presenter = new GameSettingsPresenter();
        presenter.bindView(this);
    }

    @Override
    protected void fillTitle() {
        titlePanel.setTitle(strings.GAME_PARAMS);
        titlePanel.setBackButtonVisible(true);

        titlePanel.setBackButtonClickListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                gotoPanel(new MainMenuPanel());
            }
        });
    }

    @Override
    public void onPlayersLoaded(Player leftPlayer, Player rightPlayer) {
        JPanel playersSelectionPanel = new JPanel(new GridLayout(0, constants.PLAYERS_COUNT));
        playersSelectionPanel.setOpaque(false);

        leftPlayerPanel = new PlayerSelectionPanel(strings.PLAYER_1, leftPlayer);
        playersSelectionPanel.add(leftPlayerPanel);

        rightPlayerPanel = new PlayerSelectionPanel(strings.PLAYER_2, rightPlayer);
        playersSelectionPanel.add(rightPlayerPanel);

        screenContentPanel.add(playersSelectionPanel, BorderLayout.NORTH);
    }

    @Override
    public void onPlayersSaved() {
        gotoPanel(new GamePanel());
    }
}
