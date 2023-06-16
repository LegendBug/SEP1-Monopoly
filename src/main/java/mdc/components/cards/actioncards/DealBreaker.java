package mdc.components.cards.actioncards;

import mdc.components.cards.CardColor;
import mdc.components.cards.CardPhase;
import mdc.states.game.MDCGame;

/**
 * The action card DealBreaker, is used to steal an entire property from the user.
 */
public class DealBreaker extends JustSayNo {
    protected boolean needFullSet; // Whether it is necessary to obtain the full property of the other party

    public DealBreaker(int turnMoney){
        super(turnMoney);
        this.isPlayable = true;
        this.needChooseOpponent = true;
        this.needOtherProperty = true;
        this.needFullSet = true;
    }

    @Override
    public void play(MDCGame game){
        if (!isPhaseOver) {
            super.play(game);
            if (!needOwnPropertyPile && phase == CardPhase.otherPropertyPhase) {
                if (game.getSelectButton().isIfActive()) {
                    // Take the property and add it to your deck
                    int cardIndex = game.getCurrPropertyIndex();
                    CardColor color = game.getColors().get(cardIndex);
                    game.getCurrPlayer().getOwnProperty().clear(color); // Clear the current deck
                    if (tempOpponent.getOwnProperty().takeProperty(color, game.getCurrPlayer().getOwnProperty(), needFullSet)) {
                        game.getGameInfoBar().add("You acquired all of Player " + game.getPlayerIndex(tempOpponent) +
                                "'s " + color + " property!");
                    } else game.getGameInfoBar().add("This property is not full.");
                    game.getSelectButton().resetButton();
                    isPhaseOver = true;
                }
            }
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.isPlayable = true;
        this.needChooseOpponent = true;
        this.needOtherProperty = true;
        this.needFullSet = true;
    }
}
