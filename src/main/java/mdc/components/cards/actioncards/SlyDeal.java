package mdc.components.cards.actioncards;

import mdc.components.cards.properties.PropertyCard;
import mdc.components.piles.DrawPile;
import mdc.components.piles.ActionPile;
import mdc.components.players.Player;

/**
 * 偷一个不成套的地
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 */
public class SlyDeal extends AbstractActionCard {
    private int turnMoney;
    private boolean isActing;


    public SlyDeal(int turnMoney){
        this.turnMoney=turnMoney;
        isActing=true;
    }

    public void play(ActionPile pile, Player player, Player payPlayer, PropertyCard property){
//        if (isActing){
//            player.getOwnProperty().addProperty(property);
//            payPlayer.getOwnProperty().takeProperty(property,false);
//            pile.addCards(this);
//        }
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
    public void discard(DrawPile pile) {
        pile.addCard(this);
    }
}
