package mdc.components.cards.moneycards;

import mdc.components.cards.AbstractCard;
import mdc.components.piles.DrawPile;
import mdc.states.game.MDCGame;

public class MoneyCard extends AbstractCard {

    public MoneyCard(int turnMoney) {
        super();
        this.isMoney = true;
        this.turnMoney = turnMoney;
    }

    @Override
    public void play(MDCGame game) {
        super.play(game);
        game.getButtons().remove(game.getPlayButton());
        game.getPlayButton().resetButton();
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
