package listeners;

public interface KeysCommand {
    boolean hasPressedExit();

    boolean hasPressedEnter();

    boolean hasPressedUp();

    boolean hasPressedDown();

    boolean hasPressedRight();

    boolean hasPressedLeft();

    void resetKeyPresses();
}