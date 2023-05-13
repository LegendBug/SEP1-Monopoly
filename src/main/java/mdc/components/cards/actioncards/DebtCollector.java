package mdc.components.cards.actioncards;

import mdc.components.cards.CardInterface;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.piles.playerpiles.ActionPile;
import mdc.components.players.Player;

/**
 * 指定一个人给赔钱
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 * @para payValue:赔多少钱
 */

public class DebtCollector implements ActionInterface, CardInterface {
    private String name;
    private int turnMoney;
    private boolean isActing;
    private int payValue;

    public DebtCollector(String name,int turnMoney,int payValue){
        this.name=name;
        this.turnMoney=turnMoney;
        this.payValue=payValue;
        isActing=true;
    }

    public void play(ActionPile pile,Player player, Player payPlayer){
        if (isActing) {
            player.takeMoney(player,payPlayer,payValue);
            pile.addCards(this);
        }

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
    public int getPayValue() {
        return payValue;
    }
}
