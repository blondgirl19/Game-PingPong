package code.data.pojo;

import code.data.pojo.game.Ball;
import code.data.pojo.game.Racket;

import static resources.constants.*;

public class GameParams {
    private int scoresToWins;
    private Dimension racketSize;
    private int ballDiameter;
    private int fps;
    private double racketStep, ballStep;

    public GameParams() {
        scoresToWins = DEFAULT_SCORES_TO_WIN;
        racketSize = new Dimension(DEFAULT_RACKET_WIDTH, DEFAULT_RACKET_HEIGHT);
        ballDiameter = DEFAULT_BAL_DIAMETER;
        fps = DEFAULT_GAME_FPS;
        racketStep = DEFAULT_RACKET_STEP_PX;
        ballStep = DEFAULT_BALL_STEP_IN_PX;
    }

    public Racket createRacket() {
        return new Racket(racketSize, racketStep);
    }

    public Ball createBall() {
        return new Ball(ballStep, ballDiameter);
    }

    public int getScoresToWin() {
        return scoresToWins;
    }

    public void setScoresToWins(int scoresToWins) {
        this.scoresToWins = scoresToWins;
    }

    public Dimension getRacketSize() {
        return racketSize;
    }

    public void setRacketSize(Dimension racketSize) {
        this.racketSize = racketSize;
    }

    public int getBallDiameter() {
        return ballDiameter;
    }

    public void setBallDiameter(int ballDiameter) {
        this.ballDiameter = ballDiameter;
    }

    public int getFps() {
        return fps;
    }

    public void setFps(int fps) {
        this.fps = fps;
    }

    public int getScreenUpdateDelayMillis() {
        return MILLIS_IN_ONE_SECOND / fps;
    }

    public double getRacketStep() {
        return racketStep;
    }

    public void setRacketStep(double racketStep) {
        this.racketStep = racketStep;
    }

    public double getBallStep() {
        return ballStep;
    }

    public void setBallStep(double ballStep) {
        this.ballStep = ballStep;
    }
}
