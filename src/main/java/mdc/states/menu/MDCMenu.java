package mdc.states.menu;

import mdc.components.buttons.Button;
import mdc.listeners.KeysListener;
import mdc.listeners.MousesListener;
import mdc.screens.MenuScreen;
import mdc.states.State;
import mdc.tools.Config;

import java.util.ArrayList;

public class MDCMenu implements State {
    private int currButton;
    private Button gameButton;
    private Button exitButton;
    private ArrayList<Button> buttons = new ArrayList<>();


    private KeysListener keysListener;
    private MousesListener mousesListener;
    private Config config;

    private boolean isStateOver;

    public MDCMenu(KeysListener keysListener, MousesListener mousesListener, Config config) {
        this.config = config;
        this.keysListener = keysListener;
        this.mousesListener = mousesListener;
    }

    @Override
    public int getScreenWidth() {
        return MenuScreen.screenWidth;
    }

    @Override
    public int getScreenHeight() {
        return MenuScreen.screenHeight;
    }

    public int getCurrButton() {
        return currButton;
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public ArrayList<Button> getButtonsCopy() {
        return new ArrayList<Button>(buttons);
    }


    @Override
    public boolean isStateOver() {
        for (Button button : buttons) {
            if (button.isIfActive()) return true;
        }
        return false;
    }

    @Override
    public void listenerController() {
        boolean[] keysListenerBos = keysListener.getBos();
        boolean[] mouseListenerBos = mousesListener.getBos();
        // Directional key control
        if (keysListenerBos[4])
            currButton = (currButton + 1) % buttons.size();
        else if (keysListenerBos[5])
            currButton = (currButton == -1 || currButton == 0) ? buttons.size() - 1 : currButton - 1;
        // Mouse control + determine the status
        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            button.setSelected(i == currButton);
            if (button.checkForState(mousesListener.getMousePoint(), mouseListenerBos[1],
                    mouseListenerBos[2], keysListenerBos[1])) {
                currButton = i;
                button.setSelected(true); // Mouse hover and select
            } else if (keysListenerBos[0] || mouseListenerBos[0]) {
                currButton = -1;
                button.setSelected(false);
            }
            if (i != currButton) buttons.get(i).setSelected(false); // Set other cards to unchecked
        }
    }

    @Override
    public void startState() {
        this.currButton = -1;
        this.gameButton = new Button(MenuScreen.gameRect, MenuScreen.gameStart1, MenuScreen.gameStart2, MenuScreen.gameStart3);
        this.exitButton = new Button(MenuScreen.exitRect, MenuScreen.exit1, MenuScreen.exit2, MenuScreen.exit3);
        this.buttons.add(gameButton);
        this.buttons.add(exitButton);
    }

    @Override
    public void updateState() {
        listenerController();
    }

    @Override
    public void moveToOtherStates() {
        keysListener.resetKeyPresses();
        mousesListener.resetMousePressed();
    }

    public boolean isChosenGameSrc() {
        if (gameButton.isIfSelected()) {
            buttons.clear();
            return true;
        }
        return false;
    }

    public boolean isChosenExit() {
        return exitButton.isIfSelected();
    }
}
