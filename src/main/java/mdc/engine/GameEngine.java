package mdc.engine;

import mdc.states.game.MDCGame;
import mdc.states.menu.MDCMenu;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The GameEngine class is the main engine for running the game. It handles the switching
 * between different game states, updating game states, and rendering game screens.
 */
public class GameEngine {
    private JFrame mainWindow;
    private JPanel mainPanel;
    private JPanel menuScreen;
    private JPanel gameScreen;
    private CardLayout cardLayout;
    private MDCMenu menu;
    private MDCGame game;
    private Screens screens;
    private ScheduledExecutorService updateExecutor;
    private ScheduledExecutorService renderExecutor;

    public GameEngine(MDCGame game, MDCMenu menu, JFrame applicationWindow, JPanel menuScr, JPanel gameScr) {
        this.screens = Screens.MENU_SCREEN;
        this.mainWindow = applicationWindow;
        this.menu = menu;
        this.game = game;
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(this.cardLayout);
        this.mainWindow.getContentPane().add(this.mainPanel);
        this.mainPanel.add(menuScr, "Menu");
        this.mainPanel.add(gameScr, "Game");
        this.menuScreen = menuScr;
        this.gameScreen = gameScr;
        this.showMainMenuScreen();
    }

    // Display the main menu screen
    private void showMainMenuScreen() {
        this.menu.startState();
        this.cardLayout.show(this.mainPanel, "Menu");
        this.screens = Screens.MENU_SCREEN;
    }

    // Display the game screen
    private void showGameScreen() {
        this.game.startState();
        this.cardLayout.show(this.mainPanel, "Game");
        this.screens = Screens.GAME_SCREEN;
    }

    // Start the game engine
    public void run() {
        updateExecutor = Executors.newScheduledThreadPool(1);
        renderExecutor = Executors.newScheduledThreadPool(1);

        // Scheduled task for updating game states
        updateExecutor.scheduleAtFixedRate(() -> {
            try {
                switch (this.screens) {
                    case GAME_SCREEN -> {
                        if (!this.game.isStateOver()) {
                            this.game.updateState();
                        } else {
                            this.showMainMenuScreen();
                            this.game.moveToOtherStates();
                        }
                    }
                    case MENU_SCREEN -> {
                        if (!this.menu.isStateOver()) {
                            this.menu.updateState();
                        } else {
                            if (this.menu.isChosenGameSrc()) {
                                this.showGameScreen();
                            } else if (this.menu.isChosenExit()) {
                                this.exitGame();
                            }
                            this.menu.moveToOtherStates();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 17, TimeUnit.MILLISECONDS);

        // Scheduled task for rendering game screens
        renderExecutor.scheduleAtFixedRate(() -> {
            try {
                switch (this.screens) {
                    case GAME_SCREEN ->
                            this.gameScreen.paintImmediately(0, 0, this.game.getScreenWidth(), this.game.getScreenHeight());
                    case MENU_SCREEN ->
                            this.menuScreen.paintImmediately(0, 0, this.menu.getScreenWidth(), this.menu.getScreenHeight());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 17, TimeUnit.MILLISECONDS);
    }

    // Exit the game
    public void exitGame() {
        this.renderExecutor.shutdown();
        this.updateExecutor.shutdown();
        System.exit(0);
    }
}
