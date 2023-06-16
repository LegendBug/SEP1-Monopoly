package mdc.components.cards.actioncards;

import mdc.states.game.MDCGame;

/**
 * DebtCollector, designates a player to give money. Inherited from Birthday
 */

public class DebtCollector extends Birthday {
    public DebtCollector(int turnMoney) {
        super(turnMoney);
        this.needChooseOpponent = true;
        this.expectToPay = 5;
        this.remainToPay = 5;
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
        this.needChooseOpponent = true;
        this.expectToPay = 5;
        this.remainToPay = 5;
    }
}
