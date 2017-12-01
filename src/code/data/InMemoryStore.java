package code.data;

import code.data.pojo.players.BasePlayer;
import code.data.pojo.players.ComputerPlayer;
import code.data.pojo.players.HumanPlayer;
import resources.strings;

public class InMemoryStore {
    private static BasePlayer leftPlayer, rightPlayer;

    public void savePlayers(BasePlayer leftPlayer, BasePlayer rightPlayer) {
        InMemoryStore.leftPlayer = leftPlayer;
        InMemoryStore.rightPlayer = rightPlayer;
    }

    public BasePlayer getLeftPlayer() {
        if (InMemoryStore.leftPlayer == null) {
            InMemoryStore.leftPlayer = new ComputerPlayer("Roger", strings.MEDIUM);
        }

        return InMemoryStore.leftPlayer;
    }

    public BasePlayer getRightPlayer() {
        if (InMemoryStore.rightPlayer == null) {
            InMemoryStore.rightPlayer = new HumanPlayer("Simple Human");
        }

        return InMemoryStore.rightPlayer;
    }
}
