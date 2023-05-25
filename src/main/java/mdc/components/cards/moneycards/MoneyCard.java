package mdc.components.cards.moneycards;

import mdc.components.cards.ICard;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.piles.ownbank.OwnBank;
import mdc.components.players.Player;

public class MoneyCard implements ICard {
    private int turnMoney;

    public MoneyCard(int turnMoney){
        this.turnMoney = turnMoney;
    }

    public void play(Player player, MoneyCard card){
        OwnBank ownBank =player.getOwnBank();
        ownBank.addCard(card);
    }

    @Override
    public void deal(DrawPile pile) {
        pile.addCard(this);
    }

    @Override
    public void discard(DrawPile pile) {
        pile.addCard(this);
    }

    public int getTurnMoney() {
        return turnMoney;
    }
}
