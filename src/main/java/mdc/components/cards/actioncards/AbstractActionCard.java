package mdc.components.cards.actioncards;

import mdc.components.cards.CardPhase;
import mdc.components.cards.properties.AbstractPropertyCard;
import mdc.states.game.MDCGame;

/**
 * An abstract class of action cards. Do not participate in the creation of specific cards
 */
public abstract class AbstractActionCard extends AbstractPropertyCard {
    protected boolean needOwnPile; // Whether the action involves the current player's hand

    public AbstractActionCard(int turnMoney) {
        super();
        this.turnMoney = turnMoney;
        this.needOwnPile = false;
    }

    /**
     * The method used to perform all action card functions
     * @param game Game master object
     */
    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            // Check if the play button is pressed (if you need to select the property to be processed in the parent class)
            if (!needOwnPropertyPile && phase == CardPhase.waitingPhase) {
                // No need to hand your own cards, enter the selection of opponents
                if (!needOwnPile && game.getPlayButton().isIfActive()) {
                    // Need to select the opponent, then enter the selection of opponents
                    phase = CardPhase.chooseOpponentsPhase;
                    game.setCurrOpponentIndex(game.getCurrOpponentIndex() + 1);
                    game.getButtons().clear();
                    game.getButtons().add(game.getSelectButton());
                    game.getPlayButton().resetButton();
                    game.getGameInfoBar().add("Select your opponent!");
                } else if (needOwnPile && game.getPlayButton().isIfActive()) {
                    phase = CardPhase.ownPilePhase;
                    game.getPlayButton().resetButton();
                }
            }
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.needOwnPile = false;
    }
}