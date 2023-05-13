package mdc.screenpainters;

import mdc.tools.GraphPainter;
import mdc.states.game.MDCGame;

import javax.swing.*;
import java.awt.*;

/**
 * This class is used to draw components on menu screen
 */
public class MenuScreen extends JPanel {
    public static final Rectangle PAC_MAN_LOGO_1 = new Rectangle(MDCGame.SCREEN_WIDTH / 3 - 2, MDCGame.SCREEN_HEIGHT * 10 / 48 - 2, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 5 / 48);
    public static final Rectangle PAC_MAN_LOGO_2 = new Rectangle(MDCGame.SCREEN_WIDTH / 3 + 4, MDCGame.SCREEN_HEIGHT * 10 / 48 + 4, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 5 / 48);
    public static final Rectangle PAC_MAN_LOGO_3 = new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 10 / 48, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 5 / 48);
    public static final Rectangle NEW_GAME_RECT = new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 20 / 48, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 4 / 48);
    public static final Rectangle ABOUT_RECT = new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 24 / 48, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 4 / 48);
    public static final Rectangle SCORE_RECT = new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 28 / 48, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 4 / 48);
    public static final Rectangle EXIT_RECT = new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 32 / 48, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 4 / 48);


    private void drawString(Graphics g) {
        // title
        GraphPainter.drawString(g, "Pac-Man", "Showcard Gothic", 1, MDCGame.SCREEN_HEIGHT / 8, Color.WHITE, 1, PAC_MAN_LOGO_1);
        GraphPainter.drawString(g, "Pac-Man", "Showcard Gothic", 1, MDCGame.SCREEN_HEIGHT / 8, Color.WHITE, 1, PAC_MAN_LOGO_2);
        GraphPainter.drawString(g, "Pac-Man", "Showcard Gothic", 1, MDCGame.SCREEN_HEIGHT / 8, new Color(255, 130, 0), 1, PAC_MAN_LOGO_3);
        // options
        GraphPainter.drawString(g, "New Game", "Courier New", 1, MDCGame.SCREEN_WIDTH/ 24, Color.WHITE, 1, NEW_GAME_RECT);
        GraphPainter.drawString(g, "About", "Courier New", 1, MDCGame.SCREEN_WIDTH/ 24, Color.WHITE, 1, ABOUT_RECT);
        GraphPainter.drawString(g, "Score", "Courier New", 1,MDCGame.SCREEN_WIDTH/ 24, Color.WHITE, 1, SCORE_RECT);
        GraphPainter.drawString(g, "Exit", "Courier New", 1, MDCGame.SCREEN_WIDTH/ 24, Color.WHITE, 1, EXIT_RECT);
    }

    private void drawBackground(Graphics g) {
        // background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, MDCGame.SCREEN_WIDTH, MDCGame.SCREEN_HEIGHT);
        // title box
        g.setColor(new Color(255, 140, 80));
        g.fillOval(MDCGame.SCREEN_WIDTH / 7, MDCGame.SCREEN_HEIGHT * 125 / 960, 40, 40);
        g.fillOval(MDCGame.SCREEN_WIDTH * 6 / 7 - 40, MDCGame.SCREEN_HEIGHT * 125 / 960, 40, 40);
        g.fillOval(MDCGame.SCREEN_WIDTH / 7, MDCGame.SCREEN_HEIGHT * 125 / 960 + MDCGame.SCREEN_HEIGHT / 6, 40, 40);
        g.fillOval(MDCGame.SCREEN_WIDTH * 6 / 7 - 40, MDCGame.SCREEN_HEIGHT * 125 / 960 + MDCGame.SCREEN_HEIGHT / 6, 40, 40);
        g.fillRect(MDCGame.SCREEN_WIDTH / 7, MDCGame.SCREEN_HEIGHT * 125 / 960 + 20, MDCGame.SCREEN_WIDTH * 5 / 7, MDCGame.SCREEN_HEIGHT / 6);
        g.fillRect(MDCGame.SCREEN_WIDTH / 7 + 20, MDCGame.SCREEN_HEIGHT * 125 / 960, MDCGame.SCREEN_WIDTH * 5 / 7 - 40, MDCGame.SCREEN_HEIGHT / 6 + 40);
    }

    public void paintComponent(Graphics g) {
        drawBackground(g);
        drawString(g);
    }
}