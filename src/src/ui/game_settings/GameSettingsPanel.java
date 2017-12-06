package src.ui.game_settings;

import src.core.BasePanel;
import src.data.pojo.GameParams;
import src.data.pojo.game.Player;
import src.ui.components.GameParamsPanel;
import src.ui.components.PlayerSelectionPanel;
import src.ui.game.GamePanel;
import src.ui.main_menu.MainMenuPanel;
import resources.constants;
import resources.strings;
import resources.styles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static resources.strings.NAME_CANNOT_BE_EMPTY;
import static resources.strings.WARNING;

public class GameSettingsPanel extends BasePanel implements GameSettingsContract.IGameSettingsView {
    private GameSettingsPresenter presenter;
    private PlayerSelectionPanel leftPlayerPanel, rightPlayerPanel;
    private JPanel scrollablePanel;
    private GameParamsPanel paramsPanel;

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
    protected void fillScreenContent() {
        screenContentPanel.setLayout(new BorderLayout());
        scrollablePanel = new JPanel(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(scrollablePanel);
        screenContentPanel.add(scrollPane, BorderLayout.CENTER);

        presenter.loadParams();
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
                    GameParams gameParams = paramsPanel.getGameParams();

                    presenter.saveGameParams(leftPlayer, rightPlayer, gameParams);
                } else {
                    showNotification(WARNING, NAME_CANNOT_BE_EMPTY);
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
    public void onPreviousParamsLoaded(Player leftPlayer, Player rightPlayer, GameParams gameParams) {
        setupPlayersPanel(leftPlayer, rightPlayer);
        setupParamsPanel(gameParams);
    }

    private void setupParamsPanel(GameParams gameParams) {
        paramsPanel = new GameParamsPanel(gameParams);
        scrollablePanel.add(paramsPanel);
    }

    private void setupPlayersPanel(Player leftPlayer, Player rightPlayer) {
        JPanel playersSelectionPanel = new JPanel(new GridLayout(0, constants.PLAYERS_COUNT));
        playersSelectionPanel.setOpaque(false);

        leftPlayerPanel = new PlayerSelectionPanel(strings.PLAYER_1, leftPlayer);
        playersSelectionPanel.add(leftPlayerPanel);

        rightPlayerPanel = new PlayerSelectionPanel(strings.PLAYER_2, rightPlayer);
        playersSelectionPanel.add(rightPlayerPanel);

        scrollablePanel.add(playersSelectionPanel);
    }

    @Override
    public void onPlayersSaved() {
        gotoPanel(new GamePanel());
    }
}
