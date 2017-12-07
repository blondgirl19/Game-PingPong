package code.ui.components;

import resources.colors;
import resources.constants;
import resources.strings;
import resources.styles;
import code.core.BaseBorderPanel;
import code.data.pojo.Dimension;
import code.data.pojo.GameParams;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static resources.constants.*;
import static resources.strings.*;

public class GameParamsPanel extends BaseBorderPanel {
    private GameParams lastParams;

    private ValuableSlider ballSpeedSlider;
    private ValuableSlider ballDiameterSlider;

    private ValuableSlider racketSpeedSlider;
    private ValuableSlider racketHeightSlider;

    private ValuableSlider scoresToWinSlider;

    public GameParamsPanel(GameParams lastParams) {
        super(colors.DARK_GRAY, strings.ANOTHER_PARAMS);

        this.lastParams = lastParams;
        initScreenContent();
    }

    @Override
    protected void initScreenContent() {
        screenContentPanel.setLayout(new GridLayout(0, COMPONENTS_IN_ROW, constants.TITLE_MARGINS, constants.TITLE_MARGINS));
        createSliders();
        setStartValues();
        setupResetLabel();
    }

    private void createSliders() {
        ballDiameterSlider = createSlider(BALL_DIAMETER, MIN_BALL_DIAMETER, MAX_BALL_DIAMETER);
        ballSpeedSlider = createSlider(BALL_SPEED, MIN_BALL_STEP_PX, MAX_BALL_STEP_PX);
        racketHeightSlider = createSlider(RACKETS_SIZE, MIN_RACKET_HEIGHT, MAX_RACKET_HEIGHT);
        racketSpeedSlider = createSlider(RACKETS_SPEED, MIN_RACKET_STEP_PX, MAX_RACKET_STEP_PX);
        scoresToWinSlider = createSlider(SCORES_TO_WIN, MIN_SCORES_TO_WIN, MAX_SCORES_TO_WIN);
    }

    private void setStartValues() {
        ballDiameterSlider.setValue(lastParams.getBallDiameter());
        ballSpeedSlider.setValue((int) lastParams.getBallStep());
        racketHeightSlider.setValue((int) lastParams.getRacketSize().height);
        racketSpeedSlider.setValue((int) lastParams.getRacketStep());
        scoresToWinSlider.setValue(lastParams.getScoresToWin());
    }

    private void setupResetLabel() {
        JLabel resetLabel = new JLabel(strings.DEFAULT);
        styles.LightFontStyle(resetLabel);
        resetLabel.setHorizontalAlignment(SwingConstants.CENTER);
        styles.addForegroundClickListener(resetLabel, colors.GREYISH_BROWN, colors.BLUE);

        resetLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mousePressed(e);
                lastParams = new GameParams();
                setStartValues();
            }
        });

        screenContentPanel.add(resetLabel);
    }

    private ValuableSlider createSlider(String labelText, int minValue, int maxValue) {
        ValuableSlider slider = new ValuableSlider();
        slider.createSlider(labelText, minValue, maxValue);

        screenContentPanel.add(slider);
        return slider;
    }
    public GameParams getGameParams() {
        GameParams gameParams = new GameParams();
        gameParams.setBallDiameter(ballDiameterSlider.getValue());
        gameParams.setBallStep(ballSpeedSlider.getValue());

        Dimension racketSize = gameParams.getRacketSize();
        racketSize.setHeight(racketHeightSlider.getValue());
        gameParams.setRacketSize(racketSize);

        gameParams.setRacketStep(racketSpeedSlider.getValue());
        gameParams.setScoresToWins(scoresToWinSlider.getValue());

        return gameParams;
    }

    private static final int COMPONENTS_IN_ROW = 2;
}
