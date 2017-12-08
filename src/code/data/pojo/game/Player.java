package code.data.pojo.game;

import code.data.pojo.Dimension;

import java.awt.*;

import static resources.constants.HUMAN;

public class Player {
    private String name;
    private int type;
    private Racket racket;
    private int side;
    private Color racketColor;

    public Player(String name, int type, int side, Color racketColor) {
        this.name = name;
        this.type = type;
        this.side = side;
        this.racketColor = racketColor;
    }

    public boolean isHuman() {
        return type == HUMAN;
    }

    public void setRacket(Racket racket) {
        this.racket = racket;
    }

    public Racket getRacket() {
        return racket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSide() {
        return side;
    }

    public void setSide(int side) {
        this.side = side;
    }

    public Color getRacketColor() {
        return racketColor;
    }

    public void setRacketColor(Color racketColor) {
        this.racketColor = racketColor;
    }

    public void setMoveState(int direction) {
        racket.setMoveState(direction);
    }

    public void repaint(Graphics g) {
        g.setColor(racketColor);
        racket.repaint(g);
    }

    public void onScreenResized(double scaleX, double scaleY) {
        racket.onScreenResized(scaleX, scaleY);
    }

    public void update(Dimension dimension) {
        racket.moveTowardsDirection(dimension);
    }
}