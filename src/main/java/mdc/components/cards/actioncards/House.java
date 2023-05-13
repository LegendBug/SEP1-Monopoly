package mdc.components.cards.actioncards;

import mdc.components.cards.CardInterface;
import mdc.components.cards.properties.PropertyInterface;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.piles.playerpiles.ActionPile;
import mdc.components.players.Player;

/**
 * 放在成套土地上加租金
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para value:加多少钱
 * @para isActing:判断当前行动卡是否在生效
 */
public class House implements ActionInterface, CardInterface {
    private String name;
    private int turnMoney;
    private int value;
    private boolean isActing;


    public House(String name,int turnMoney,int value){
        this.name=name;
        this.turnMoney=turnMoney;
        this.value=value;
        isActing=true;
    }

    public void play(ActionPile pile,Player player, PropertyInterface card, House house){
        if (isActing){
            if (player.getOwnProperty(player).ifFullSet(card)){
                player.getOwnProperty(player).addHouse(house,card.getColor());
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

    public int getValue() {
        return value;
    }
}
