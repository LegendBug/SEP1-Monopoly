package mdc.models.Entities;

import java.awt.*;

/**
This class is entities marked as 'F' in "map.txt"
 */
public class Fruit implements Entity {
    private static final int FRUIT_WIDTH = 40;
    private static final int FRUIT_HEIGHT = 40;
    private final int x, y;
    private final Rectangle hitBox;

    public Fruit(int x, int y) {
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(x, y, FRUIT_WIDTH, FRUIT_HEIGHT);
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
