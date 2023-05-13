package mdc.engine;

import engine.ScoreKeeper;
import engine.ScreenEnum;
import mdc.states.about.MDCAbout;
import mdc.states.game.Game;
import mdc.states.game.MDCGame;
import mdc.states.menu.MDCMenu;
import mdc.states.score.MDCScore;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameEngine {
    private static final long RENDER_TIME = 17L;
    private static final long UPDATE_TIME = 30L; // 更新频率总是大于或等于渲染频率
    private static final int MAX_RENDER_TIMES = 5;
    private JFrame mainWindow;
    private JPanel mainPanel;
    private JPanel menuScreen;
    private JPanel aboutScreen;
    private JPanel scoreScreen;
    private JPanel gameScreen;
    private CardLayout cardLayout;
    private ScoreKeeper scoreKeeper;
    private MDCMenu menu;
    private MDCAbout about;
    private MDCScore score;
    private MDCGame game;
    private ScreenEnum screenEnum;
    private ScheduledExecutorService updateExecutor;
    private ScheduledExecutorService renderExecutor;

    public GameEngine(MDCGame game, MDCMenu menu, MDCAbout about, MDCScore score, JFrame applicationWindow, JPanel menuScr, JPanel aboutScr, JPanel scoreScr, JPanel gameScr, ScoreKeeper scoreKeep) {
        this.screenEnum = ScreenEnum.MAIN_MENU_SCREEN;
        this.mainWindow = applicationWindow;
        this.menu = menu;
        this.about = about;
        this.score = score;
        this.game = game;
        this.scoreKeeper = scoreKeep;
        this.cardLayout = new CardLayout();
        this.mainPanel = new JPanel(this.cardLayout);
        this.mainWindow.getContentPane().add(this.mainPanel);
        this.mainPanel.add(aboutScr, "About");
        this.mainPanel.add(menuScr, "Main Menu");
        this.mainPanel.add(scoreScr, "High Scores");
        this.mainPanel.add(gameScr, "Game Screen");
        this.menuScreen = menuScr;
        this.aboutScreen = aboutScr;
        this.scoreScreen = scoreScr;
        this.gameScreen = gameScr;
        this.showMainMenuScreen();
    }

    public GameEngine() {
        this.screenEnum = ScreenEnum.MAIN_MENU_SCREEN;
    }

    private void showMainMenuScreen() {
        this.menu.startState();
        this.cardLayout.show(this.mainPanel, "Main Menu");
        this.screenEnum = ScreenEnum.MAIN_MENU_SCREEN;
    }

    private void showAboutScreen() {
        this.about.startState();
        this.cardLayout.show(this.mainPanel, "About");
        this.screenEnum = ScreenEnum.ABOUT_SCREEN;
    }

    private void showHighScoreScreen() {
        this.score.startState();
        this.cardLayout.show(this.mainPanel, "High Scores");
        this.screenEnum = ScreenEnum.SCORE_SCREEN;
    }

    private void showGameScreen() {
        this.game.startState();
        this.cardLayout.show(this.mainPanel, "Game Screen");
        this.screenEnum = ScreenEnum.GAME_SCREEN;
    }

    public void run() {
        updateExecutor = Executors.newScheduledThreadPool(1);
        renderExecutor = Executors.newScheduledThreadPool(1);

        updateExecutor.scheduleAtFixedRate(() -> {
            switch (this.screenEnum) {
                case SCORE_SCREEN:
                    if (this.score.isStateOver()) {
                        this.score.moveToOtherStates();
                        this.showHighScoreScreen();
                    }
                case ABOUT_SCREEN:
                    if (this.about.isStateOver()) {
                        this.about.moveToOtherStates();
                        this.showMainMenuScreen();
                    }
                    break;
                case GAME_SCREEN:
                    if (!this.game.isStateOver() && !this.game.isLevelFinished()) {
                        if (this.game.isPlayerAlive() && !this.game.isPaused()) {
                            this.game.updateState();
                            this.game.checkForPause();
                        } else {
                            if (!this.game.isPlayerAlive()) {
                                this.game.resetDestroyedPlayer();
                            } else {
                                this.game.checkForPause();
                            }
                        }
                    } else if (!this.game.isStateOver() && this.game.isLevelFinished()){
                        this.game.moveToNextLevel();
                    } else if (game.isStateOver()) {
                        this.game.moveToOtherStates();
                        int scores = this.game.getPlayerScore();
                        if (scores > this.scoreKeeper.getLowestScore()) {
                            String name = JOptionPane.showInputDialog("New High Score, Please enter your name:");
                            this.scoreKeeper.addScore(name, scores);
                            this.showHighScoreScreen();
                            // TODO 修复点击取消后的BUG
                        } else {
                            this.showMainMenuScreen();
                        }
                    }
                    break;
                case MAIN_MENU_SCREEN:
                    if (!this.menu.isStateOver()) {
                        this.menu.updateState();
                    } else {
                        this.menu.moveToOtherStates();
                        if (this.menu.isChosenGameSrc()) {
                            this.showGameScreen();
                        } else if (this.menu.isChosenHelpSrc()) {
                            this.showAboutScreen();
                        } else if (this.menu.isChosenScoreScr()) {
                            this.showHighScoreScreen();
                        } else if (this.menu.isChosenExit()) {
                            this.exitGame();
                        }
                    }
                    break;
            }
        }, 0, 17, TimeUnit.MILLISECONDS);

        renderExecutor.scheduleAtFixedRate(() -> {
            switch (this.screenEnum) {
                case SCORE_SCREEN ->
                        this.scoreScreen.paintImmediately(0, 0, this.score.getScreenWidth(), this.score.getScreenHeight());
                case ABOUT_SCREEN ->
                        this.aboutScreen.paintImmediately(0, 0, this.score.getScreenWidth(), this.score.getScreenHeight());
                case GAME_SCREEN ->
                        this.gameScreen.paintImmediately(0, 0, this.game.getScreenWidth(), this.game.getScreenHeight());
                case MAIN_MENU_SCREEN ->
                        this.menuScreen.paintImmediately(0, 0, this.menu.getScreenWidth(), this.menu.getScreenHeight());
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
