package mdc.components.cards.actioncards;

import mdc.components.cards.CardInterface;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.piles.playerpiles.ActionPile;
import mdc.components.players.Player;

/**
 * 所有人给两块
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 * @para takeMoney:规定卡牌打出后每人给多少钱
 */

public class Birthday implements ActionInterface, CardInterface {
    private String name;
    private int turnMoney;
    private boolean isActing;
    private int takeMoney;
    public Birthday(String name,int turnMoney,int takeMoney){
        this.name=name;
        this.turnMoney=turnMoney;
        this.takeMoney=takeMoney;
        isActing=true;
    }

    public void play(ActionPile pile, Player player) {
        if (isActing){
            Player[] players=player.getPlayers();
            for (Player p:players){
                if (p!=player){
                    p.takeMoney(player,p,takeMoney);
                }
            }
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
