package mdc.components.cards.actioncards;

import mdc.components.cards.CardColor;
import mdc.components.cards.CardPhase;
import mdc.states.game.MDCGame;

/**
 * On a set of land for rent and rent
 */
public class House extends AbstractActionCard {

    public House(int turnMoney) {
        super(turnMoney);
        this.needOwnPropertyPile = true;
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.ownPropertyPhase && game.getSelectButton().isIfActive()) {
                CardColor color = game.getColors().get(game.getCurrPropertyIndex());
                if (color != CardColor.railRoad && color != CardColor.utility) {
                    game.getGameInfoBar().add(game.getCurrPropertyPile().addRent(turnMoney, color));
                    isPhaseOver = true;
                }
                game.getSelectButton().resetButton();
            }
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.needOwnPropertyPile = true;
    }
}