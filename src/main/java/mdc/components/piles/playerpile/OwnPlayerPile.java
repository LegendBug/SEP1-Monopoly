package mdc.components.piles.playerpile;

import mdc.components.cards.CardInterface;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.players.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * 手牌
 */
public class OwnPlayerPile {
    private Player player;
    private List<CardInterface> cards;
    public OwnPlayerPile(Player player){
        this.player=player;
        cards=new ArrayList<>();
    }

    public Player getPlayer() {
        return player;
    }

    public List<CardInterface> getCards() {
        return cards;
    }

    public void addCards(CardInterface card){
        cards.add(card);
    }

    public void clear(){
        cards.clear();
    }

    public void takeCards(CardInterface card, DrawPile pile){
        cards.remove(card);
        pile.addCards(card);
    }
}
