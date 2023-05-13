package mdc.components.cards.actioncards;

import mdc.components.cards.CardInterface;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.piles.playerpiles.ActionPile;

import java.util.List;

/**
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 */
public class JustSayNo implements ActionInterface, CardInterface {
    private String name;
    private int turnMoney;
    private boolean isActing;

    public JustSayNo(String name,int turnMoney){
        this.name=name;
        this.turnMoney=turnMoney;
        isActing=true;
    }

    public void play(ActionPile pile){
        if (isActing){
            List<ActionInterface> pileCards=pile.getCards();
            ActionInterface card=pileCards.get(pileCards.size()-1);
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
    public void discard(DrawPile pile) {
        pile.addCards((CardInterface) this);
    }

    public boolean isActing() {
        return isActing;
    }

    public int getTurnMoney() {
        return turnMoney;
    }

    public String getName() {
        return name;
    }

    @Override
    public void setActing(boolean act) {
        isActing=act;
    }
}
