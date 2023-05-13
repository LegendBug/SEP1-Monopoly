package mdc.listeners;

import java.awt.*;

public interface MousesCommand {
    boolean hasClickedButton1();

    boolean hasPressedButton1();

    boolean hasReleasedButton1();

    boolean hasEnteredButton1();

    boolean hasExitButton1();

    Point getMousePoint();

    void resetMousePressed();


}
