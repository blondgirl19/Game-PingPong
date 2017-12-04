package code.data.pojo;

import java.awt.*;

import static resources.constants.DIRECTION_DOWN;
import static resources.constants.DIRECTION_UP;
import static resources.constants.STOP_MOVING;

public class Racket {
    private int moveDirection;
    private int racketWidth, racketHeight;
    private int currentX, currentY;
    private int oneStepInPX;

    public Racket(int startX, int startY, int racketWidth, int racketHeight, int oneStepInPX) {
        this.currentX = startX;
        this.currentY = startY;
        this.racketHeight = racketHeight;
        this.racketWidth = racketWidth;
        this.oneStepInPX = oneStepInPX;
        this.moveDirection = STOP_MOVING;
    }

    public void setMoveDirection(int direction) {
        this.moveDirection = direction;
    }

    private int getCurrentStep() {
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
        return new Rectangle(currentX, currentY, racketWidth, racketHeight);
    }

    public void repaint(Graphics g) {
        g.fillRect(currentX, currentY, racketWidth, racketHeight);
    }
}