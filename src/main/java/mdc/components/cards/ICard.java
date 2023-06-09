package mdc.components.cards;

import mdc.components.piles.DrawPile;

public interface ICard {

    public void discard(DrawPile pile);

    public void deal(DrawPile pile);
}
