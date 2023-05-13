package mdc.models.Entities;

import java.awt.*;

/**
This class is entities marked as '.' in "map.txt"
 */
public class Dot implements Entity {
    private static final int DOT_WIDTH = 10;
    private static final int DOT_HEIGHT = 10;
    private final int x, y;
    private final Rectangle hitBox;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(x, y, DOT_WIDTH, DOT_HEIGHT);
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }
}
