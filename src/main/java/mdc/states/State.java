package mdc.states;

public interface State {
    int getScreenWidth();

    int getScreenHeight();

    void buttonController();

    void startState();

    void updateState();

    boolean isStateOver();

    void moveToOtherStates();
}
