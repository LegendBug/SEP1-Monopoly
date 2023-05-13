package mdc.states.menu;

import mdc.listeners.KeysListener;
import mdc.listeners.MousesListener;
import mdc.screenpainters.MenuScreen;
import mdc.states.State;

import java.awt.*;

public class MDCMenu implements State {
    private static final int SCREEN_WIDTH = 960;
    private static final int SCREEN_HEIGHT = 960;
    private final KeysListener keysListener;
    private final MousesListener mousesListener;
    private MenuItem menuItem;

    public MDCMenu(KeysListener keysListener, MousesListener mousesListener) {
        this.keysListener = keysListener;
        this.mousesListener = mousesListener;
        startState();
    }

    @Override
    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    @Override
    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    private void nextItem() {
        switch (menuItem) {
            case GameScr -> menuItem = MenuItem.HelpScr;
            case HelpScr -> menuItem = MenuItem.ScoreScr;
            case ScoreScr -> menuItem = MenuItem.Exit;
            case Exit -> menuItem = MenuItem.GameScr;
        }
    }

    private void prevItem() {
        switch (menuItem) {
            case GameScr -> menuItem = MenuItem.Exit;
            case HelpScr -> menuItem = MenuItem.GameScr;
            case ScoreScr -> menuItem = MenuItem.HelpScr;
            case Exit -> menuItem = MenuItem.ScoreScr;
        }
    }

    public boolean isChosenScoreScr() {
        return menuItem == MenuItem.ScoreScr;
    }

    public boolean isChosenHelpSrc() {
        return menuItem == MenuItem.HelpScr;
    }

    public boolean isChosenGameSrc() {
        return menuItem == MenuItem.GameScr;
    }

    public boolean isChosenExit() {
        return menuItem == MenuItem.Exit;
    }

    @Override
    public boolean isStateOver() {
        if (mousesListener.hasReleasedButton1()) {
            return MenuScreen.NEW_GAME_RECT.contains(mousesListener.getMousePoint()) || MenuScreen.ABOUT_RECT.contains(mousesListener.getMousePoint())
                    || MenuScreen.SCORE_RECT.contains(mousesListener.getMousePoint()) || MenuScreen.EXIT_RECT.contains(mousesListener.getMousePoint());
        }
        return keysListener.hasPressedEnter();
    }

    @Override
    public void buttonController() {
        if (keysListener.hasPressedUp()) {
            prevItem();
            System.out.println(menuItem);
        } else if (keysListener.hasPressedDown()) {
            nextItem();
            System.out.println(menuItem);
        }

        if (mousesListener.getMousePoint() != null) {
            if (MenuScreen.NEW_GAME_RECT.contains(mousesListener.getMousePoint())) {
                menuItem = MenuItem.GameScr;
            } else if (MenuScreen.ABOUT_RECT.contains(mousesListener.getMousePoint())) {
                menuItem = MenuItem.HelpScr;
            } else if (MenuScreen.SCORE_RECT.contains(mousesListener.getMousePoint())) {
                menuItem = MenuItem.ScoreScr;
            } else if (MenuScreen.EXIT_RECT.contains(mousesListener.getMousePoint())) {
                menuItem = MenuItem.Exit;
            }
        }
    }

    @Override
    public void startState() {
        menuItem = MenuItem.GameScr;
    }

    @Override
    public void updateState() {
        buttonController();
    }

    @Override
    public void moveToOtherStates() {
        keysListener.resetKeyPresses();
        mousesListener.resetMousePressed();
    }

    private enum MenuItem {
        ScoreScr, HelpScr, GameScr, Exit
    }
}
