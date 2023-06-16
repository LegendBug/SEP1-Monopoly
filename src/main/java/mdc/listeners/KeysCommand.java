package mdc.listeners;

public interface KeysCommand {
    boolean hasPressedExit();

    boolean hasPressedEnter();

    boolean hasPressedUp();

    boolean hasPressedDown();

    boolean hasPressedRight();

    boolean hasPressedLeft();

    boolean hasPressedSpace();

    void resetKeyPresses();
}