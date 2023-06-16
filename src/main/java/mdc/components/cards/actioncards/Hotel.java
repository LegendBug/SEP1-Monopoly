package mdc.components.cards.actioncards;

import mdc.states.game.MDCGame;

/**
 * On a set of land for rent and rent
 */
public class Hotel extends House {
    protected int addRent;

    public Hotel(int turnMoney) {
        super(turnMoney);
        this.addRent = 4;
        this.needOwnPropertyPile = true;
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
    }
}
