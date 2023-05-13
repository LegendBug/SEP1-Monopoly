package mdc.components.players;

import mdc.components.cards.properties.PropertyInterface;
import mdc.components.piles.ownbank.OwnBank;
import mdc.components.piles.ownproperties.OwnProperty;
import mdc.components.piles.playerpile.OwnPlayerPile;

import java.util.List;

/**
 * 创建玩家及相应功能
 */
public class Player {
    private int number;
    private Player[] players;
    private OwnBank ownBank;
    private OwnProperty ownProperty;
    private OwnPlayerPile ownPlayerPile;

    public Player(int number){
        players=new Player[number];
        for (Player player:players){
            ownBank=new OwnBank(player);
            ownProperty =new OwnProperty(player);
            ownPlayerPile=new OwnPlayerPile(player);
            player=new Player(ownBank, ownProperty,ownPlayerPile);
        }
    }
    public Player(OwnBank ownBank, OwnProperty ownProperty, OwnPlayerPile ownPlayerPile){
        this.ownBank = ownBank;
        this.ownProperty = ownProperty;
        this.ownPlayerPile=ownPlayerPile;
    }

    public Player[] getPlayers() {
        return players;
    }

    public OwnBank getOwnBank(Player p) {
        for (Player player:players){
            if (player==p){
                return player.ownBank;
            }
        }
        return null;
    }

    public OwnProperty getOwnProperty(Player p){
        for (Player player:players){
            if (player==p){
                return player.ownProperty;
            }
        }
        return null;
    }


    public void clear(Player player){
        player.ownBank.clear();
    }

    //选择用户赔钱
    public void takeMoney(Player player,Player payPlayer,int value){
        if (payPlayer.ownBank.getMoney()+player.ownProperty.getAsMoney()==0){
            value=0;
        }else if (payPlayer.ownBank.getMoney()+player.ownProperty.getAsMoney()<value){
            player.ownBank.addMoney(payPlayer.ownBank.getMoney()+payPlayer.ownProperty.getAsMoney());
            payPlayer.ownBank.clear();
            payPlayer.ownProperty.clear();
        }else if (payPlayer.ownBank.getMoney()<value&&payPlayer.ownBank.getMoney()+payPlayer.ownProperty.getAsMoney()>value){
            player.ownBank.addMoney(payPlayer.ownBank.getMoney());
            value-=payPlayer.ownBank.getMoney();
            payPlayer.ownBank.clear();
            List<PropertyInterface> cards=payPlayer.ownProperty.choosePayCard(value);
            for (PropertyInterface card:cards){
                player.ownProperty.addProperty(card);
            }
        }else if (payPlayer.ownBank.getMoney()>value){
            player.ownBank.addMoney(payPlayer.ownBank.choosePayCard(value));
        }
    }

    public int getNumber() {
        return number;
    }

    public OwnPlayerPile getOwnPlayerPile() {
        return ownPlayerPile;
    }

}
