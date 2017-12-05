package code.data.pojo.game;


import code.data.pojo.Dimension;
import code.data.pojo.Point;
import resources.constants;

import java.awt.*;

import static resources.constants.*;

public class Racket implements Scalable {
    private int moveDirection;
    private Dimension racketSize;
    private Point coordinates;
    private double oneStepInPX;

    public Racket(Dimension racketSize, double oneStepInPX) {
        this.coordinates = new Point(UNDEFINED_INT, UNDEFINED_INT);
        this.racketSize = racketSize;
        this.oneStepInPX = oneStepInPX;
        this.moveDirection = STOP_MOVING;
    }

    public void setMoveDirection(int direction) {
        this.moveDirection = direction;
    }

    private double getCurrentStep() {
        switch (moveDirection) {
            case DIRECTION_UP:
                return -oneStepInPX;
            case DIRECTION_DOWN:
                return oneStepInPX;
            default:
                return 0;
        }
    }

    public void moveTowardsDirection(code.data.pojo.Dimension tableDimension) {
        double tableHeight = tableDimension.getHeight();

        if (isWithinTheScreen(tableHeight)) {
            coordinates.y += getCurrentStep();
        } else if (isHitTop() && moveDirection == DIRECTION_DOWN) {
            coordinates.y += oneStepInPX;
        } else if (isHitBottom(tableHeight) && moveDirection == DIRECTION_UP) {
            coordinates.y -= oneStepInPX;
        }
    }

    private boolean isWithinTheScreen(double tableHeight) {
        return !isHitTop() && !isHitBottom(tableHeight);
    }

    private boolean isHitTop() {
        return coordinates.y <= 0;
    }

    private boolean isHitBottom(double tableHeight){
        return coordinates.y + racketSize.height >= tableHeight;
    }

    public code.data.pojo.Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(code.data.pojo.Point coordinates) {
        this.coordinates = coordinates;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) coordinates.x, (int) coordinates.y, (int) racketSize.width, (int) racketSize.height);
    }

    public void repaint(Graphics g) {
        g.fillRect((int) coordinates.x, (int) coordinates.y, (int) racketSize.width, (int) racketSize.height);
    }

    public Dimension getRacketSize() {
        return racketSize;
    }

    public void setRacketSize(code.data.pojo.Dimension racketSize) {
        this.racketSize = racketSize;
    }

    public boolean isInitialized() {
        return coordinates.x != UNDEFINED_INT && coordinates.y != UNDEFINED_INT;
    }

    @Override
    public void onScreenResized(double scaleX, double scaleY) {
        coordinates.x *= scaleX;
        coordinates.y *= scaleY;

        racketSize.width *= scaleX;
        racketSize.height *= scaleY;

        oneStepInPX *= scaleY;
    }

    public void removeFromField() {
        coordinates = new Point(constants.UNDEFINED_INT, constants.UNDEFINED_INT);
    }

    public double getRacketCenterY() {
        return coordinates.y + racketSize.height / 2;
    }

    public double getRacketCenterX() {
        return coordinates.x + racketSize.width/2;
    }
}