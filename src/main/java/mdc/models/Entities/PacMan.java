package mdc.models.Entities;

import mdc.tools.TextReader;

import java.awt.*;
import java.util.ArrayList;

/**
This class is the pac-man controlled by player
 */
public class PacMan implements Entity {
    private final int HITBOX_WIDTH = 10;
    private final int HITBOX_HEIGHT = 10;
    /**
     * Coordinates of ghost rebirth
     */
    public final ArrayList<int[]> INITIAL_POS = TextReader.getComponents('P');
    /**
     * Current coordinates of ghost
     */
    private int x, y;
    private int graphicWidth;
    private int graphicHeight;
    /**
     * Remaining time for player to be powerful
     */
    public int powerfulTime;
    /**
     * Number of ghosts eaten by players in powerful state
     */
    public int eatenNumber;
    /**
     * The current walking direction of the player
     */
    public int mouthDirection;
    /**
     * Range of mouth opening and closing
     */
    public int mouthRange;
    private Rectangle graphicRect;
    private Rectangle hitBox;
    public boolean ifPowerful;
    public boolean ifAlive;

    public PacMan() {
        assert INITIAL_POS != null;
        x = INITIAL_POS.get(0)[0] * 40;
        y = INITIAL_POS.get(0)[1] * 40;
        graphicWidth = 30;
        graphicHeight = 30;
        powerfulTime = 0;
        eatenNumber = 0;
        mouthDirection = 4;
        mouthRange = 15;
        ifPowerful = false;
        ifAlive = true;
        graphicRect = new Rectangle(x + 5, y + 5, graphicWidth, graphicHeight);
        hitBox = new Rectangle(x + 15, y + 15, HITBOX_WIDTH, HITBOX_HEIGHT);

    }

    @Override
    public boolean isAlive() {
        return ifAlive;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public Rectangle getGraphicRect() {
        return graphicRect;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    /**
     * Control the powerful state of player
     */
    public void setIfPowerful() {
        if (ifPowerful && powerfulTime > 0) {
            powerfulTime--;
            graphicWidth = 40;
            graphicHeight = 40;
            graphicRect = new Rectangle(x, y, graphicWidth, graphicHeight);
        } else {
            ifPowerful = false;
            graphicWidth = 30;
            graphicHeight = 30;
            graphicRect = new Rectangle(x + 5, y + 5, graphicWidth, graphicHeight);
        }
    }

    /**
     * Control the movement and animation of player
     *
     * @param x1-Distance moved in x direction
     * @param y1-Distance moved in y direction
     */
    public void move(int x1, int y1) {
        // Change the coordinates of player
        hitBox = new Rectangle(x + x1 + 15, y + y1 + 15, HITBOX_WIDTH, HITBOX_HEIGHT);
        this.x += x1;
        this.y += y1;
        // Change the direction of month
        if (y1 < 0) {
            mouthDirection = 1;
        } else if (x1 < 0) {
            mouthDirection = 2;
        } else if (y1 > 0) {
            mouthDirection = 3;
        } else if (x1 > 0) {
            mouthDirection = 4;
        }
        // Change the range of mouth opening
        switch (mouthRange) {
            case 0 -> mouthRange = 15;
            case 15 -> mouthRange = 25;
            case 25 -> mouthRange = 45;
            case 45 -> mouthRange = 65;
            case 65 -> mouthRange = 80;
            case 80 -> mouthRange = 60;
            case 60 -> mouthRange = 30;
            case 30 -> mouthRange = 0;
        }
    }
}
