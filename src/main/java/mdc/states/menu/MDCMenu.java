package mdc.states.menu;

import mdc.listeners.KeysListener;
import mdc.listeners.MousesListener;
import mdc.screenpainters.MenuScreen;
import mdc.states.ButtonStates;
import mdc.states.State;
import mdc.tools.Config;

public class MDCMenu implements State {
    private MenuButtons menuButton;
    private ButtonStates buttonState;
    private final Config config;
    private final KeysListener keysListener;
    private final MousesListener mousesListener;

    public MDCMenu(KeysListener keysListener, MousesListener mousesListener, Config config) {
        this.config = config;
        this.keysListener = keysListener;
        this.mousesListener = mousesListener;
        startState();
    }

    public MenuButtons getMenuButton() {
        return menuButton;
    }

    public ButtonStates getButtonState() {
        return buttonState;
    }

    @Override
    public int getScreenWidth() {
        return MenuScreen.SCREEN_WIDTH;
    }

    @Override
    public int getScreenHeight() {
        return MenuScreen.SCREEN_HEIGHT;
    }

    private void nextButton() {
        switch (menuButton) {
            case GameScr -> menuButton = MenuButtons.HistoryScr;
            case HistoryScr -> menuButton = MenuButtons.Exit;
            case Null, Exit -> menuButton = MenuButtons.GameScr;
        }
    }

    private void prevButton() {
        switch (menuButton) {
            case Null, GameScr -> menuButton = MenuButtons.Exit;
            case HistoryScr -> menuButton = MenuButtons.GameScr;
            case Exit -> menuButton = MenuButtons.HistoryScr;
        }
    }

    public boolean isChosenHistorySrc() {
        return menuButton == MenuButtons.HistoryScr;
    }

    public boolean isChosenGameSrc() {
        return menuButton == MenuButtons.GameScr;
    }

    public boolean isChosenExit() {
        return menuButton == MenuButtons.Exit;
    }

    @Override
    public boolean isStateOver() {
        if (mousesListener.hasReleasedButton1()) {
            return MenuScreen.GAME_RECT.contains(mousesListener.getMousePoint())
                    || MenuScreen.HISTORY_RECT.contains(mousesListener.getMousePoint())
                    || MenuScreen.EXIT_RECT.contains(mousesListener.getMousePoint());
        }

        return keysListener.hasPressedEnter();
    }

    @Override
    public void listenerController() {
        if (keysListener.hasPressedUp()) {
            prevButton();
            buttonState = ButtonStates.HOVER;
        } else if (keysListener.hasPressedDown()) {
            nextButton();
            buttonState = ButtonStates.HOVER;
        }

        if (mousesListener.getMousePoint() != null) {
            if (MenuScreen.GAME_RECT.contains(mousesListener.getMousePoint())) {
                menuButton = MenuButtons.GameScr;
                if (mousesListener.hasPressedButton1()) buttonState = ButtonStates.PRESSED;
                else buttonState = ButtonStates.HOVER;
            } else if (MenuScreen.HISTORY_RECT.contains(mousesListener.getMousePoint())) {
                menuButton = MenuButtons.HistoryScr;
                if (mousesListener.hasPressedButton1()) buttonState = ButtonStates.PRESSED;
                else buttonState = ButtonStates.HOVER;
            } else if (MenuScreen.EXIT_RECT.contains(mousesListener.getMousePoint())) {
                menuButton = MenuButtons.Exit;
                if (mousesListener.hasPressedButton1()) buttonState = ButtonStates.PRESSED;
                else buttonState = ButtonStates.HOVER;
            }
        }
    }

    @Override
    public void startState() {
        menuButton = MenuButtons.Null;
        buttonState = ButtonStates.NORMAL;
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

}
