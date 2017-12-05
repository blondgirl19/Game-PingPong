package code.data.pojo.game;

import code.data.pojo.Point;
import resources.constants;

import java.awt.*;

public class Ball implements Scalable {
    private BallCallback callback;

    private double stepInPX = 2.5;
    private double ballDiameter = 15;
    private Point coordinates;

    private double stepByX, stepByY;

    public Ball(Point startCoordinates) {
        stepInPX = constants.DEFAULT_BALL_STEP_IN_PX;
        stepByX = stepInPX;
        stepByY = stepInPX;
        respawn(startCoordinates);
    }

    public Ball() {
        this(new Point(constants.UNDEFINED_INT, constants.UNDEFINED_INT));
    }

    public void setCallback(BallCallback callback) {
        this.callback = callback;
    }

    public void setStepInPX(double stepInPX) {
        this.stepInPX = stepInPX;
    }

    public void respawn(Point startCoordinates){
        coordinates = startCoordinates;
    }

    @Override
    public void onScreenResized(double scaleX, double scaleY) {
        Double minScale = Math.min(scaleX, scaleY);
        ballDiameter *= ballDiameter * minScale;

        coordinates.x *= scaleX;
        coordinates.y *= scaleY;
    }

    private void updateLocation(){
        coordinates.x += stepByX;
        coordinates.y += stepByY;
    }

    public void update(Rectangle leftRacketBounds, Rectangle rightRacketBounds, Point minCoordinates, Point maxCoordinates) {
        updateLocation();

        if (isOutOfLeft(minCoordinates.x) && callback != null) {
            stepByX = - stepByX;
            callback.onLeftPlayerGoal();
        } else if (isOutOfRight(maxCoordinates.x) && callback != null){
            stepByX = - stepByX;
            callback.onRightPlayerGoal();
        } else if (isTopBottomCollide(minCoordinates.y, maxCoordinates.y)) {
            stepByY = -stepByY; //change y moving direction
        } else if (leftRacketBounds.intersects(getBounds())) {
            if (stepByX < 0) {
                stepByX = -stepByX; // change x moving direction
            }
        } else if (rightRacketBounds.intersects(getBounds())) {
            if (stepByX > 0) {
                stepByX = - stepByX;
            }
        }
    }

    public Point getCoordinates() {
        return new Point(getCenterX(), getCenterY());
    }

    private boolean isOutOfRight(double x) {
        return getCenterX() >= x - ballDiameter/2;
    }

    private boolean isOutOfLeft(double x) {
        return getCenterX() <= x;
    }

    private boolean isTopBottomCollide(double minY, double maxY) {
        return getCenterY() < minY || getCenterY() >= maxY - ballDiameter;
    }

    public Rectangle getBounds() {
        return new Rectangle((int)coordinates.x, (int)coordinates.y, (int) ballDiameter, (int) ballDiameter);
    }

    public void repaint(Graphics g) {
        g.fillOval((int)getCenterX(), (int)getCenterY(), (int) ballDiameter, (int) ballDiameter);
    }

    public double getCenterX() {
        return coordinates.x - ballDiameter/2;
    }

    public double getCenterY() {
        return coordinates.y - ballDiameter/2;
    }

    public interface BallCallback {
        void onLeftPlayerGoal();
        void onRightPlayerGoal();
    }
}
