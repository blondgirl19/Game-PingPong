package code.ui.game;

import code.core.BasePanel;
import code.data.pojo.Dimension;
import code.data.pojo.game.Ball;
import code.data.pojo.game.Player;
import code.data.pojo.Point;
import code.data.pojo.game.Racket;
import code.data.pojo.controllers.BasePlayerController;
import code.data.pojo.controllers.HumanController;
import code.ui.components.ScoresPanel;
import code.ui.components.TablePanel;
import code.ui.game_settings.GameSettingsPanel;
import resources.strings;

import java.awt.*;
import java.awt.event.*;

public class GamePanel extends BasePanel implements GameContract.IGameView, TablePanel.PaintCallback, Ball.BallCallback {
    private GamePresenter presenter;
    private TablePanel tablePanel;
    private BasePlayerController leftController, rightController;
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
                presenter.exitGame();
                removePlayersRackets();
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

        presenter.loadControllers();
        initScreenResizeListener();
    }

    private void initScreenResizeListener() {
        tablePanel.addComponentListener(new ComponentAdapter() {
            private Dimension previousDimension;

            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Dimension currentDimension = tablePanel.getDimension();

                if (previousDimension != null && !previousDimension.equals(currentDimension)) {
                    double scaleX = currentDimension.width / previousDimension.width;
                    double scaleY = currentDimension.height / previousDimension.height;

                    leftPlayer.onScreenResized(scaleX, scaleY);
                    rightPlayer.onScreenResized(scaleX, scaleY);
                }

                previousDimension = currentDimension;
            }
        });
    }

    @Override
    public void onControllersLoaded(BasePlayerController leftController, BasePlayerController rightController) {
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

        scoresPanel = new ScoresPanel(leftPlayer, rightPlayer);
        screenContentPanel.add(scoresPanel, BorderLayout.SOUTH);

        presenter.resumeGame();
    }

    private void initRacketsStartCoordinates() {
        int racketStartY = tablePanel.getHeight()/2;

        Racket leftRacket = leftPlayer.getRacket();
        leftRacket.setCoordinates(new Point(tablePanel.getRacketStartX(), racketStartY));

        Racket rightRacket = rightPlayer.getRacket();
        rightRacket.setCoordinates(new Point(tablePanel.getRacketEndX(), racketStartY));
    }

    private void removePlayersRackets() {
        leftPlayer.getRacket().removeFromField();
        rightPlayer.getRacket().removeFromField();
    }

    @Override
    public void updateScreen() {
        if (isGameComponentsInit()) {
            ball.update(leftPlayer.getRacket().getBounds(),
                    rightPlayer.getRacket().getBounds(),
                    tablePanel.getMinBallCoordinates(),
                    tablePanel.getMaxBallCoordinates());

            rightController.update(ball.getCoordinates(), tablePanel.getDimension());
            leftController.update(ball.getCoordinates(), tablePanel.getDimension());

            tablePanel.repaint();
        }
    }

    private void checkGameComponentsInit() {
        if (isTableInit()) {
            if (!isGameComponentsInit()) {
                initRacketsStartCoordinates();
                respawnBall();
                setFocusable(true);
                requestFocusInWindow();
            }
        }
    }

    private boolean isGameComponentsInit() {
        boolean isRacketInit = leftPlayer.getRacket().isInitialized() && rightPlayer.getRacket().isInitialized();
        return isRacketInit && ball != null && isTableInit();
    }

    @Override
    public void respawnBall() {
        if (ball == null) {
            ball = new Ball();
            ball.setCallback(this);
        }

        ball.respawn(tablePanel.getCenterPoint());

    }

    private boolean isTableInit() {
        return tablePanel.getWidth() > 0 && tablePanel.getHeight() > 0;
    }

    @Override
    public void onPaintComponent(Graphics g) {
        checkGameComponentsInit();

        leftPlayer.repaint(g);
        rightPlayer.repaint(g);
        ball.repaint(g);
        repaint();
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
}
