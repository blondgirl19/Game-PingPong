package code.data.pojo.controllers;

import code.data.pojo.Dimension;
import code.data.pojo.game.Ball;
import code.data.pojo.game.Player;
import code.data.pojo.Point;
import code.data.pojo.game.Racket;
import resources.constants;

import static resources.constants.*;

public class ComputerController extends PlayerController {
    private int delaysToUpdate;
    private int updateDelayCounter;
    private double viewRange;
    private double targetY;

    public ComputerController(Player player) {
        super(player);

        this.delaysToUpdate = player.getType();
        this.updateDelayCounter = 0;
        viewRange = player.getType() / constants.RANGE_DIVIDER;
    }

    @Override
    public void update(Ball ball, Dimension dimension, Point minCoordinates, Point maxCoordinates) {
        if (isBallVisible(ball, dimension) && isBallMoveToPlayer(ball)) {
            targetY = calculateBallFinalY(ball, minCoordinates, maxCoordinates);
        } else {
            targetY = dimension.height / 2; // если мяча не видно, возвращает ракетку в центр поля
        }

        moveToTarget();
        player.update(dimension);
    }

    private double calculateBallFinalY(Ball ball, Point minCoordinates, Point maxCoordinates) {
        Racket racket = player.getRacket();
        switch (player.getSide()) {
            case SIDE_LEFT:
                minCoordinates.x = racket.getCoordinates().x + racket.getRacketSize().width;
                break;
            case SIDE_RIGHT:
                maxCoordinates.x = racket.getCoordinates().x;
                break;
        }

        return ball.getNextOutOfBorderCoordinateY(minCoordinates, maxCoordinates);
    }

    private void moveToTarget() {
        Racket racket = player.getRacket();
        double halfRacketHeight = racket.getRacketSize().height / 2;

        double racketStartY = racket.getCoordinates().y + halfRacketHeight / 4;
        double racketEndY = racketStartY + halfRacketHeight;

        if (targetY >= racketStartY && targetY <= racketEndY) {
            player.setMoveState(STOP_MOVING);
        } else if (targetY < racketStartY) {
            player.setMoveState(DIRECTION_UP);
        } else if (targetY > racketEndY) {
            player.setMoveState(DIRECTION_DOWN);
        }
    }

    private boolean isBallVisible(Ball ball, Dimension tableDimension) {
        double visibilityDistance = viewRange * tableDimension.width;

        double ballCenterX = ball.getCenterX();

        switch (player.getSide()) {
            case SIDE_LEFT:
                return visibilityDistance >= ballCenterX;
            case SIDE_RIGHT:
                return (tableDimension.width - visibilityDistance) <= ballCenterX;
        }

        return false;
    }

    private boolean isBallMoveToPlayer(Ball ball) {
        int ballDirection = ball.getXMoveDirection();
        int playerSide = player.getSide();

        switch (playerSide) {
            case SIDE_LEFT:
                return ballDirection == DIRECTION_LEFT;
            case SIDE_RIGHT:
                return ballDirection == DIRECTION_RIGHT;
        }

        return false;
    }
}