package mdc.components.cards.moneycards;

import mdc.components.cards.CardInterface;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.piles.ownbank.OwnBank;
import mdc.components.players.Player;

public class MoneyCard implements CardInterface {
    private int turnMoney;
    public MoneyCard(int turnMoney){
        this.turnMoney = turnMoney;
    }

    public void play(Player player, MoneyCard card){
        OwnBank ownBank =player.getOwnBank(player);
        ownBank.addCard(card);
    }

    @Override
    public void discard(DrawPile pile) {
        pile.addCards(this);
    }

    public int getTurnMoney() {
        return turnMoney;
    }
}
