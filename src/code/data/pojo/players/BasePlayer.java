package code.data.pojo.players;

import code.data.pojo.Racket;

public abstract class BasePlayer {
    private String name;
    private String type;
    private int moveDirection;
    protected Racket racket;

    public BasePlayer(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public void setRacket(Racket racket) {
        this.racket = racket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMoveDirection(int direction) {
        moveDirection = direction;
    }

    public abstract void update(int ballX, int ballY, int tableWidth, int tableHeight);
    public abstract void endGame();
}