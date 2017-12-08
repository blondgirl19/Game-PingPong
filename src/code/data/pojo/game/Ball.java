package code.data.pojo.game;

import code.data.pojo.Point;
import resources.colors;
import resources.constants;

import java.awt.*;

import static resources.constants.*;

public class Ball implements Scalable {
    private BallCallback callback;

    private double ballDiameter, nextCollideY;
    private Point coordinates;
    private double defaultStepX, defaultStepY;

    private double stepByX, stepByY;

    public Ball(double stepInPX, double ballDiameter) {
        this.defaultStepX = stepInPX;
        this.defaultStepY = stepInPX;

        this.stepByX = stepInPX;
        this.stepByY = stepInPX;
        this.ballDiameter = ballDiameter;
        respawn(new Point(constants.UNDEFINED_INT, constants.UNDEFINED_INT));

        nextCollideY = UNDEFINED_DOUBLE;
    }

    public void setCallback(BallCallback callback) {
        this.callback = callback;
    }

    public void respawn(Point startCoordinates){
        coordinates = startCoordinates;

        if (stepByX < 0) {
            stepByX = -defaultStepX;
        } else {
            stepByX = defaultStepX;
        }

        if (stepByY < 0) {
            stepByY = -defaultStepY;
        } else {
            stepByY = defaultStepY;
        }
    }

    @Override
    public void onScreenResized(double scaleX, double scaleY) {
        resetNextCollide();
        /*double avgScale = (scaleX + scaleY) / 2;
        ballDiameter *= avgScale;*/ //диаметр лучше не масштабировать

        defaultStepY *= scaleY;
        defaultStepX *= scaleX;

        coordinates.x *= scaleX;
        coordinates.y *= scaleY;

        stepByX *= scaleX;
        stepByY *= scaleY;
    }

    private void updateLocation(){
        coordinates.x += stepByX;
        coordinates.y += stepByY;
    }

    public void update(Racket leftRacket, Racket rightRacket, Point minCoordinates, Point maxCoordinates) {
        updateLocation();

        if (isOutOfLeft(coordinates.x, minCoordinates.x)) {
            onOutOfLeftEvent();
        } else if (isOutOfRight(coordinates.x,maxCoordinates.x)){
            onOutOfRightEvent();
        } else if (isTopCollide(coordinates.y, minCoordinates.y)) {
            onTopCollide();
        } else if (isBottomCollide(coordinates.y, maxCoordinates.y)){
            onBottomCollide();
        } else if (leftRacket.getBounds().intersects(getBounds())) {
            onLeftRacketCollide(leftRacket);
        } else if (rightRacket.getBounds().intersects(getBounds())) {
            onRightRacketCollide(rightRacket);
        }
    }

    private void onOutOfLeftEvent() {
        resetNextCollide();
        stepByX = - stepByX;
        if (callback != null) {
            callback.onLeftPlayerGoal();
        }
    }

    private void onOutOfRightEvent() {
        resetNextCollide();
        stepByX = - stepByX;
        if (callback != null) {
            callback.onRightPlayerGoal();
        }
    }

    private void onTopCollide() {
        if (getYMoveDirection() == DIRECTION_UP) {
            stepByY = -stepByY; //change y moving direction
        }
    }

    private void onBottomCollide() {
        if (getYMoveDirection() == DIRECTION_DOWN) {
            stepByY = -stepByY;
        }
    }

    private void onLeftRacketCollide(Racket racket) {
        if (stepByX < 0) {
            resetNextCollide();

            if (racket.getMoveState() != STOP_MOVING) {
                if (racket.getMoveState() == getYMoveDirection()) {
                    stepByX -= defaultStepX * constants.PERCENT_2; // ускоряем мяч по Х, если двигаются в одну сторону
                } else {
                    changeYDirectionAndIncreaseSpeed();
                }
            }else {
                increaseXY();
            }

            stepByX = -stepByX; // change x moving direction
        }
    }

