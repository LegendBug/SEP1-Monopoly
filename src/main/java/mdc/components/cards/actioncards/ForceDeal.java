package mdc.components.cards.actioncards;

import mdc.components.cards.ICard;
import mdc.components.cards.properties.PropertyCard;
import mdc.components.piles.DrawPile;
import mdc.components.piles.ActionPile;
import mdc.components.players.Player;

/**
 * 指定一个玩家交换一个不成套的
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 */
public class ForceDeal extends AbstractActionCard {
    private int turnMoney;
    private boolean isActing;


    public ForceDeal(int turnMoney){
        this.turnMoney=turnMoney;
        isActing=true;
    }

    @Override
    public void deal(DrawPile pile) {
        pile.addCard(this);
    }

    //应该在使用该方法之前用isFullSet判断是否是一整套
    public void play(ActionPile pile, Player player, Player dealPlayer, PropertyCard card, PropertyCard dealCard){
//        if (isActing){
//            if (!dealPlayer.getOwnProperty().ifFullSet(dealCard)){
//                player.getOwnProperty().takeProperty(card,false);
//                player.getOwnProperty().addProperty(dealCard);
//                dealPlayer.getOwnProperty().takeProperty(dealCard,false);
//                dealPlayer.getOwnProperty().addProperty(card);
//                pile.addCards(this);
//            }
//        }
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
