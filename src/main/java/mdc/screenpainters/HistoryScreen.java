package mdc.screenpainters;


import engine.Score;
import engine.ScoreKeeper;
import mdc.tools.GraphPainter;

import javax.swing.*;
import java.awt.*;

public class HistoryScreen extends JPanel {
    public static final int SCREEN_WIDTH = MenuScreen.SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT = MenuScreen.SCREEN_HEIGHT;
    private final ScoreKeeper scoreKeeper;
    private Score[] scores;

    public HistoryScreen(ScoreKeeper scoreKeeper) {
        this.scoreKeeper = scoreKeeper;
        scores = scoreKeeper.getScores();
    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    private void drawString(Graphics2D g2d) {
        GraphPainter.drawString(g2d, "Pac-Man Top 10 Players", "Courier New", 1, 45, Color.RED, 1,
                new Rectangle(SCREEN_WIDTH / 3, SCREEN_HEIGHT * 100 / 960, SCREEN_WIDTH / 3, SCREEN_HEIGHT * 80 / 960));
        scores = scoreKeeper.getScores();
        for (int i = 0; i < 10; i++) {
            Score score = scores[i];
            GraphPainter.drawString(g2d, score.getName(), "Courier New", 1, 45, Color.WHITE, 0,
                    new Rectangle(SCREEN_WIDTH * 250 / 960, SCREEN_HEIGHT * 50 * i / 960 + 200, SCREEN_WIDTH / 6, SCREEN_HEIGHT * 80 / 960));
            GraphPainter.drawString(g2d, score.getScore() + "", "Courier New", 1, 45, Color.WHITE, 2,
                    new Rectangle(SCREEN_WIDTH * 550 / 960, SCREEN_HEIGHT * 50 * i / 960 + 200, SCREEN_WIDTH / 6, SCREEN_HEIGHT * 80 / 960));
            GraphPainter.drawString(g2d, "Press 'M' to return to the Main Menu", "Courier New", 1, 30, Color.WHITE, 1,
                    new Rectangle(SCREEN_WIDTH / 3, SCREEN_HEIGHT * 750 / 960, SCREEN_WIDTH / 3, SCREEN_HEIGHT * 80 / 960));
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        drawBackground(g2d);
        drawString(g2d);
    }
}
