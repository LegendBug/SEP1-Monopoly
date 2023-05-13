package mdc.states.score;

import mdc.listeners.KeysListener;
import mdc.states.State;

public class MDCScore implements State {
    private static final int SCREEN_WIDTH = 960;
    private static final int SCREEN_HEIGHT = 960;
    private final KeysListener keysListener;

    public MDCScore(KeysListener keysListener) {
        this.keysListener = keysListener;
    }

    @Override
    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    @Override
    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    @Override
    public void buttonController() {

    }

    @Override
    public void startState() {

    }

    @Override
    public void updateState() {
        buttonController();
    }

    @Override
    public boolean isStateOver() {
        return keysListener.hasPressedExit();
    }

    @Override
    public void moveToOtherStates() {
        keysListener.resetKeyPresses();
    }
}
