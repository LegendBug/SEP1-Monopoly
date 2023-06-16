package mdc.components.cards.actioncards;

import mdc.states.game.MDCGame;

/**
 * Use it with a rent card, double it
 */

public class DoubleRent extends AbstractActionCard {

    public DoubleRent(int turnMoney){
        super(turnMoney);
        this.isPlayable = false;
    }

    @Override
    public void play(MDCGame game){
        if (!isPhaseOver) {
            super.play(game);
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.isPlayable = false;
    }
}
