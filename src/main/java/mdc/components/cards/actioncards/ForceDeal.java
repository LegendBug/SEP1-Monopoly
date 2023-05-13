package mdc.components.cards.actioncards;

import mdc.components.cards.CardInterface;
import mdc.components.cards.properties.PropertyInterface;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.piles.playerpiles.ActionPile;
import mdc.components.players.Player;

/**
 * 指定一个玩家交换一个不成套的
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 */
public class ForceDeal implements ActionInterface, CardInterface {
    private String name;
    private int turnMoney;
    private boolean isActing;


    public ForceDeal(String name,int turnMoney){
        this.name=name;
        this.turnMoney=turnMoney;
        isActing=true;
    }

    //应该在使用该方法之前用isFullSet判断是否是一整套
    public void play(ActionPile pile,Player player, Player dealPlayer, PropertyInterface card, PropertyInterface dealCard){
        if (isActing){
            if (!dealPlayer.getOwnProperty(dealPlayer).ifFullSet(dealCard)){
                player.getOwnProperty(player).takeProperty(card,false);
                player.getOwnProperty(player).addProperty(dealCard);
                dealPlayer.getOwnProperty(dealPlayer).takeProperty(dealCard,false);
                dealPlayer.getOwnProperty(dealPlayer).addProperty(card);
                pile.addCards(this);
            }
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
}
