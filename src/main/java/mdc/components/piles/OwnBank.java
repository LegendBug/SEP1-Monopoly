package mdc.components.piles;

import mdc.components.cards.AbstractCard;
import mdc.components.cards.ICard;

import java.util.ArrayList;

/**
 * 玩家银行
 */
public class OwnBank extends OwnPlayerPile {
    private int money;

    public OwnBank() {
        money = 0;
    }

    public void addMoney(int m) {
        this.money += m;
    }

    @Override
    public void clear() {
        cards.clear();
        money = 0;
    }

    @Override
    public void addCard(ICard card) {
        super.addCard(card);
        sort();
        money += ((AbstractCard) card).getTurnMoney();
    }

    @Override
    public ICard removeCard(int currBankIndex) {
        ICard card = super.removeCard(currBankIndex);
        money -= ((AbstractCard) card).getTurnMoney();
        return card;
    }

    @Override
    public void addCards(ArrayList<AbstractCard> newCards) {
        super.addCards(newCards);
        for (ICard card : newCards) money += ((AbstractCard) card).getTurnMoney();
    }

    public int getMoney() {
        return money;
    }

}
