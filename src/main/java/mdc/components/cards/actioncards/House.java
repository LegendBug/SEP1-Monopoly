package mdc.components.cards.actioncards;

import mdc.components.cards.properties.PropertyCard;
import mdc.components.piles.DrawPile;
import mdc.components.piles.ActionPile;
import mdc.components.players.Player;

/**
 * 放在成套土地上加租金
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para value:加多少钱
 * @para isActing:判断当前行动卡是否在生效
 */
public class House extends AbstractActionCard {
    private boolean isActing;


    public House(int turnMoney){
        this.turnMoney=turnMoney;
        isActing=true;
    }

    @Override
    public void deal(DrawPile pile) {
        pile.addCard(this);
    }

    public void play(ActionPile pile, Player player, PropertyCard card, House house){
        if (isActing){
            if (player.getOwnProperty().ifFullSet(card)){
                player.getOwnProperty().addHouse(house,card.getColor());
                pile.addCards(this);
            }
        }
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
