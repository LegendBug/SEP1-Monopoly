package mdc.components.cards.actioncards;

import mdc.components.cards.ICard;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.players.Player;

/**
 * 摸两张
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para value:发几张牌
 * @para isActing:判断当前行动卡是否在生效
 */
public class PassGo extends AbstractActionCard {
    private int turnMoney;
    private int value;
    private boolean isActing;

    public PassGo(int turnMoney){
        this.turnMoney=turnMoney;
        this.value=value;
        isActing=true;
    }


    public void play(Player player,DrawPile pile){
        pile.deal(player,value);
    }

    @Override
    public void deal(DrawPile pile) {
        pile.addCard(this);
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
        pile.addCard((ICard) this);
    }
}
