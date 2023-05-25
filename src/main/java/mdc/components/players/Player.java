package mdc.components.players;

import mdc.components.cards.properties.AbstractPropertyCard;
import mdc.components.piles.ownbank.OwnBank;
import mdc.components.piles.ownproperties.OwnProperty;
import mdc.components.piles.playerpile.OwnPlayerPile;

import java.util.List;

/**
 * 创建玩家及相应功能
 */
public class Player {
    private OwnBank ownBank;
    private OwnProperty ownProperty;
    private OwnPlayerPile ownPlayerPile;

    public Player(OwnBank ownBank, OwnProperty ownProperty, OwnPlayerPile ownPlayerPile){
        this.ownBank = ownBank;
        this.ownProperty = ownProperty;
        this.ownPlayerPile=ownPlayerPile;
    }


    public OwnBank getOwnBank() {
        return ownBank;
    }

    public OwnProperty getOwnProperty(){
        return ownProperty;
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
            List<AbstractPropertyCard> cards=payPlayer.ownProperty.choosePayCard(value);
            for (AbstractPropertyCard card:cards){
                player.ownProperty.addProperty(card);
            }
        }else if (payPlayer.ownBank.getMoney()>value){
            player.ownBank.addMoney(payPlayer.ownBank.choosePayCard(value));
        }
    }


    public OwnPlayerPile getOwnPlayerPile() {
        return ownPlayerPile;
    }

}
