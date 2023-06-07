package mdc.screenpainters;

import mdc.states.menu.MDCMenu;
import mdc.tools.Config;
import mdc.tools.GraphPainter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * This class is used to draw components on menu screen
 */
public class MenuScreen extends JPanel {
    public static int screenWidth;
    public static int screenHeight;
    public static Rectangle gameRect;
    public static Rectangle historyRect;
    public static Rectangle exitRect;
    public static Image gameStart1;
    public static Image gameStart2;
    public static Image gameStart3;
    public static Image exit1;
    public static Image exit2;
    public static Image exit3;
    public static Image history1;
    public static Image history2;
    public static Image history3;
    public final Rectangle mdcLogo1;
    public final Rectangle mdcLogo2;
    public final Rectangle mdcLogo3;
    public final Rectangle mdcLogo4;
    public final Rectangle mdcLogo5;
    public final Rectangle mdcLogo6;
    // TODO 将所有的Rectangle加入Config
    public final Rectangle backgroundRect;
    public final Image background;
    private final int screenResolution;
    private final MDCMenu menu;

    public MenuScreen(MDCMenu menu, Config config) throws IOException {
        this.menu = menu;
        screenResolution = Toolkit.getDefaultToolkit().getScreenResolution();
        screenWidth = (int) (config.getScreen().getWidth() * (96.0 / screenResolution));
        screenHeight = (int) (config.getScreen().getHeight() * (96.0 / screenResolution));
        gameRect = new Rectangle(screenWidth * 6 / 48, screenHeight * 32 / 48, screenWidth * 36 / 48, screenHeight * 13 / 48);
        historyRect = new Rectangle(screenWidth - screenWidth * 5 / 48, 0, screenWidth * 5 / 48, screenHeight * 3 / 48);
        exitRect = new Rectangle(0, 0, screenWidth / 10, screenHeight / 15);

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

        mdcLogo1 = new Rectangle(screenWidth * 3 / 16 - 3, screenHeight * 31 / 48 - 10, screenWidth / 3, screenHeight * 5 / 48);
        mdcLogo2 = new Rectangle(screenWidth * 3 / 16 + 5, screenHeight * 31 / 48 - 4, screenWidth / 3, screenHeight * 5 / 48);
        mdcLogo3 = new Rectangle(screenWidth * 3 / 16, screenHeight * 31 / 48 - 8, screenWidth / 3, screenHeight * 5 / 48);
        mdcLogo4 = new Rectangle(screenWidth * 8 / 16 - 3, screenHeight * 31 / 48 - 10, screenWidth / 3, screenHeight * 5 / 48);
        mdcLogo5 = new Rectangle(screenWidth * 8 / 16 + 5, screenHeight * 31 / 48 - 4, screenWidth / 3, screenHeight * 5 / 48);
        mdcLogo6 = new Rectangle(screenWidth * 8 / 16, screenHeight * 31 / 48 - 8, screenWidth / 3, screenHeight * 5 / 48);
        backgroundRect = new Rectangle(0, 0, screenWidth, screenHeight);
    }

    private void drawString(Graphics2D g2d) throws IOException {
        GraphPainter.drawString(g2d, "Deal", "Showcard Gothic", 1, screenHeight / 8, Color.GRAY, 0, mdcLogo1);
        GraphPainter.drawString(g2d, "Deal", "Showcard Gothic", 1, screenHeight / 8, Color.GRAY, 0, mdcLogo2);
        GraphPainter.drawString(g2d, "Deal", "Showcard Gothic", 1, screenHeight / 8, new Color(238, 238, 238), 0, mdcLogo3);

        GraphPainter.drawString(g2d, "Cards", "Showcard Gothic", 1, screenHeight / 8, Color.GRAY, 2, mdcLogo4);
        GraphPainter.drawString(g2d, "Cards", "Showcard Gothic", 1, screenHeight / 8, Color.GRAY, 2, mdcLogo5);
        GraphPainter.drawString(g2d, "Cards", "Showcard Gothic", 1, screenHeight / 8, new Color(238, 238, 238), 2, mdcLogo6);
    }

//    private void drawButton(Graphics2D g2d) throws IOException {
//        for (Button button : menu.getButtonsCopy()) {
//            GraphPainter.drawImage(g2d, button.getImage(), button.getRect(), false);
//        }
//    }

    private void drawBackground(Graphics2D g2d) throws IOException {
        GraphPainter.drawImage(g2d, background, backgroundRect, false);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            drawBackground(g2d);
//            drawButton(g2d);
            drawString(g2d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}