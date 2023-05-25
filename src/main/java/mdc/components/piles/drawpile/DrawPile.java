package mdc.components.piles.drawpile;

import mdc.components.cards.ICard;
import mdc.components.players.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 弃牌堆，依托答辩
 */
public class DrawPile {
    private final List<ICard> cards;

    public DrawPile() {
        cards = new ArrayList<>();
    }

    public int size() {
        return cards.size();
    }

    public void shuffle() {//洗牌
        Collections.shuffle(cards);
    }

    public void deal(Player player, int number) { //发指定数量
        player.getOwnPlayerPile().addCards(takeCards(number));
    }

    private ICard takeCard() {
        return cards.remove(cards.size() - 1);
    }

    public List<ICard> takeCards(int value) {
        List<ICard> tempCards = new ArrayList<>();
        for (int k = 0; k < value; k++) {
            tempCards.add(takeCard());
        }
        return tempCards;
    }

    public void clearAll() {
        cards.clear();
    }

    public void addCard(ICard card) {
        cards.add(card);
    }
}