    private void onRightRacketCollide(Racket racket) {
        if (stepByX > 0) {
            resetNextCollide();

            if (racket.getMoveState() != STOP_MOVING) {
                if (racket.getMoveState() == getYMoveDirection()) {
                    stepByX += defaultStepX * constants.PERCENT_2; // ускоряем мяч по Х, если двигаются в одну сторону
                } else {
                    changeYDirectionAndIncreaseSpeed();
                }
            } else {
                increaseXY();
            }

            stepByX = -stepByX;
        }
    }

    private void increaseXY() {
        stepByY *= constants.PERCENT_102;
        stepByX *= constants.PERCENT_105;
    }

    private void changeYDirectionAndIncreaseSpeed() {
        if (stepByY < 0) {
            stepByY = stepByY - defaultStepY * constants.PERCENT_10;
        } else {
            stepByY = stepByY + defaultStepY * constants.PERCENT_10;
        }

        stepByY = -stepByY;
    }

    private void resetNextCollide() {
        nextCollideY = UNDEFINED_DOUBLE;
    }

    public double getNextOutOfBorderCoordinateY(Point minCoordinates, Point maxCoordinates) {
        if (nextCollideY == UNDEFINED_DOUBLE) {
            double stepX = stepByX;
            double stepY = stepByY;
            Point currentPoint = coordinates.clone();


            while (true) {
                currentPoint.x += stepX;
                currentPoint.y += stepY;

                if (isOutOfLeft(currentPoint.x, minCoordinates.x)) {
                    break;
                } else if (isOutOfRight(currentPoint.x, maxCoordinates.x)) {
                    break;
                } else if (isTopCollide(currentPoint.y, minCoordinates.y)){
                    if (getYMoveDirection() == DIRECTION_UP) {
                        stepY = -stepY;
                    }
                } else if (isBottomCollide(currentPoint.y, maxCoordinates.y)){
                    if (getYMoveDirection() == DIRECTION_DOWN) {
                        stepY = -stepY;
                    }
                }
            }

            nextCollideY = currentPoint.y;
        }

        return nextCollideY;
    }

    public Point getCenterCoordinates() {
        return new Point(getCenterX(), getCenterY());
    }

    private boolean isOutOfRight(double ballX, double rightBorderX) {
        return ballX >= rightBorderX + ballDiameter/2; // will out of right bound on half width
    }

    private boolean isOutOfLeft(double ballX, double leftBorderX) {
        return ballX <= leftBorderX - ballDiameter/2; // will out of left bound on half width
    }

    private boolean isTopCollide(double ballY, double topY) {
        return ballY <= topY;
    }

    private boolean isBottomCollide(double ballY, double bottomY){
        return ballY >= bottomY - ballDiameter;
    }

    public int getXMoveDirection() {
        if (stepByX >= 0) {
            return DIRECTION_RIGHT;
        } else {
            return DIRECTION_LEFT;
        }
    }

    public int getYMoveDirection() {
        if (stepByY >= 0) {
            return DIRECTION_DOWN;
        } else {
            return DIRECTION_UP;
        }
    }

    public Rectangle getBounds() {
        return new Rectangle((int)coordinates.x, (int)coordinates.y, (int) ballDiameter, (int) ballDiameter);
    }

    public void repaint(Graphics g) {
        g.setColor(colors.GREYISH_BROWN);
        g.fillOval((int)coordinates.x, (int)coordinates.y, (int) ballDiameter, (int) ballDiameter);
    }

    public double getCenterX() {
        return coordinates.x - ballDiameter/2;
    }

    public double getCenterY() {
        return coordinates.y - ballDiameter/2;
    }

    public double getBallDiameter() {
        return ballDiameter;
    }

    public void setBallDiameter(double ballDiameter) {
        this.ballDiameter = ballDiameter;
    }

    public interface BallCallback {
        void onLeftPlayerGoal();
        void onRightPlayerGoal();
    }
}
