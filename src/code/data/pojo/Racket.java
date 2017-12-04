package code.data.pojo;

import java.awt.*;

import static resources.constants.*;

public class Racket {
    private int moveDirection;
    private int racketWidth, racketHeight;
    private double currentX, currentY;
    private double oneStepInPX;

    public Racket(int startX, int startY, int racketWidth, int racketHeight, double oneStepInPX) {
        this.currentX = startX;
        this.currentY = startY;
        this.racketHeight = racketHeight;
        this.racketWidth = racketWidth;
        this.oneStepInPX = oneStepInPX;
        this.moveDirection = STOP_MOVING;
    }

    public Racket(int startX, int startY) {
        this(startX, startY, DEFAULT_RACKET_WIDTH, DEFAULT_RACKET_HEIGHT, DEFAULT_RACKET_STEP_PX);
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

    public void update(int tableWidth, int tableHeight) {
        if (isHitTop(tableHeight)) {
            currentY += oneStepInPX;
        } else if (isHitBottom(tableHeight)) {
            currentY -= oneStepInPX;
        } else {                        //is within the screen
            currentY += getCurrentStep();
        }
    }

    private boolean isHitTop(int tableHeight){
        return currentY <= tableHeight;
    }

    private boolean isHitBottom(int tableHeight){
        return currentY >= (tableHeight - racketHeight);
    }

    public Rectangle getBounds() {
        return new Rectangle((int) currentX, (int)currentY, racketWidth, racketHeight);
    }

    public void repaint(Graphics g) {
        g.fillRect((int)currentX, (int)currentY, racketWidth, racketHeight);
    }
}