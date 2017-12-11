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
    int DIRECTION_LEFT = 3;
    int DIRECTION_RIGHT = 4;

    int MIN_RACKET_HEIGHT = 40;
    int DEFAULT_RACKET_WIDTH = 15, DEFAULT_RACKET_HEIGHT = 100;
    int MAX_RACKET_HEIGHT = 300;

    int LEFT_PLAYER_UP_CODE = KeyEvent.VK_W;
    int LEFT_PLAYER_DOWN_CODE = KeyEvent.VK_S;

    int RIGHT_PLAYER_UP_CODE = KeyEvent.VK_UP;
    int RIGHT_PLAYER_DOWN_CODE = KeyEvent.VK_DOWN;

    int UNDEFINED_INT = -1;
    double UNDEFINED_DOUBLE = -1;

    int HUMAN = 1;
    int COMPUTER_HARD = 8;
    int COMPUTER_MEDIUM = 5;
    int COMPUTER_EASY = 3;
    int RANGE_DIVIDER = 20;

    int SIDE_LEFT = 1;
    int SIDE_RIGHT = 2;
    int SIDE_CENTER = 3;

    int DEFAULT_GAME_FPS = 60;

    int MIN_RACKET_STEP_PX = 1;
    double DEFAULT_RACKET_STEP_PX = 6.6;
    int MAX_RACKET_STEP_PX = 20;

    int MIN_BALL_STEP_PX = 1;
    double DEFAULT_BALL_STEP_IN_PX = 8.33;
    int MAX_BALL_STEP_PX = 30;

    int MIN_SCORES_TO_WIN = 1;
    int DEFAULT_SCORES_TO_WIN = 10;
    int MAX_SCORES_TO_WIN = 100;

    int MIN_BALL_DIAMETER = 5;
    int DEFAULT_BAL_DIAMETER = 18;
    int MAX_BALL_DIAMETER = 50;
    int MILLIS_IN_ONE_SECOND = 1000;

    int STATE_PAUSED = 1;
    int STATE_TIMER = 2;
    int STATE_GAME = 4;
    int STATE_GAME_ENDED = 5;

    int TIMER_DELAY = 400;
    int TIMER_TICKS = 3;

    double PERCENT_2 = 0.02;
    double PERCENT_102 = 1.02;
    double PERCENT_105 = 1.05;
    double PERCENT_10 = 0.1;

    int POSITIVE = 1;
    int NEGATIVE = -1;
}
