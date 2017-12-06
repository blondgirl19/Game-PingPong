package src.data.pojo.controllers;

import src.data.pojo.Dimension;
import src.data.pojo.game.Player;
import src.data.pojo.Point;
import resources.constants;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HumanController extends PlayerController implements KeyListener{
    private int upKeyCode, downKeyCode;

    public HumanController(Player player, int upKeyCode, int downKeyCode) {
        super(player);

        this.upKeyCode = upKeyCode;
        this.downKeyCode = downKeyCode;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!isPause) {
            int keyCode = e.getKeyCode();

            if (keyCode == upKeyCode) {
                player.setMoveDirection(constants.DIRECTION_UP);
            } else if (keyCode == downKeyCode) {
                player.setMoveDirection(constants.DIRECTION_DOWN);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!isPause) {
            int keyCode = e.getKeyCode();

            if (keyCode == upKeyCode || keyCode == downKeyCode) {
                player.setMoveDirection(constants.STOP_MOVING);
            }
        }
    }

    @Override
    public void update(Point ballCoordinates, Dimension dimension) {
        player.update(dimension);
    }
}
