package resources;

import java.awt.event.KeyEvent;

public interface constants {
    int MIN_FRAME_WIDTH = 900;
    int MIN_FRAME_HEIGHT = 550;
    int MIN_TABLE_PANEL_HEIGHT = 391;

    int TITLE_MARGINS = 7;
    int SHADOW_MARGINS = 2;
    int BORDER_THICKNESS = 2;
    int LIST_ITEMS_SPACING = 5;

    int PLAYERS_COUNT = 2;
    int TITLE_TRANSPARENT_MARGINS = 23;

    int TABLE_MARGINS = 10;
    int TABLE_BORDER = 5;

    int STOP_MOVING = 0;
    int DIRECTION_UP = 1;
    int DIRECTION_DOWN = 2;

    int DEFAULT_RACKET_WIDTH = 10, DEFAULT_RACKET_HEIGHT = 100;

    int LEFT_PLAYER_UP_CODE = KeyEvent.VK_W;
    int LEFT_PLAYER_DOWN_CODE = KeyEvent.VK_S;

    int RIGHT_PLAYER_UP_CODE = KeyEvent.VK_UP;
    int RIGHT_PLAYER_DOWN_CODE = KeyEvent.VK_DOWN;

    int UNDEFINED_INT = -1;

    int HUMAN = 1;
    int COMPUTER_HARD = 4;
    int COMPUTER_MEDIUM = 28;
    int COMPUTER_EASY = 80;

    int SIDE_LEFT = 1;
    int SIDE_RIGHT = 2;

    int DEFAULT_GAME_FPS = 60;
    double DEFAULT_RACKET_STEP_PX = 6.6;
    double DEFAULT_BALL_STEP_IN_PX = 8.33;
    int DEFAULT_SCORES_TO_WIN = 3;
    int DEFAULT_BAL_DIAMETER = 18;
    int MILLIS_IN_ONE_SECOND = 1000;

    int STATE_PAUSED = 1;
    int STATE_TIMER = 2;
    int STATE_NOTHING = 3;
    int STATE_GAME = 4;
    int STATE_GAME_ENDED = 5;

    int TIMER_DELAY = 400;
    int TIMER_TICKS = 3;
}
