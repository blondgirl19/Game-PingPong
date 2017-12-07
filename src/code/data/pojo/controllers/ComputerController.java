package code.data.pojo.controllers;

import code.data.pojo.Dimension;
import code.data.pojo.game.Ball;
import code.data.pojo.game.Player;
import code.data.pojo.Point;
import code.data.pojo.game.Racket;

import static resources.constants.*;

public class ComputerController extends PlayerController {
    private int delaysToUpdate;
    private int updateDelayCounter;

    public ComputerController(Player player) {
        super(player);

        this.delaysToUpdate = player.getType();
        this.updateDelayCounter = 0;
    }

    @Override
    public void update(Ball ball, Dimension dimension, Point minCoordinates, Point maxCoordinates) {
        if (++updateDelayCounter == delaysToUpdate) {

            Racket racket = player.getRacket();
            double racketStartY = racket.getCoordinates().y;
            double racketEndY = racketStartY + racket.getRacketSize().height;

            if (player.getSide() == SIDE_LEFT) {
                minCoordinates.x = racket.getCoordinates().x;
            } else {
                maxCoordinates.x = racket.getCoordinates().x;
            }
            double ballOutY = ball.getNextOutOfBorderCoordinateY(minCoordinates, maxCoordinates);

            if (ballOutY > racketStartY && ballOutY < racketEndY) {
                player.setMoveDirection(STOP_MOVING);
            } else if (ballOutY < racketStartY) {
                player.setMoveDirection(DIRECTION_UP);
            } else if (ballOutY > racketEndY){
                player.setMoveDirection(DIRECTION_DOWN);
            }

            updateDelayCounter = 0;
        }

        player.update(dimension);
    }
}