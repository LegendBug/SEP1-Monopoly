package mdc.components.piles.playerpile;

import mdc.components.cards.ICard;

import mdc.components.piles.drawpile.DrawPile;


import java.util.ArrayList;
import java.util.List;

/**
 * 手牌
 */
public class OwnPlayerPile {
    private List<ICard> cards;

    public OwnPlayerPile(){
        cards=new ArrayList<>();
    }

    public int size() {
        return cards.size();
    }

    public List<ICard> getCards() {
        return cards;
    }

    public void addCard(ICard card){
        cards.add(card);
    }

    public void addCards(List<ICard> newCards) {
        cards.addAll(newCards);
    }

    public void clear(){
        cards.clear();
    }

    public void takeCards(ICard card, DrawPile pile){
        cards.remove(card);
        pile.addCard(card);
    }
}
