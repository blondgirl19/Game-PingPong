package src.data.pojo.controllers;

import src.data.pojo.Dimension;
import src.data.pojo.game.Player;
import src.data.pojo.Point;

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
    public void update(Point ballCoordinates, Dimension dimension) {
        if (++updateDelayCounter == delaysToUpdate) {
            double ballCoordinateY = ballCoordinates.y;
            double racketCoordinateY = player.getRacket().getRacketCenterY();

            if (ballCoordinateY < racketCoordinateY)
                player.setMoveDirection(DIRECTION_UP);
            else if (ballCoordinateY > racketCoordinateY)
                player.setMoveDirection(DIRECTION_DOWN);
            else
                player.setMoveDirection(STOP_MOVING);

            updateDelayCounter = 0;
        }

        player.update(dimension);
    }
}