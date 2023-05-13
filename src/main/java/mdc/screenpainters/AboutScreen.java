package mdc.screenpainters;

import mdc.tools.GraphPainter;
import mdc.states.game.MDCGame;

import javax.swing.*;
import java.awt.*;

/**
 * This class is used to draw components on about screen
 */
public class AboutScreen extends JPanel {
    private void drawString(Graphics g) {
        GraphPainter.drawString(g, "Pac-Man Controls", "Courier New", 1, 45, Color.RED, 1,
                new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 100 / 960, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 80 / 960));
        GraphPainter.drawString(g, "Action          Button", "Courier New", 1, 45, Color.WHITE, 1,
                new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 200 / 960, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 80 / 960));
        GraphPainter.drawString(g, "Move Up    -    'UP ARROW'", "Courier New", 1, 45, Color.WHITE, 1,
                new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 280 / 960, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 80 / 960));
        GraphPainter.drawString(g, "Move Down  -  'DOWN ARROW'", "Courier New", 1, 45, Color.WHITE, 1,
                new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 360 / 960, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 80 / 960));
        GraphPainter.drawString(g, "Move Right - 'RIGHT ARROW'", "Courier New", 1,45, Color.WHITE, 1,
                new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 440 / 960, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 80 / 960));
        GraphPainter.drawString(g, "Move Left  -  'LEFT ARROW'", "Courier New", 1, 45, Color.WHITE, 1,
                new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 520 / 960, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 80 / 960));
        GraphPainter.drawString(g, "Pause/Play -           'P'", "Courier New", 1, 45, Color.WHITE, 1,
                new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 600 / 960, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 80 / 960));
        GraphPainter.drawString(g, "Press 'M' to return to the Main Menu", "Courier New", 1, 30, Color.WHITE, 1,
                new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 750 / 960, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 80 / 960));

    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, MDCGame.SCREEN_WIDTH, MDCGame.SCREEN_HEIGHT);
    }

    public void paintComponent(Graphics g) {
        drawBackground(g);
        drawString(g);
    }
}
