package mdc.components.cards.actioncards;

import mdc.components.cards.CardColor;
import mdc.components.cards.CardPhase;
import mdc.components.cards.properties.PropertyCard;
import mdc.states.game.MDCGame;

import java.util.Stack;

/**
 * Assign a player to swap an un-full-set one
 */
public class ForceDeal extends DealBreaker {
    protected CardColor ownColor; // The player selects the color of the property
    protected CardColor opponentColor; // Select the color of the other person's property
    protected Stack<PropertyCard> ownProperty; // The player offers the property in exchange

    public ForceDeal(int turnMoney) {
        super(turnMoney);
        this.needOwnPropertyPile = true;
        this.needFullSet = false;
        this.ownProperty = new Stack<>();
    }

    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.ownPropertyPhase && game.getSelectButton().isIfActive()) {
                // Choose the property you want to swap
                ownColor = game.getColors().get(game.getCurrPropertyIndex());
                if (game.getCurrPlayer().getOwnProperty().getProperty(ownColor).size() != 0) {
                    while (!game.getCurrPlayer().getOwnProperty().getProperty(ownColor).isEmpty())
                        ownProperty.add(game.getCurrPlayer().getOwnProperty().getProperty(ownColor).pop());
                    phase = CardPhase.chooseOpponentsPhase;
                } else {
                    game.getGameInfoBar().add("The current property is empty！");
                    isPhaseOver = true;
                }
                game.getSelectButton().resetButton();
            } else if (phase == CardPhase.otherPropertyPhase && game.getSelectButton().isIfActive()) {
                // Exchange property
                int cardIndex = game.getCurrPropertyIndex();
                opponentColor = game.getColors().get(cardIndex); // Gets the property color selected by your opponent
                game.getCurrPlayer().getOwnProperty().clear(opponentColor); // Clears properties that the player wants to add card colors to
                // Adds rival properties to the property
                if (tempOpponent.getOwnProperty().takeProperty(opponentColor, game.getCurrPlayer().getOwnProperty(), needFullSet)) {
                    game.getGameInfoBar().add("You traded " + ownColor +
                            " property for Player " + game.getPlayerIndex(tempOpponent) + "'s " + color + " one!");
                } else game.getGameInfoBar().add("This property is empty!");
                tempOpponent.getOwnProperty().clear(ownColor);// Clear properties that opponents have been added to
                tempOpponent.getOwnProperty().addCards(ownProperty); // Join player-provided properties
                game.getSelectButton().resetButton();
                isPhaseOver = true;
            }
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.needOwnPropertyPile = true;
        this.needFullSet = false;
        this.ownProperty = new Stack<>();
    }
}
