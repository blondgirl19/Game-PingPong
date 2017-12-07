package code.data.pojo.game;

import code.data.pojo.Point;
import resources.colors;
import resources.constants;

import java.awt.*;

import static resources.constants.UNDEFINED_DOUBLE;

public class Ball implements Scalable {
    private BallCallback callback;

    private double ballDiameter, nextCollideY;
    private Point coordinates;

    private double stepByX, stepByY;

    public Ball(double stepInPX, double ballDiameter) {
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
    }

    @Override
    public void onScreenResized(double scaleX, double scaleY) {
        /*double avgScale = (scaleX + scaleY) / 2;
        ballDiameter *= avgScale;*/ //диаметр лучше не масштабировать

        coordinates.x *= scaleX;
        coordinates.y *= scaleY;

        stepByX *= scaleX;
        stepByY *= scaleY;
    }

    private void updateLocation(){
        coordinates.x += stepByX;
        coordinates.y += stepByY;
    }

    public void update(Rectangle leftRacketBounds, Rectangle rightRacketBounds, Point minCoordinates, Point maxCoordinates) {
        updateLocation();

        if (isOutOfLeft(coordinates.x,minCoordinates.x) && callback != null) {
            resetNextCollide();
            stepByX = - stepByX;
            callback.onLeftPlayerGoal();
        } else if (isOutOfRight(coordinates.x,maxCoordinates.x) && callback != null){
            resetNextCollide();
            stepByX = - stepByX;
            callback.onRightPlayerGoal();
        } else if (isTopBottomCollide(coordinates.y, minCoordinates.y, maxCoordinates.y)) {
            stepByY = -stepByY; //change y moving direction
        } else if (leftRacketBounds.intersects(getBounds())) {
            resetNextCollide();
            if (stepByX < 0) {
                stepByX = -stepByX; // change x moving direction
            }
        } else if (rightRacketBounds.intersects(getBounds())) {
            resetNextCollide();
            if (stepByX > 0) {
                stepByX = - stepByX;
            }
        }
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
                } else if (isTopBottomCollide(currentPoint.y, minCoordinates.y, maxCoordinates.y)) {
                    stepY = -stepY;
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

    private boolean isTopBottomCollide(double ballY, double minY, double maxY) {
        return ballY <= minY || ballY >= maxY - ballDiameter;
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
