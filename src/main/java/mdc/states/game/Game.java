package mdc.states.game;

public interface Game {
    int getPlayerScore();

    int getLives();

    boolean isPaused();

    void checkForPause();

    boolean isLevelFinished();

    boolean isPlayerAlive();

    void resetDestroyedPlayer();

    void moveToNextLevel();
}
