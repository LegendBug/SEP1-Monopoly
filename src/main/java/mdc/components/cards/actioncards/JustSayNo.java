package mdc.components.cards.actioncards;

import mdc.components.cards.ICard;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.piles.actionpile.ActionPile;

import java.util.List;

/**
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 */
public class JustSayNo extends AbstractActionCard {
    private int turnMoney;
    private boolean isActing;

    public JustSayNo(int turnMoney){
        this.turnMoney=turnMoney;
        isActing=true;
    }

    public void play(ActionPile pile){
        if (isActing){
            List<AbstractActionCard> pileCards=pile.getCards();
            AbstractActionCard card=pileCards.get(pileCards.size()-1);
            int i=2;
            while (card instanceof JustSayNo||card instanceof DoubleRent){
                card=pileCards.get(pileCards.size()-i);
                i++;
            }
            card.setActing(false);
            pile.addCards(this);
        }
    }

    @Override
    public void deal(DrawPile pile) {
        pile.addCard(this);
    }

    @Override
    public void discard(DrawPile pile) {
        pile.addCard((ICard) this);
    }

    public boolean isActing() {
        return isActing;
    }

    public int getTurnMoney() {
        return turnMoney;
    }

    @Override
    public void setActing(boolean act) {
        isActing=act;
    }
}
