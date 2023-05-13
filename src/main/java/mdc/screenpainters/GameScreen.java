package mdc.screenpainters;

import mdc.tools.GraphPainter;
import mdc.states.game.MDCGame;

import javax.swing.*;
import java.awt.*;

/**
 * This class is used to draw components on game screen
 */
public class GameScreen extends JPanel {
    private final MDCGame game;
    private Color color;

    public GameScreen(MDCGame game) {
        this.game = game;
    }

    private void drawBackground(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, MDCGame.SCREEN_WIDTH, MDCGame.SCREEN_HEIGHT);

    }

    private void drawNormalWalls(Graphics g) {
        for (Rectangle normalWallRect : game.getNormalWallsRect()) {
            GraphPainter.drawRectangle(g, Color.BLUE, normalWallRect);
        }
    }

    private void drawGhostWalls(Graphics g) {
        for (Rectangle ghostWallRect : game.getGhostWallsRect()) {
            GraphPainter.drawRectangle(g, Color.DARK_GRAY, ghostWallRect);
        }
    }

    private void drawDots(Graphics g) {
        for (Rectangle dotRect : game.getDotsRect()) {
            GraphPainter.drawRectangle(g, Color.WHITE, dotRect);
        }
    }

    private void drawFruit(Graphics g) {
        if (game.getFruitsRect() != null) {
            // Here is a simple drawing of an apple
            for (Rectangle fruitRect : game.getFruitsRect()) {
                GraphPainter.drawRectangle(g, Color.GRAY, new Rectangle(fruitRect.x + 18, fruitRect.y + 4, 3, 14));
                int[] xPoints = new int[] {5, 16, 19, 23, 34, 39, 31, 22, 20, 19, 8, 0};
                int[] yPoints = new int[] {10, 10, 16, 10, 10, 20, 39, 39, 35, 39, 39, 20};
                for (int i = 0; i < xPoints.length; i++) {
                    xPoints[i] = fruitRect.x + xPoints[i];
                    yPoints[i] = fruitRect.y + yPoints[i];
                }
                GraphPainter.drawPolygon(g, Color.RED, xPoints, yPoints, 12);
            }
        }
    }

    private void drawPowerPellets(Graphics g) {
        for (Rectangle powerPellets : game.getPowerPelletsRect())
            GraphPainter.drawOval(g, Color.YELLOW, powerPellets.x, powerPellets.y, powerPellets.width, powerPellets.height);
    }

    private void drawPacMan(Graphics g) {
        Rectangle rect = game.getPacManGraphicRect();
        int direction = game.pacMan.mouthDirection;
        int range = game.pacMan.mouthRange;
        GraphPainter.drawAct(g, Color.YELLOW, rect.x, rect.y, rect.width, rect.height, direction * 90 + range, 360 - 2 * range);
    }

    private void drawGhostBody(Graphics g, Color color, Rectangle ghostRect, int hemlineNumber) {
        // 头部
        GraphPainter.drawAct(g, color, ghostRect.x, ghostRect.y, ghostRect.width, ghostRect.height, 0, 180);
        GraphPainter.drawRectangle(g, color, new Rectangle(ghostRect.x, ghostRect.y + 20, 40, 16));
        // 腿部
        switch (hemlineNumber) {
            case 2 -> {
                for (int number = 0; number < hemlineNumber; number++) {
                    GraphPainter.drawAct(g, color, ghostRect.x + 20 * number, ghostRect.y + 22, 20, 20, 180, 360);
                }
            }
            case 3 -> {
                for (int number = 0; number < hemlineNumber; number++) {
                    GraphPainter.drawAct(g, color, ghostRect.x + 13 * number, ghostRect.y + 27, 13, 13, 180, 360);
                }
            }
            case 4 -> {
                for (int number = 0; number < hemlineNumber; number++) {
                    GraphPainter.drawAct(g, color, ghostRect.x + 10 * number, ghostRect.y + 30, 10, 10, 180, 360);
                }
            }
            case 5 -> {
                for (int number = 0; number < hemlineNumber; number++) {
                    GraphPainter.drawAct(g, color, ghostRect.x + 8 * number, ghostRect.y + 32, 8, 8, 180, 360);
                }
            }
        }
    }

    private void drawGhostEyes(Graphics g, Rectangle ghostRect, int direction) {
        // 眼眶
        GraphPainter.drawOval(g, Color.WHITE, ghostRect.x + 6, ghostRect.y + 9, 11, 11);
        GraphPainter.drawOval(g, Color.WHITE, ghostRect.x + 23, ghostRect.y + 9, 11, 11);
        // 眼珠
        switch (direction) {
            case 1 -> {
                GraphPainter.drawOval(g, Color.BLACK, ghostRect.x + 8, ghostRect.y + 9, 7, 7);
                GraphPainter.drawOval(g, Color.BLACK, ghostRect.x + 25, ghostRect.y + 9, 7, 7);
            }
            case 2 -> {
                GraphPainter.drawOval(g, Color.BLACK, ghostRect.x + 6, ghostRect.y + 11, 7, 7);
                GraphPainter.drawOval(g, Color.BLACK, ghostRect.x + 23, ghostRect.y + 11, 7, 7);
            }
            case 3 -> {
                GraphPainter.drawOval(g, Color.BLACK, ghostRect.x + 8, ghostRect.y + 13, 7, 7);
                GraphPainter.drawOval(g, Color.BLACK, ghostRect.x + 25, ghostRect.y + 13, 7, 7);
            }
            case 4 -> {
                GraphPainter.drawOval(g, Color.BLACK, ghostRect.x + 10, ghostRect.y + 11, 7, 7);
                GraphPainter.drawOval(g, Color.BLACK, ghostRect.x + 27, ghostRect.y + 11, 7, 7);
            }
        }
    }

    /**
     *Every ghost has three states: normal, frightened (powerless) and eaten
     */
    private void drawGhosts(Graphics g) {
        for (int i = game.getGhostsGraphicRect().size() - 1; i >= 0; i--) {
            Rectangle ghostRect = game.getGhostsGraphicRect().get(i);
            int direction = game.ghosts.get(i).direction;
            int hemlineNumber = game.ghosts.get(i).hemlineNumber;
            if (game.ghosts.get(i).ifEaten) {
                // eaten
                drawGhostEyes(g, ghostRect, direction);
            } else {
                if (game.ghosts.get(i).ifPowerless) {
                    // frightened
                    if (game.pacMan.powerfulTime < 100 && game.pacMan.powerfulTime % 6 / 3 == 1) {
                        color = Color.LIGHT_GRAY;
                    } else {
                        color = Color.BLUE;
                    }
                    drawGhostBody(g, color, ghostRect, hemlineNumber);
                    GraphPainter.drawRectangle(g, Color.WHITE, new Rectangle(ghostRect.x + 10, ghostRect.y + 15, 5, 5));
                    GraphPainter.drawRectangle(g, Color.WHITE, new Rectangle(ghostRect.x + 25, ghostRect.y + 15, 5, 5));
                    GraphPainter.drawRectangle(g, Color.WHITE, new Rectangle(ghostRect.x + 3, ghostRect.y + 29, 2, 2));
                    GraphPainter.drawRectangle(g, Color.WHITE, new Rectangle(ghostRect.x + 5, ghostRect.y + 27, 6, 2));
                    GraphPainter.drawRectangle(g, Color.WHITE, new Rectangle(ghostRect.x + 11, ghostRect.y + 29, 6, 2));
                    GraphPainter.drawRectangle(g, Color.WHITE, new Rectangle(ghostRect.x + 17, ghostRect.y + 27, 6, 2));
                    GraphPainter.drawRectangle(g, Color.WHITE, new Rectangle(ghostRect.x + 23, ghostRect.y + 29, 6, 2));
                    GraphPainter.drawRectangle(g, Color.WHITE, new Rectangle(ghostRect.x + 29, ghostRect.y + 27, 6, 2));
                    GraphPainter.drawRectangle(g, Color.WHITE, new Rectangle(ghostRect.x + 35, ghostRect.y + 29, 2, 2));
                } else {
                    // normal
                    switch (i) {
                        case 0 -> color = Color.RED;
                        case 1 -> color = Color.PINK;
                        case 2 -> color = Color.CYAN;
                        case 3 -> color = Color.ORANGE;
                    }
                    drawGhostBody(g, color, ghostRect, hemlineNumber);
                    drawGhostEyes(g, ghostRect, direction);
                }
            }
        }
    }

    private void drawString(Graphics g) {
        // Current level
        GraphPainter.drawString(g, "Level:", "Courier New", 1, 30, Color.WHITE, 0,
                new Rectangle(MDCGame.SCREEN_WIDTH * 810 / 960, MDCGame.SCREEN_HEIGHT * 50 / 960, MDCGame.SCREEN_WIDTH / 6, MDCGame.SCREEN_HEIGHT * 80 / 960));
        GraphPainter.drawString(g, "" + game.getCurrentLevel(), "Courier New", 1, 30, Color.WHITE, 1,
                new Rectangle(MDCGame.SCREEN_WIDTH * 780 / 960, MDCGame.SCREEN_HEIGHT * 80 / 960, MDCGame.SCREEN_WIDTH / 6, MDCGame.SCREEN_HEIGHT * 80 / 960));
        // score
        GraphPainter.drawString(g, "Score:", "Courier New", 1, 30, Color.WHITE, 0,
                new Rectangle(MDCGame.SCREEN_WIDTH * 810 / 960, MDCGame.SCREEN_HEIGHT * 150 / 960, MDCGame.SCREEN_WIDTH / 6, MDCGame.SCREEN_HEIGHT * 80 / 960));
        GraphPainter.drawString(g, "" + game.getPlayerScore(), "Courier New", 1, 30, Color.WHITE, 1,
                new Rectangle(MDCGame.SCREEN_WIDTH * 780 / 960, MDCGame.SCREEN_HEIGHT * 180 / 960, MDCGame.SCREEN_WIDTH / 6, MDCGame.SCREEN_HEIGHT * 80 / 960));
        // lives
        if (game.getLives() <= 1) {
            color = Color.RED;
        } else {
            color = Color.WHITE;
        }
        GraphPainter.drawString(g, "Lives:", "Courier New", 1, 30, color, 0,
                new Rectangle(MDCGame.SCREEN_WIDTH * 810 / 960, MDCGame.SCREEN_HEIGHT * 250 / 960, MDCGame.SCREEN_WIDTH / 6, MDCGame.SCREEN_HEIGHT * 80 / 960));
        GraphPainter.drawString(g, "" + game.getLives(), "Courier New", 1, 30, color, 1,
                new Rectangle(MDCGame.SCREEN_WIDTH * 780 / 960, MDCGame.SCREEN_HEIGHT * 280 / 960, MDCGame.SCREEN_WIDTH / 6, MDCGame.SCREEN_HEIGHT * 80 / 960));
        // help
        if (game.isPaused()) {
            GraphPainter.drawString(g, "GAME PAUSE", "Courier New", 1, 100, Color.GREEN, 0,
                    new Rectangle(MDCGame.SCREEN_WIDTH * 200 / 960, MDCGame.SCREEN_HEIGHT * 400 / 960, MDCGame.SCREEN_WIDTH / 6, MDCGame.SCREEN_HEIGHT * 80 / 960));
            GraphPainter.drawString(g, "Play ('P')", "Courier New", 1, 20, Color.RED, 0,
                    new Rectangle(MDCGame.SCREEN_WIDTH * 840 / 960, MDCGame.SCREEN_HEIGHT * 930 / 960, MDCGame.SCREEN_WIDTH / 6, MDCGame.SCREEN_HEIGHT * 80 / 960));
        } else {
            GraphPainter.drawString(g, "Pause('P')", "Courier New", 1, 20, Color.WHITE, 0,
                    new Rectangle(MDCGame.SCREEN_WIDTH * 840 / 960, MDCGame.SCREEN_HEIGHT * 930 / 960, MDCGame.SCREEN_WIDTH / 6, MDCGame.SCREEN_HEIGHT * 80 / 960));
        }
        GraphPainter.drawString(g, "Skip ('S')", "Courier New", 1, 20, Color.WHITE, 0,
                new Rectangle(MDCGame.SCREEN_WIDTH * 840 / 960, MDCGame.SCREEN_HEIGHT * 900 / 960, MDCGame.SCREEN_WIDTH / 6, MDCGame.SCREEN_HEIGHT * 80 / 960));
    }

    protected void paintComponent(Graphics g) {
        if (game != null) {
            drawBackground(g);
            drawNormalWalls(g);
            drawGhostWalls(g);
            drawDots(g);
            drawFruit(g);
            drawPowerPellets(g);
            drawGhosts(g);
            drawPacMan(g);
            drawString(g);
        }
    }
}
