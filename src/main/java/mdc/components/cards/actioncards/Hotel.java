package mdc.components.cards.actioncards;

import mdc.components.cards.ICard;
import mdc.components.cards.properties.AbstractPropertyCard;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.piles.actionpile.ActionPile;
import mdc.components.players.Player;

/**
 * 放在成套的土地上收租加租金
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para value:加多少钱
 * @para isActing:判断当前行动卡是否在生效
 */
public class Hotel extends AbstractActionCard {
    private int turnMoney;
    private int value;
    private boolean isActing;


    public Hotel(int turnMoney){
        this.turnMoney=turnMoney;
        this.value=value;
        isActing=true;
    }

    @Override
    public void deal(DrawPile pile) {
        pile.addCard(this);
    }

    public void play(ActionPile pile, Player player, AbstractPropertyCard card, Hotel hotel){
        if (isActing){
            if (player.getOwnProperty().ifFullSet(card)){
                player.getOwnProperty().addHotel(hotel, card.getColor());
            }
        }
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

    public int getValue() {
        return value;
    }
}
