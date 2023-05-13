package mdc.components.cards;

import mdc.components.piles.drawpile.DrawPile;

public interface CardInterface {
    public int getTurnMoney();
    public void discard(DrawPile pile);
}
