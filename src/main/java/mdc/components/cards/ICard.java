package mdc.components.cards;

import mdc.components.piles.drawpile.DrawPile;

public interface ICard {
    public int getTurnMoney();
    public void discard(DrawPile pile);
    public void deal(DrawPile pile);
}
