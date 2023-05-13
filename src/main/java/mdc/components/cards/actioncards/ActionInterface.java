package mdc.components.cards.actioncards;

import mdc.components.piles.drawpile.DrawPile;

public interface ActionInterface {

    public String getName();

    public int getTurnMoney();

    public boolean isActing();

    public void setActing(boolean act);

    public void discard(DrawPile pile);
}
