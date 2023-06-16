package mdc.components.buttons;

import java.awt.*;

/**
 * The button class controlled in the game
 */
public class Button {
    private final Rectangle rect;
    private final Image normal;
    private final Image hover;
    private final Image pressed;
    private ButtonStates state;
    private boolean ifSelected;
    private boolean ifActive;

    public Button(Rectangle rect, Image normal, Image hover, Image pressed) {
        this.rect = rect;
        this.normal = normal;
        this.hover = hover;
        this.pressed = pressed;
        this.state = ButtonStates.NORMAL;
        this.ifSelected = false; // Whether any and only the current key is selected (externally controlled, internally used)
        this.ifActive = false; // Whether the key is activated
    }

    public Rectangle getRect() {
        return rect;
    }

    public Image getImage() {
        switch (state) {
            case NORMAL -> {
                return normal;
            }
            case HOVER -> {
                return hover;
            }
            case PRESSED ->
            {
                return pressed;
            }
        }
        return null;
    }

    public boolean isIfSelected() {
        return ifSelected;
    }

    public boolean isIfActive() {
        return ifActive;
    }

    public void setSelected(boolean bo) {
        this.ifSelected = bo;
    }

    public void setActive(boolean bo) {
        this.ifActive = bo;
    }

    /**
     * Control the current status of the key
     * @param mousePoint Mouse coordinates
     * @param ifPressed mouse click
     * @param ifReleased Mouse release
     * @param ifEnter Press Enter
     * @return Returns true when the mouse is over the key
     */
    public boolean checkForState(Point mousePoint, boolean ifPressed, boolean ifReleased, boolean ifEnter) {
        if (mousePoint != null && this.rect.contains(mousePoint)) {
            if (ifPressed) this.state = ButtonStates.PRESSED; // Mouse down, press
            else this.state = ButtonStates.HOVER; // Mouse stay, stay
            if (ifReleased || ifEnter) ifActive = true; // Release the mouse or press Enter to go
            return true; // With mouse in range, return true
        } else {
            if (this.ifSelected) { //
                if (ifEnter) this.ifActive = true; // The mouse is not on the button, control is left to the button
                else this.state = ButtonStates.HOVER; // Press the button to select and stay
            } else this.state = ButtonStates.NORMAL; // If no, the status is normal
        }
        return false;
    }

    public void resetButton() {
        this.state = ButtonStates.NORMAL;
        this.ifSelected = false;
        this.ifActive = false;
    }
}
