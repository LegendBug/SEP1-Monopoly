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

    //临时方法，让用户选择赔钱的卡，涉及到listener到时候再改，卡的数值总合大于等于value，删除选择的卡,返回总合的值
    public int choosePayCard(int value) {
        return 0;
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
