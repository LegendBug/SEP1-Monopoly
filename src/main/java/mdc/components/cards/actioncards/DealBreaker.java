package mdc.components.cards.actioncards;

import mdc.components.cards.CardInterface;
import mdc.components.cards.properties.Property;
import mdc.components.cards.properties.PropertyInterface;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.piles.playerpiles.ActionPile;
import mdc.components.players.Player;

import java.util.List;

/**
 * 偷一整套
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 */

public class DealBreaker implements ActionInterface, CardInterface {
    private String name;
    private int turnMoney;
    private boolean isActing;

    public DealBreaker(String name,int turnMoney){
        this.name=name;
        this.turnMoney=turnMoney;
        isActing=true;
    }

    public void play(ActionPile pile,Player payPlayer, PropertyInterface card){
        if (isActing){
            payPlayer.getOwnProperty(payPlayer).takeProperty(card,true);
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
}
