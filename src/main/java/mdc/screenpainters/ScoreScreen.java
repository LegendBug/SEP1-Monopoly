package mdc.screenpainters;


import engine.Score;
import engine.ScoreKeeper;
import mdc.states.game.MDCGame;
import mdc.tools.GraphPainter;

import javax.swing.*;
import java.awt.*;

public class ScoreScreen extends JPanel {
    private final ScoreKeeper scoreKeeper;
    private Score[] scores;

    public ScoreScreen(ScoreKeeper scoreKeeper) {
        this.scoreKeeper = scoreKeeper;
        scores = scoreKeeper.getScores();
    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, MDCGame.SCREEN_WIDTH, MDCGame.SCREEN_HEIGHT);
    }

    private void drawString(Graphics g) {
        GraphPainter.drawString(g, "Pac-Man Top 10 Players", "Courier New", 1, 45, Color.RED, 1,
                new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 100 / 960, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 80 / 960));
        scores = scoreKeeper.getScores();
        for (int i = 0; i < 10; i++) {
            Score score = scores[i];
            GraphPainter.drawString(g, score.getName(), "Courier New", 1, 45, Color.WHITE, 0,
                    new Rectangle(MDCGame.SCREEN_WIDTH * 250 / 960, MDCGame.SCREEN_HEIGHT * 50 * i / 960 + 200, MDCGame.SCREEN_WIDTH / 6, MDCGame.SCREEN_HEIGHT * 80 / 960));
            GraphPainter.drawString(g, score.getScore() + "", "Courier New", 1, 45, Color.WHITE, 2,
                    new Rectangle(MDCGame.SCREEN_WIDTH * 550 / 960, MDCGame.SCREEN_HEIGHT * 50 * i / 960 + 200, MDCGame.SCREEN_WIDTH / 6, MDCGame.SCREEN_HEIGHT * 80 / 960));
        GraphPainter.drawString(g, "Press 'M' to return to the Main Menu", "Courier New", 1, 30, Color.WHITE, 1,
                new Rectangle(MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 750 / 960, MDCGame.SCREEN_WIDTH / 3, MDCGame.SCREEN_HEIGHT * 80 / 960));
        }
    }

    public void paintComponent(Graphics g) {
        drawBackground(g);
        drawString(g);
    }
}
