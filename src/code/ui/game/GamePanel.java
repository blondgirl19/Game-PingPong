package code.ui.game;

import code.core.BasePanel;
import code.data.pojo.Dimension;
import code.data.pojo.game.Ball;
import code.data.pojo.game.Player;
import code.data.pojo.Point;
import code.data.pojo.game.Racket;
import code.data.pojo.controllers.PlayerController;
import code.data.pojo.controllers.HumanController;
import code.ui.components.ScoresPanel;
import code.ui.components.TablePanel;
import code.ui.game_settings.GameSettingsPanel;
import resources.strings;

import java.awt.*;
import java.awt.event.*;

import static resources.constants.*;

public class GamePanel extends BasePanel implements GameContract.IGameView, TablePanel.PaintCallback, Ball.BallCallback, ScoresPanel.ScoresCallback {
    private GamePresenter presenter;
    private TablePanel tablePanel;
    private PlayerController leftController, rightController;
    private Player leftPlayer, rightPlayer;
    private Ball ball;
    private ScoresPanel scoresPanel;

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
                onBackPressed();
            }
        });
    }

    private void onBackPressed() {
        presenter.exitGame();
        removePlayersRackets();
        gotoPanel(new GameSettingsPanel());
    }

    @Override
    protected void fillScreenContent() {
        screenContentPanel.setLayout(new BorderLayout());
        tablePanel = new TablePanel();
        tablePanel.setTextToDraw(strings.PRESS_SPACE_TO_START);
        tablePanel.setCallback(this);
        screenContentPanel.add(tablePanel, BorderLayout.CENTER);

        presenter.loadGameParams();
        initScreenResizeListener();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    presenter.onControlButtonClicked();
                }
            }
        });
    }

    private void initScreenResizeListener() {
        tablePanel.addComponentListener(new ComponentAdapter() {
            private Dimension previousDimension = new Dimension(MIN_FRAME_WIDTH, MIN_TABLE_PANEL_HEIGHT);

            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Dimension currentDimension = tablePanel.getDimension();

                if (!previousDimension.equals(currentDimension)) {
                    double scaleX = currentDimension.width / previousDimension.width;
                    double scaleY = currentDimension.height / previousDimension.height;

                    leftPlayer.onScreenResized(scaleX, scaleY);
                    rightPlayer.onScreenResized(scaleX, scaleY);
                    ball.onScreenResized(scaleX, scaleY);
                }

                previousDimension = currentDimension;
                tablePanel.repaint();
            }
        });
    }

    @Override
    public void onGameParamsLoaded(PlayerController leftController, PlayerController rightController, Ball ball, int scoresToWin) {
        this.ball = ball;
        this.ball.setCallback(this);

        this.leftController = leftController;
        leftPlayer = leftController.getPlayer();

        this.rightController = rightController;
        rightPlayer = rightController.getPlayer();

        if (leftPlayer.isHuman()) {
            addKeyListener((HumanController) leftController);
        }

        if (rightPlayer.isHuman()) {
            addKeyListener((HumanController) rightController);
        }

        scoresPanel = new ScoresPanel(leftPlayer, rightPlayer, scoresToWin);
        scoresPanel.setCallback(this);
        screenContentPanel.add(scoresPanel, BorderLayout.SOUTH);

        respawnRackets();
        respawnBall();
    }

    private void respawnRackets() {
        Racket leftRacket = leftPlayer.getRacket();
        double racketStartY = tablePanel.getHeight()/2 - leftRacket.getRacketSize().height/2;

        leftRacket.setCoordinates(new Point(tablePanel.getRacketStartX(), racketStartY));

        Racket rightRacket = rightPlayer.getRacket();
        double racketX = tablePanel.getRacketEndX() - rightRacket.getRacketSize().getWidth();
        rightRacket.setCoordinates(new Point(racketX, racketStartY));
    }

    private void removePlayersRackets() {
        leftPlayer.getRacket().removeFromField();
        rightPlayer.getRacket().removeFromField();
    }

    @Override
    public void updateScreen() {
        if (isPanelInit()) {
            ball.update(leftPlayer.getRacket().getBounds(),
                    rightPlayer.getRacket().getBounds(),
                    tablePanel.getMinBallCoordinates(),
                    tablePanel.getMaxBallCoordinates());

            rightController.update(ball.getCenterCoordinates(), tablePanel.getDimension());
            leftController.update(ball.getCenterCoordinates(), tablePanel.getDimension());

            tablePanel.repaint();
        }
    }

    private void setupWindowListeners() {
        if (isPanelInit()) {
            setFocusable(true);
            requestFocusInWindow();
        }
    }

    private boolean isPanelInit() {
        return getWidth() > 0 && getHeight() > 0;
    }

    @Override
    public void respawnBall() {
        Point point = tablePanel.getCenterPoint();
        point.x -= ball.getBallDiameter()/2;
        point.y -= ball.getBallDiameter()/2;
        ball.respawn(point);
    }

    @Override
    public void onGameRestarted() {
        respawnBall();
        respawnRackets();
        scoresPanel.refreshScores();
        tablePanel.setTextToDraw(strings.PRESS_SPACE_TO_START);

    }

    @Override
    public void onGamePaused() {
        tablePanel.setTextToDraw(strings.PAUSE);
    }

    @Override
    public void onGameResumed() {
        tablePanel.clearTextToDraw();
    }

    @Override
    public void onTimerTick(String time) {
        tablePanel.setTextToDraw(time);
    }

    @Override
    public void onPaintComponent(Graphics g) {
        setupWindowListeners();

        ball.repaint(g);
        leftPlayer.repaint(g);
        rightPlayer.repaint(g);
    }

    @Override
    public void onLeftPlayerGoal() {
        scoresPanel.incRightPlayerScore();
        presenter.onGoalEvent();
    }

    @Override
    public void onRightPlayerGoal() {
        scoresPanel.incLeftPlayerScores();
        presenter.onGoalEvent();
    }

    @Override
    public void onPlayerWin(Player winner) {
        presenter.endGame(winner);

        showYesNoDialog(
                strings.GAME_END,
                String.format(strings.PLAYER_WIN_FORMAT, winner.getName()),
                strings.PLAY_AGAIN,
                strings.GAME_PARAMS,
                new YesNoClickListener() {
                    @Override
                    public void onYesClicked() {
                        presenter.restartGame();
                    }

                    @Override
                    public void onNoClicked() {
                        onBackPressed();
                    }
                });
    }
}
