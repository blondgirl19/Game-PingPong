package code.ui.game;

import code.core.BasePanel;
import code.data.pojo.Racket;
import code.data.pojo.players.BasePlayer;
import code.ui.components.ScoresPanel;
import code.ui.components.TablePanel;
import code.ui.game_settings.GameSettingsPanel;
import resources.strings;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends BasePanel implements GameContract.IGameView, TablePanel.PaintCallback {
    private GamePresenter presenter;
    private TablePanel tablePanel;
    private BasePlayer leftPlayer, rightPlayer;

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
        tablePanel.setCallback(this);
        screenContentPanel.add(tablePanel, BorderLayout.CENTER);
        presenter.loadPlayers();
    }

    @Override
    public void onPlayersLoaded(BasePlayer leftPlayer, BasePlayer rightPlayer) {
        this.leftPlayer = leftPlayer;
        this.rightPlayer = rightPlayer;
        initRackets();

        ScoresPanel scoresPanel = new ScoresPanel(leftPlayer, rightPlayer);
        screenContentPanel.add(scoresPanel, BorderLayout.SOUTH);

        presenter.resumeGame();
    }

    private void initRackets() {
        printDimention();
        int racketStartY = tablePanel.getHeight()/2;

        Racket leftRacket = new Racket(100, racketStartY);
        leftPlayer.setRacket(leftRacket);

        Racket rightRacket = new Racket(tablePanel.getWidth(), racketStartY);
        rightPlayer.setRacket(rightRacket);
    }

    @Override
    public void updateScreen() {
        tablePanel.repaint();
    }

    private int counter = 0;

    @Override
    public void onPaintComponent(Graphics g) {
        leftPlayer.repaint(g);
        rightPlayer.repaint(g);

        counter++;
        if (counter % 200 == 0) {
            printDimention();
        }
    }

    private void printDimention() {
        int screenWidth = getWidth();
        int screenHeight = getHeight();
        int tableWidth = tablePanel.getWidth();
        int tableHeight = tablePanel.getHeight();

        System.out.println("H: " + screenHeight + ", W: " + screenWidth + ", TH: " + tableHeight + ", TW: " + tableWidth);
    }
}
