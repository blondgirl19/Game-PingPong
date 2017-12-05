package code.data.pojo.controllers;

import code.data.pojo.Dimension;
import code.data.pojo.Point;
import code.data.pojo.game.Player;

public abstract class PlayerController {
    protected Player player;
    protected boolean isPause;

    public PlayerController(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void pauseControl(){
        isPause = true;
    }

    public void resumeControl(){
        isPause = false;
    }

    public abstract void update(Point ballCoordinates, Dimension dimension);

    public boolean isPlayerReady(){
        return player.getRacket().isInitialized();
    }
}
