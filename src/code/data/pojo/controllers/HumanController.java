package code.data.pojo.controllers;

import code.data.pojo.Dimension;
import code.data.pojo.game.Ball;
import code.data.pojo.game.Player;
import code.data.pojo.Point;
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
                player.setMoveState(constants.DIRECTION_UP);
            } else if (keyCode == downKeyCode) {
                player.setMoveState(constants.DIRECTION_DOWN);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!isPause) {
            int keyCode = e.getKeyCode();

            if (keyCode == upKeyCode || keyCode == downKeyCode) {
                player.setMoveState(constants.STOP_MOVING);
            }
        }
    }

    @Override
    public void update(Ball ball, Dimension dimension, Point minCoordinates, Point maxCoordinates) {
        player.update(dimension);
    }
}
