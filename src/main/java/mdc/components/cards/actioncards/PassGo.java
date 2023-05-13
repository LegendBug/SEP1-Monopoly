package mdc.components.cards.actioncards;

import mdc.components.cards.CardInterface;
import mdc.components.piles.drawpile.DrawPile;

/**
 * 摸两张
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 */
public class PassGo implements ActionInterface, CardInterface {
    private String name;
    private int turnMoney;
    private boolean isActing;


    public PassGo(String name,int turnMoney){
        this.name=name;
        this.turnMoney=turnMoney;
        isActing=true;
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getTurnMoney() {
        return turnMoney;
    }

    @Override
    public boolean isActing() {
        return isActing;
    }

    @Override
    public void setActing(boolean act) {
        isActing=act;
    }

    @Override
    public void discard(DrawPile pile) {
        pile.addCards((CardInterface) this);
    }
}
