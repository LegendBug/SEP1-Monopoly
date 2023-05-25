package mdc.components.cards.properties;

import mdc.components.cards.Colors;
import mdc.components.cards.ICard;
import mdc.components.cards.actioncards.Hotel;
import mdc.components.cards.actioncards.House;
import mdc.components.piles.drawpile.DrawPile;

public abstract class AbstractPropertyCard implements ICard {

    public int getTurnMoney() {
        return 0;
    }

    public Colors getColor() {
        return null;
    }

    public int getValue() {
        return 0;
    }

    public int getRent(int v) {
        return 0;
    }

    public void addHotel(Hotel hotel) {

    }

    public void addHouse(House house) {

    }

    public void discard(DrawPile pile) {

    }

}
