package mdc.engine;

import mdc.states.game.MDCGame;
import mdc.states.menu.MDCMenu;
import mdc.states.history.MDCHistory;
import mdc.tools.Config;
import mdc.tools.ScoreKeeper;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameEngine {
    private static final long RENDER_TIME = 17L;
    private static final long UPDATE_TIME = 30L; // 更新频率总是大于或等于渲染频率
    private static final int MAX_RENDER_TIMES = 5;
    private Config config;
    private JFrame mainWindow;
    private JPanel mainPanel;
    private JPanel menuScreen;
    private JPanel historyScreen;
    private JPanel gameScreen;
    private CardLayout cardLayout;
    private ScoreKeeper scoreKeeper;
    private MDCMenu menu;
    private MDCHistory history;
    private MDCGame game;
    private Screens screens;
    private ScheduledExecutorService updateExecutor;
    private ScheduledExecutorService renderExecutor;

    public GameEngine(MDCGame game, MDCMenu menu, MDCHistory history, JFrame applicationWindow, JPanel menuScr, JPanel historyScr, JPanel gameScr, ScoreKeeper scoreKeep, Config config) {
        this.config = config;
        this.screens = Screens.MENU_SCREEN;
        this.mainWindow = applicationWindow;
        this.menu = menu;
        this.history = history;
        this.game = game;
        this.scoreKeeper = scoreKeep;
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(this.cardLayout);
        this.mainWindow.getContentPane().add(this.mainPanel);
        this.mainPanel.add(menuScr, "Menu");
        this.mainPanel.add(historyScr, "History");
        this.mainPanel.add(gameScr, "Game");
        this.menuScreen = menuScr;
        this.historyScreen = historyScr;
        this.gameScreen = gameScr;
        this.showMainMenuScreen();
    }

    private void showMainMenuScreen() {
        this.menu.startState();
        this.cardLayout.show(this.mainPanel, "Menu");
        this.screens = Screens.MENU_SCREEN;
    }

    private void showScoreScreen() {
        this.history.startState();
        this.cardLayout.show(this.mainPanel, "History");
        this.screens = Screens.HISTORY_SCREEN;
    }

    private void showGameScreen() {
        this.game.startState();
        this.cardLayout.show(this.mainPanel, "Game");
        this.screens = Screens.GAME_SCREEN;
    }

    public void run() {
        updateExecutor = Executors.newScheduledThreadPool(1);
        renderExecutor = Executors.newScheduledThreadPool(1);

        updateExecutor.scheduleAtFixedRate(() -> {
            try {
                switch (this.screens) {
                    case HISTORY_SCREEN -> {
                        if (this.history.isStateOver()) {
                            this.history.moveToOtherStates();
                            this.showMainMenuScreen();
                        }
                    }
                    case GAME_SCREEN -> {
                        if (!this.game.isStateOver()) {
                            this.game.updateState();
                        } else {
                            this.showMainMenuScreen();
                            this.game.moveToOtherStates();
//                        int scores = this.game.getPlayerScore();
//                        if (scores > this.scoreKeeper.getLowestScore()) {
//                            String name = JOptionPane.showInputDialog("New High Score, Please enter your name:");
//                            this.scoreKeeper.addScore(name, scores);
//                            this.showScoreScreen();
                            // TODO 修复点击取消后的BUG

                        }
                    }
                    case MENU_SCREEN -> {
                        if (!this.menu.isStateOver()) {
                            this.menu.updateState();
                        } else {
                            if (this.menu.isChosenGameSrc()) {
                                this.showGameScreen();
                            } else if (this.menu.isChosenHistorySrc()) {
                                this.showScoreScreen();
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

        renderExecutor.scheduleAtFixedRate(() -> {
            try {
                switch (this.screens) {
                    case HISTORY_SCREEN ->
                            this.historyScreen.paintImmediately(0, 0, this.history.getScreenWidth(), this.history.getScreenHeight());
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


    public void exitGame() {
        this.renderExecutor.shutdown();
        this.updateExecutor.shutdown();
        this.scoreKeeper.saveScores();
        System.exit(0);
    }
}
