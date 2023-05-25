package mdc.screenpainters;

import mdc.states.ButtonStates;
import mdc.states.menu.MDCMenu;
import mdc.states.menu.MenuButtons;
import mdc.tools.Config;
import mdc.tools.GraphPainter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class is used to draw components on menu screen
 */
public class MenuScreen extends JPanel {
    private int screenResolution;
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static Rectangle GAME_RECT;
    public static Rectangle HISTORY_RECT;
    public static Rectangle EXIT_RECT;

    public final Rectangle MDC_LOGO_1;
    public final Rectangle MDC_LOGO_2;
    public final Rectangle MDC_LOGO_3;
    public final Rectangle MDC_LOGO_4;
    public final Rectangle MDC_LOGO_5;
    public final Rectangle MDC_LOGO_6;
    public final Rectangle BACKGROUND_RECT;
    // TODO 将这些放入构造器

    private final Image background;
    private final Image gameStart1;
    private final Image gameStart2;
    private final Image gameStart3;
    private final Image exit1;
    private final Image exit2;
    private final Image exit3;
    private final Image history1;
    private final Image history2;
    private final Image history3;


    private final MDCMenu menu;

    public MenuScreen(MDCMenu menu, Config config) throws IOException {
        this.menu = menu;
        screenResolution  = Toolkit.getDefaultToolkit().getScreenResolution();
        SCREEN_WIDTH = (int) (config.getScreen().getWidth() * (96.0 / screenResolution));
        SCREEN_HEIGHT = (int) (config.getScreen().getHeight() * (96.0 / screenResolution));
        GAME_RECT = new Rectangle(SCREEN_WIDTH * 6 / 48, SCREEN_HEIGHT * 32 / 48, SCREEN_WIDTH * 36 / 48, SCREEN_HEIGHT * 13 / 48);
        HISTORY_RECT = new Rectangle(SCREEN_WIDTH - SCREEN_WIDTH  * 5 / 48, 0, SCREEN_WIDTH  * 5 / 48, SCREEN_HEIGHT * 3 / 48);
        EXIT_RECT = new Rectangle(0, 0, SCREEN_WIDTH / 10, SCREEN_HEIGHT / 15);

        background = GraphPainter.getImage(config.getImagePath().getBackground());
        gameStart1 = GraphPainter.getImage(config.getImagePath().getGameStart1());
        gameStart2 = GraphPainter.getImage(config.getImagePath().getGameStart2());
        gameStart3 = GraphPainter.getImage(config.getImagePath().getGameStart3());
        exit1 = GraphPainter.getImage(config.getImagePath().getExit1());
        exit2 = GraphPainter.getImage(config.getImagePath().getExit2());
        exit3 = GraphPainter.getImage(config.getImagePath().getExit3());
        history1 = GraphPainter.getImage(config.getImagePath().getHistory1());
        history2 = GraphPainter.getImage(config.getImagePath().getHistory2());
        history3 = GraphPainter.getImage(config.getImagePath().getHistory3());

        MDC_LOGO_1 = new Rectangle(SCREEN_WIDTH * 3 / 16 - 3, SCREEN_HEIGHT * 31 / 48 - 10, SCREEN_WIDTH / 3, SCREEN_HEIGHT * 5 / 48);
        MDC_LOGO_2 = new Rectangle(SCREEN_WIDTH * 3 / 16 + 5, SCREEN_HEIGHT * 31 / 48 - 4, SCREEN_WIDTH / 3, SCREEN_HEIGHT * 5 / 48);
        MDC_LOGO_3 = new Rectangle(SCREEN_WIDTH * 3 / 16, SCREEN_HEIGHT * 31 / 48 - 8, SCREEN_WIDTH / 3, SCREEN_HEIGHT * 5 / 48);
        MDC_LOGO_4 = new Rectangle(SCREEN_WIDTH * 8 / 16 - 3, SCREEN_HEIGHT * 31 / 48 - 10, SCREEN_WIDTH / 3, SCREEN_HEIGHT * 5 / 48);
        MDC_LOGO_5 = new Rectangle(SCREEN_WIDTH * 8 / 16 + 5, SCREEN_HEIGHT * 31 / 48 - 4, SCREEN_WIDTH / 3, SCREEN_HEIGHT * 5 / 48);
        MDC_LOGO_6 = new Rectangle(SCREEN_WIDTH * 8 / 16, SCREEN_HEIGHT * 31 / 48 - 8, SCREEN_WIDTH / 3, SCREEN_HEIGHT * 5 / 48);
        BACKGROUND_RECT = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

    }

    private void drawString(Graphics2D g2d) throws IOException {
        GraphPainter.drawString(g2d, "Deal", "Showcard Gothic", 1, SCREEN_HEIGHT / 8, Color.GRAY, 0, MDC_LOGO_1);
        GraphPainter.drawString(g2d, "Deal", "Showcard Gothic", 1, SCREEN_HEIGHT / 8, Color.GRAY, 0, MDC_LOGO_2);
        GraphPainter.drawString(g2d, "Deal", "Showcard Gothic", 1, SCREEN_HEIGHT / 8, new Color(238, 238, 238), 0, MDC_LOGO_3);

        GraphPainter.drawString(g2d, "Cards", "Showcard Gothic", 1, SCREEN_HEIGHT / 8, Color.GRAY, 2, MDC_LOGO_4);
        GraphPainter.drawString(g2d, "Cards", "Showcard Gothic", 1, SCREEN_HEIGHT / 8, Color.GRAY, 2, MDC_LOGO_5);
        GraphPainter.drawString(g2d, "Cards", "Showcard Gothic", 1, SCREEN_HEIGHT / 8, new Color(238, 238, 238), 2, MDC_LOGO_6);
    }

    private void drawButton(Graphics2D g2d) throws IOException {
        GraphPainter.drawImage(g2d, gameStart1, GAME_RECT, false);
        GraphPainter.drawImage(g2d, history1, HISTORY_RECT, false);
        GraphPainter.drawImage(g2d, exit1, EXIT_RECT, false);
        ButtonStates state = menu.getButtonState();
        MenuButtons button = menu.getMenuButton();
        switch (state) {
            case NORMAL -> {}
            case HOVER -> {
                switch (button) {
                    case GameScr -> GraphPainter.drawImage(g2d, gameStart2, GAME_RECT, false);
                    case HistoryScr -> GraphPainter.drawImage(g2d, history2, HISTORY_RECT, false);
                    case Exit -> GraphPainter.drawImage(g2d, exit2, EXIT_RECT, false);
                }
            }
            case PRESSED -> {
                switch (button) {
                    case GameScr -> GraphPainter.drawImage(g2d, gameStart3, GAME_RECT, false);
                    case HistoryScr -> GraphPainter.drawImage(g2d, history3, HISTORY_RECT, false);
                    case Exit -> GraphPainter.drawImage(g2d, exit3, EXIT_RECT, false);
                }
            }
        }

    }

    private void drawBackground(Graphics2D g2d) throws IOException {
        GraphPainter.drawImage(g2d,background, BACKGROUND_RECT, false);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            drawBackground(g2d);
            drawButton(g2d);
            drawString(g2d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}