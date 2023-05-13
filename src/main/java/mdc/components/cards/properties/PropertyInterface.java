package mdc.components.cards.properties;

import mdc.components.cards.actioncards.Hotel;
import mdc.components.cards.actioncards.House;
import mdc.components.piles.drawpile.DrawPile;

public interface PropertyInterface {
    public String getName();
    public int getTurnMoney();
    public String getColor();
    public int getValue();
    public int getRent(int v);
    public void addHotel(Hotel hotel);
    public void addHouse(House house);
    public void discard(DrawPile pile);
}
