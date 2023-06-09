package mdc.states.history;

import mdc.listeners.KeysListener;
import mdc.screens.HistoryScreen;
import mdc.states.State;
import mdc.tools.Config;

public class MDCHistory implements State {
    private final Config config;
    private final KeysListener keysListener;

    public MDCHistory(KeysListener keysListener, Config config) {
        this.config = config;
        this.keysListener = keysListener;
        startState();
    }

    @Override
    public int getScreenWidth() {
        return HistoryScreen.SCREEN_WIDTH;
    }

    @Override
    public int getScreenHeight() {
        return HistoryScreen.SCREEN_HEIGHT;
    }

    @Override
    public void listenerController() {

    }

    @Override
    public void startState() {

    }

    @Override
    public void updateState() {
        listenerController();
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
