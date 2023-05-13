package mdc.components.cards.actioncards;

import mdc.components.cards.CardInterface;
import mdc.components.cards.properties.PropertyInterface;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.piles.playerpiles.ActionPile;
import mdc.components.players.Player;


/**
 * 收租,双色和全色，收多少，钱
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 * @para value:收多少
 */

public class RentCard implements CardInterface,ActionInterface {
    private String name;
    private int turnMoney;
    private boolean isActing;
    private String color1,color2,finalColor;
    private boolean isFullColor;

    public RentCard(String name, int turnMoney,String color1,String color2) {
        this.name = name;
        this.turnMoney = turnMoney;
        this.color1=color1;
        this.color2=color2;
        isActing = true;
        isFullColor=false;
    }

    //创建全色卡牌
    public RentCard(String name,int turnMoney,boolean fullColor){
        this.name = name;
        this.turnMoney = turnMoney;
        isActing=true;
        isFullColor=true;
    }

    public void play(ActionPile pile,Player player) {
        if (isActing){
            Player[] players=player.getPlayers();
            int rent=player.getOwnProperty(player).getRent(this.finalColor);
            for (Player p:players){
                if (p!=player){
                    player.takeMoney(player,p,rent);
                }
            }
            pile.addCards(this);
        }
    }

    public void play(ActionPile pile,Player player,boolean full,Player payPlayer){
        if (isActing){
            int rent=player.getOwnProperty(player).getRent(this.finalColor);
            player.takeMoney(player,payPlayer,rent);
            pile.addCards(this);
        }
    }

    public void play(ActionPile pile,Player player, boolean doubleRent) {
        if (isActing){
            Player[] players=player.getPlayers();
            int rent=player.getOwnProperty(player).getRent(this.finalColor)*2;
            for (Player p:players){
                if (p!=player){
                    player.takeMoney(player,p,rent);
                }
            }
            pile.addCards(this);
        }
    }

    public void play(ActionPile pile,Player player,boolean full,Player payPlayer,boolean doubleRent){
        if (isActing){
            int rent=player.getOwnProperty(player).getRent(this.finalColor)*2;
            player.takeMoney(player,payPlayer,rent);
            pile.addCards(this);
        }
    }

    public void chooseColor(String c){
        finalColor=c;
    }

    public String getName() {
        return name;
    }

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

    public String getFinalColor() {
        return finalColor;
    }

    public String getColor() {
        return color1+" "+color2;
    }

    public boolean isFullColor() {
        return isFullColor;
    }
}

