package mdc.models.Entities;

import java.awt.*;

/**
This interface contains methods for some entities that can interact with players or ghosts
 */
public interface Entity {
    boolean isAlive();

    int getX();

    int getY();

    Rectangle getHitBox();
}
