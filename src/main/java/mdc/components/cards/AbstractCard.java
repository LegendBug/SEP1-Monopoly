package mdc.components.cards;

import mdc.components.buttons.Button;
import mdc.states.game.MDCGame;

import java.util.ArrayList;

/**
 * The parent class of all cards
 */
public abstract class AbstractCard implements ICard {
    protected int turnMoney; // face value
    protected boolean isPhaseOver; // Whether the card operation is over
    protected boolean isPayable; // Whether it can be used as money
    protected boolean isMoney; // Whether it is money or not

    protected CardPhase phase;

    public AbstractCard() {
        this.isPayable = true;
        this.isMoney = false;
        this.isPhaseOver = false;
        this.phase = CardPhase.waitingPhase;
    }

    public CardPhase getPhase() {
        return phase;
    }

    public int getTurnMoney() {
        return turnMoney;
    }

    public boolean isPhaseOver() {
        return isPhaseOver;
    }

    /**
     * The method used to perform all action card functions
     * @param game Game master object
     */
    public void play(MDCGame game) {
        ArrayList<Button> buttons = game.getButtons();
        Button saveButton = game.getSaveButton();
        if (phase == CardPhase.waitingPhase) { // Control wait phase
            if (isPayable) {
                if (!buttons.contains(saveButton)) buttons.add(saveButton); // Add a save button as a card
                else if (saveButton.isIfActive()) {
                    isPhaseOver = true; // Hit the save button. It's over
                    saveButton.resetButton();
                }
            } else buttons.remove(saveButton);
        }
    }

    public boolean isPayable() {
        return isPayable;
    }

    public void resetCard() {
        this.isPayable = true;
        this.isMoney = true;
        this.isPhaseOver = false;
        this.phase = CardPhase.waitingPhase;
    }
}
