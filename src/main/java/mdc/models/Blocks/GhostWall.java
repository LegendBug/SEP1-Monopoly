package mdc.models.Blocks;

import java.awt.*;

/**
This class is static blocks marked as '-' in "map.txt"
 */
public class GhostWall implements Block {
    private static final int WALL_WIDTH = 40;
    private static final int WALL_HEIGHT = 40;
    private final int x, y;
    private final Rectangle hitBox;

    public GhostWall(int x, int y) {
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(x, y, WALL_WIDTH, WALL_HEIGHT);
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
        return new Rectangle(hitBox);
    }
}
