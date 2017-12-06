package code.data.pojo.game;

import code.data.pojo.Point;
import resources.colors;
import resources.constants;

import java.awt.*;

public class Ball implements Scalable {
    private BallCallback callback;

    private double ballDiameter;
    private Point coordinates;

    private double stepByX, stepByY;

    public Ball(double stepInPX, double ballDiameter) {
        this.stepByX = stepInPX;
        this.stepByY = stepInPX;
        this.ballDiameter = ballDiameter;
        respawn(new Point(constants.UNDEFINED_INT, constants.UNDEFINED_INT));
    }

    public void setCallback(BallCallback callback) {
        this.callback = callback;
    }

    public void respawn(Point startCoordinates){
        coordinates = startCoordinates;
    }

    @Override
    public void onScreenResized(double scaleX, double scaleY) {
        double avgScale = (scaleX + scaleY) / 2;

        ballDiameter *= avgScale;

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

    public Point getCenterCoordinates() {
        return new Point(getCenterX(), getCenterY());
    }

    private boolean isOutOfRight(double x) {
        return coordinates.x >= x + ballDiameter/2; // will out of right bound on half width
    }

    private boolean isOutOfLeft(double x) {
        return coordinates.x <= x - ballDiameter/2; // will out of left bound on half width
    }

    private boolean isTopBottomCollide(double minY, double maxY) {
        return coordinates.y <= minY || coordinates.y >= maxY - ballDiameter;
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
