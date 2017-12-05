package code.data;

import code.data.pojo.game.Player;
import code.data.pojo.controllers.BasePlayerController;
import code.data.pojo.controllers.ComputerController;
import code.data.pojo.controllers.HumanController;
import resources.constants;

import static resources.constants.*;

public class InMemoryStore {
    private static BasePlayerController leftPlayerController, rightPlayerController;

    public void savePlayers(Player leftPlayer, Player rightPlayer) {
        leftPlayerController = createPlayerController(leftPlayer);
        rightPlayerController = createPlayerController(rightPlayer);
    }

    private BasePlayerController createPlayerController(Player player) {
        if (player.isHuman()) {
            switch (player.getSide()) {
                case SIDE_LEFT:
                    return new HumanController(player, LEFT_PLAYER_UP_CODE, LEFT_PLAYER_DOWN_CODE);

                case SIDE_RIGHT:
                default:
                    return new HumanController(player, RIGHT_PLAYER_UP_CODE, RIGHT_PLAYER_DOWN_CODE);
            }
        }

        return new ComputerController(player);
    }

    public Player getLeftPlayer() {
        if (leftPlayerController == null) {
            Player leftPlayer = new Player("Roger", constants.COMPUTER_MEDIUM, SIDE_LEFT);
            leftPlayerController = createPlayerController(leftPlayer);
        }

        return leftPlayerController.getPlayer();
    }

    public Player getRightPlayer() {
        if (rightPlayerController == null) {
            Player rightPlayer = new Player("Simple Human", HUMAN, SIDE_RIGHT);
            rightPlayerController = createPlayerController(rightPlayer);
        }

        return rightPlayerController.getPlayer();
    }

    public BasePlayerController getLeftPlayerController(){
        return leftPlayerController;
    }

    public BasePlayerController getRightPlayerController() {
        return rightPlayerController;
    }
}
