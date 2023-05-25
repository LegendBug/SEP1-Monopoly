package mdc.components.cards.actioncards;

import mdc.components.cards.ICard;
import mdc.components.piles.drawpile.DrawPile;

public abstract class AbstractActionCard implements ICard {

    public int getTurnMoney() {
        return 0;
    }

    public boolean isActing() {
        return false;
    }

    public void setActing(boolean act) {

    }

    public void discard(DrawPile pile) {

    }
}
