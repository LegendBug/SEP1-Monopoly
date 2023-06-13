package mdc.components.piles;

import mdc.components.cards.AbstractCard;
import mdc.components.cards.ICard;

import mdc.components.piles.AbstractPile;


import java.util.ArrayList;

/**
 * 手牌
 */
public class OwnPlayerPile extends AbstractPile {

    public OwnPlayerPile(){
        cards=new ArrayList<>();
    }

    public int size() {
        return cards.size();
    }

    public ArrayList<AbstractCard> getCards() {
        return cards;
    }

    @Override
    public void addCard(ICard card){
        cards.add((AbstractCard) card);
    }

    public void takeCard(int currCardIndex, AbstractPile targetPile){
        ICard takenCard = removeCard(currCardIndex);
        targetPile.addCard(takenCard);
    }

    public ICard removeCard(int currCardIndex) {
        return cards.remove(currCardIndex);
    }

    public void addCards(ArrayList<AbstractCard> newCards) {
        cards.addAll(newCards);
    }

}
