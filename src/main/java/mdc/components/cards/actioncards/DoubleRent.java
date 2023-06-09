package mdc.components.cards.actioncards;

import mdc.components.cards.ICard;
import mdc.components.piles.DrawPile;
import mdc.components.piles.ActionPile;

/**
 * 跟一个rent卡一起用，加倍
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 */

public class DoubleRent extends AbstractActionCard {
    private int turnMoney;
    private boolean isActing;

    public DoubleRent(int turnMoney){
        this.turnMoney=turnMoney;
        isActing=true;
    }

    @Override
    public void deal(DrawPile pile) {
        pile.addCard(this);
    }

    public void play(ActionPile pile, RentCard card){
        if (isActing){
            pile.addCards(this);
        }
    }

    @Override
    public int getTurnMoney() {
        return turnMoney;
    }

    @Override
    public void discard(DrawPile pile) {
        pile.addCard((ICard) this);
    }
}
