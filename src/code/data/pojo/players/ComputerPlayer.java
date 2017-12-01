package code.data.pojo.players;

import resources.strings;

public class ComputerPlayer extends BasePlayer {
    private String difficult;

    public ComputerPlayer(String name, String difficult) {
        super(name, strings.COMPUTER);
        this.difficult = difficult;
    }

    @Override
    public void update(int ballX, int ballY) {

    }

    @Override
    public void endGame() {

    }
}
