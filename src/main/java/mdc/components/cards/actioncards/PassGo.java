package mdc.components.cards.actioncards;

import mdc.components.cards.CardPhase;
import mdc.components.cards.ICard;
import mdc.components.piles.DrawPile;
import mdc.components.players.Player;
import mdc.states.game.MDCGame;

/**
 * get two cards
 */
public class PassGo extends AbstractActionCard {
    public PassGo(int turnMoney) {
        super(turnMoney);
        this.needOwnPile = true;
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.ownPilePhase) {
                game.getDrawPile().deal(game.getCurrPlayer(), 2);
                isPhaseOver = true;
            }
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.needOwnPile = true;
    }
}
