package mdc.components.piles;

import mdc.components.cards.AbstractCard;
import mdc.components.cards.ICard;
import mdc.components.piles.AbstractPile;
import mdc.components.players.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 弃牌堆，依托答辩
 */
public class DrawPile extends AbstractPile {
    public DrawPile() {
        cards = new ArrayList<>();
    }

    public int size() {
        return cards.size();
    }

    public void deal(Player player, int number) { //发指定数量
        player.getOwnPlayerPile().addCards(takeCards(number));
    }

    private AbstractCard takeCard() {
        return cards.remove(cards.size() - 1);
    }

    public ArrayList<AbstractCard> takeCards(int value) {
        ArrayList<AbstractCard> tempCards = new ArrayList<>();
        for (int k = 0; k < value; k++) {
            tempCards.add(takeCard());
        }
        return tempCards;
    }

    @Override
    public void addCard(ICard card) {
        cards.add((AbstractCard) card);
    }
}
