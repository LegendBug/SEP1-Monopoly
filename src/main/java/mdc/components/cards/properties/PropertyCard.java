package mdc.components.cards.properties;

import mdc.components.cards.CardPhase;
import mdc.components.cards.CardColor;
import mdc.components.cards.actioncards.Hotel;
import mdc.components.cards.actioncards.House;
import mdc.components.piles.DrawPile;
import mdc.states.game.MDCGame;

import java.util.ArrayList;

/**
 * Used to create single-color property cards
 */
public class PropertyCard extends AbstractPropertyCard {
    protected ArrayList<Integer> rents = new ArrayList<>(); // The rent required for the current property

    public PropertyCard() {
        super();
        this.needOwnPropertyPile = true;
    }

    // Level 2 card
    public PropertyCard(int turnMoney, CardColor color, int rent1, int rent2) {
        this();
        this.turnMoney = turnMoney;
        this.color = color;
        this.rents.add(rent1);
        this.rents.add(rent2);
    }

    // Level 3 card
    public PropertyCard(int turnMoney, CardColor color, int rent1, int rent2, int rent3) {
        this();
        this.turnMoney = turnMoney;
        this.color = color;
        this.rents.add(rent1);
        this.rents.add(rent2);
        this.rents.add(rent3);
    }

    // Level 4 card
    public PropertyCard(int turnMoney, CardColor color, int rent1, int rent2, int rent3, int rent4) {
        this();
        this.turnMoney = turnMoney;
        this.color = color;
        this.rents.add(rent1);
        this.rents.add(rent2);
        this.rents.add(rent3);
        this.rents.add(rent4);
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.ownPropertyPhase) {
                // Currently a monochrome card, straight to the end
                if (game.getColors().get(game.getCurrPropertyIndex()) == color) {
                    isPhaseOver = true;
                }
            }
        }
    }

    /**
     * Increase the rent of the current stage property
     */
    public void addRent(int addRent) {
        int maxRent = rents.get(rents.size() - 1) + addRent;
        rents.set(rents.size() - 1, maxRent);
    }

    public CardColor getColor() {
        return color;
    }

    /**
     * Get the rent of the property at the current stage
     */
    public int getRent(int size) {
        switch (size) {
            case 1 -> {
                return rents.get(0);
            }
            case 2 -> {
                return rents.get(1);
            }
            case 3 -> {
                return rents.get(2);
            }
            case 4 -> {
                return rents.get(3);
            }
        }
        return 0;
    }

    public ArrayList<Integer> getRents() {
        return rents;
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.needOwnPropertyPile = true;
    }

    @Override
    public String toString() {
        return color.toString();
    }
}