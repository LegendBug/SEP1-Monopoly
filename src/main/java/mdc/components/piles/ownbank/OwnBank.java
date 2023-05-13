package mdc.components.piles.ownbank;

import mdc.components.cards.CardInterface;
import mdc.components.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家银行
 */
public class OwnBank {
    private int money;
    private Player player;
    private List<CardInterface> cards =new ArrayList<>();

    public OwnBank(Player player){
        this.player=player;
        money=0;
    }

    public void addMoney(int m) {
        this.money += m;
    }

    public void clear(){
        cards.clear();
        money=0;
    }

    //临时方法，让用户选择赔钱的卡，涉及到listenner到时候再改，卡的数值总合大于等于value，删除选择的卡,返回总合的值
    public int choosePayCard(int value){
        return 0;
    }


    public void addCard(CardInterface card){
        cards.add(card);
        money+=card.getTurnMoney();
    }

    public Player getPlayer() {
        return player;
    }

    public int getMoney() {
        return money;
    }

}
