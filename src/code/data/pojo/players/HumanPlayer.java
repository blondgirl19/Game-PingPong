package code.data.pojo.players;

import resources.strings;

public class HumanPlayer extends BasePlayer {
    public HumanPlayer(String name) {
        super(name, strings.HUMAN);
    }

    @Override
    public void update(int ballX, int ballY, int tableWidth, int tableHeight) {

    }

    @Override
    public void endGame() {

    }
}
