package mdc.components.cards.moneycards;

import mdc.components.cards.AbstractCard;
import mdc.states.game.MDCGame;

/**
 * This is the current gold card and cannot be played as an action card
 */
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

    public int getTurnMoney() {
        return turnMoney;
    }
}
