package mdc.components.cards.properties;

import mdc.components.cards.AbstractCard;
import mdc.components.cards.CardPhase;
import mdc.components.cards.CardColor;
import mdc.states.game.MDCGame;

/**
 * The abstract property card, which is actually the parent of the action card
 */
public abstract class AbstractPropertyCard extends AbstractCard {
    protected boolean isPlayable; // Whether it can be played as money
    protected boolean needOwnPropertyPile; // Whether the current player's property needs to be manipulated
    protected CardColor color; // Color of the current card

    public AbstractPropertyCard() {
        super();
        this.isPlayable = true;
        this.needOwnPropertyPile = false;
    }

    @Override
    public void play(MDCGame game) {
        super.play(game);
        // Wait phase set button
        if (phase == CardPhase.waitingPhase && !game.getButtons().contains(game.getPlayButton())) {
            game.getButtons().add(game.getPlayButton());
            if (game.getButtons().contains(game.getSaveButton())) {
                game.getButtons().set(2, game.getSaveButton()); // Place the save button one to the left of cancel
                game.getButtons().set(1, game.getPlayButton());
            }
            if (!isPlayable) {
                game.getButtons().remove(game.getPlayButton());
                game.getPlayButton().resetButton();
            }
        }
        // Check whether the play button is pressed, and first enter the stage of selecting your own property
        if (phase == CardPhase.waitingPhase && needOwnPropertyPile && game.getPlayButton().isIfActive()) {
            // Switch to ownProperty
            phase = CardPhase.ownPropertyPhase;
            setCurrPropertyColor(game);
            game.getButtons().clear();
            game.getButtons().add(game.getSelectButton());
            game.getPlayButton().resetButton();
        }

    }

    protected void setCurrPropertyColor(MDCGame game) {
        if (color == null || color == CardColor.fullColor) {
            game.setCurrPropertyIndex(game.getColors().indexOf(CardColor.yellow)); // Make it yellow
        } else if (color.toString().split("_").length == 2) {
            CardColor currColor = CardColor.valueOf(color.toString().split("_")[0]);
            game.setCurrPropertyIndex(game.getColors().indexOf(currColor)); // Set it to color number one
        } else if (color.toString().split("_").length == 1) {
            game.setCurrPropertyIndex(game.getColors().indexOf(color)); // Set to the current color
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.isPlayable = true;
        this.needOwnPropertyPile = false;
    }
}