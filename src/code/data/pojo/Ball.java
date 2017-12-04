package code.data.pojo;

import java.awt.*;

public class Ball {
    private double stepInPX = 2.5;
    private int ballDiameter = 30;

    private double currentX, currentY;
    private double stepByX, stepByY;

    public Ball(int startX, int startY) {
        refresh(startX, startY);
    }

    public void setStepInPX(double stepInPX) {
        this.stepInPX = stepInPX;
    }

    private void refresh(int startX, int startY){
        currentX = startX;
        currentY = startY;
        stepByX = stepInPX;
        stepByY = stepInPX;
    }
}
