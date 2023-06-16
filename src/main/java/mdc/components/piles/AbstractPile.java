package mdc.components.piles;

import mdc.components.cards.AbstractCard;
import mdc.components.cards.ICard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * An abstract class for the deck
 */
public class AbstractPile {
    protected ArrayList<AbstractCard> cards;

    public void addCard(ICard card){
    }

    public void shuffle() {//洗牌
        Collections.shuffle(cards);
    }

    public void sort() {
        cards.sort(Comparator.comparingInt(AbstractCard::getTurnMoney));
    }

    public void clear() {
        cards.clear();
    }
}
