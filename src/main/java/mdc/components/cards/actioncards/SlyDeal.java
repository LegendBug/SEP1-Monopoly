package mdc.components.cards.actioncards;

import mdc.states.game.MDCGame;

/**
 * Stealing an incomplete property
 */
public class SlyDeal extends DealBreaker {

    public SlyDeal(int turnMoney) {
        super(turnMoney);
        this.needFullSet = false;
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.needFullSet = false;
    }
}
