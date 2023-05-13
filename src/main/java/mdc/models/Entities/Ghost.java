package mdc.models.Entities;

import mdc.tools.TextReader;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class is ghost for this game
 */
public class Ghost implements Entity {
    private final int GRAPHIC_WIDTH = 40;
    private final int GRAPHIC_HEIGHT = 40;
    private final int HITBOX_WIDTH = 30;
    private final int HITBOX_HEIGHT = 30;
    /**
     * Coordinates of ghost rebirth
     */
    public final ArrayList<int[]> INITIAL_POS = TextReader.getComponents('G');
    /**
     * Current coordinates of ghost
     */
    public double x, y;
    /**
     * Remaining time for ghosts to be frightened
     */
    public int powerlessTime;
    /**
     * Remaining time for ghosts to resurrect
     */
    public int resurrectionTime;
    /**
     * Remaining time for ghost to randomly select target position
     */
    public int randomTime;
    /**
     * The current walking direction of the ghost
     */
    public int direction;
    /**
     * Number of ghost hemline (leg)
     */
    public int hemlineNumber;
    /**
     * Whether the ghost is eaten by players
     */
    public boolean ifEaten;
    /**
     * Whether the ghost is in a state of fright
     */
    public boolean ifPowerless;
    /**
     * Whether the ghost is resurrected
     */
    public boolean ifResurrection;
    /**
     * Whether ghost is in random walking state
     */
    public boolean ifRandom;
    /**
     * Whether ghost is alive
     */
    public boolean ifAlive;
    /**
     * Rectangle of ghost's current position
     */
    private Rectangle graphicRect;
    /**
     * Collision box of ghost's current position
     */
    private Rectangle hitBox;
    /**
     * The color of ghost
     */
    public Color color;

    public Ghost(int resurrectionTime, Color color) {
        assert INITIAL_POS != null;
        x = INITIAL_POS.get(0)[0] * 40;
        y = INITIAL_POS.get(0)[1] * 40;
        powerlessTime = 300;
        this.resurrectionTime = resurrectionTime * 5;
        this.randomTime = 0;
        direction = 1;
        hemlineNumber = 3;
        ifEaten = false;
        ifPowerless = false;
        ifResurrection = false;
        ifRandom = true;
        graphicRect = new Rectangle(getX(), getY(), GRAPHIC_WIDTH, GRAPHIC_HEIGHT);
        hitBox = new Rectangle(getX() + 5, getY() + 5, HITBOX_WIDTH, HITBOX_HEIGHT);
        this.color = color;
    }

    @Override
    public boolean isAlive() {
        return ifAlive;
    }

    @Override
    public int getX() {
        return (int) (x);
    }

    @Override
    public int getY() {
        return (int) (y);
    }

    public Rectangle getGraphicRect() {
        return graphicRect;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    /**
     * Control the frightened state of ghosts
     */
    public void setIfPowerless() {
        if (ifPowerless && powerlessTime > 0) {
            powerlessTime--;
        } else if (!ifEaten) {
            ifPowerless = false;
        }
    }

    /**
     * Control the resurrection time of ghosts
     */
    public void setIfResurrection() {
        if (!ifResurrection && resurrectionTime > 0) {
            resurrectionTime--;
        } else if (resurrectionTime == 0) {
            resurrectionTime = 1;
            ifResurrection = true;
            randomTime = 0;
        }
    }

    /**
     * Control the random move of ghosts
     */
    public void setIfRandom() {
        if (randomTime > 0) {
            randomTime--;
            ifRandom = false;
        } else {
            randomTime = (int) (100 + Math.random() * 200);
            ifRandom = true;
        }
    }

    /**
     * Control the eaten state of ghosts
     */
    public void setIfEaten() {
        if (ifEaten) {
            ifPowerless = true;
            powerlessTime = 300;
        }
    }

    /**
     * Control the movement and animation of ghosts
     *
     * @param x1-Distance moved in x direction
     * @param y1-Distance moved in y direction
     */
    public void move(double x1, double y1) {
        // Change the coordinates of ghosts
        graphicRect = new Rectangle(getX(), getY(), GRAPHIC_WIDTH, GRAPHIC_HEIGHT);
        hitBox = new Rectangle((int) (x + x1) + 5, (int) (y + y1) + 5, HITBOX_WIDTH, HITBOX_HEIGHT);
        this.x += x1;
        this.y += y1;
        // Change the direction of eyes
        if (y1 < 0 && x1 == 0) {
            direction = 1;
        } else if (x1 < 0 && y1 == 0) {
            direction = 2;
        } else if (y1 > 0 && x1 == 0) {
            direction = 3;
        } else if (x1 > 0 && y1 == 0) {
            direction = 4;
        }
        // Change the number of legs
        switch (hemlineNumber) {
            case 2 -> hemlineNumber = 3;
            case 3 -> hemlineNumber = 4;
            case 4 -> hemlineNumber = 5;
            case 5 -> hemlineNumber = 2;
        }
    }
}
