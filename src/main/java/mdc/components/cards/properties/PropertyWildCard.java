package mdc.components.cards.properties;

import mdc.components.cards.CardColor;
import mdc.components.cards.CardPhase;
import mdc.states.game.MDCGame;

import java.util.ArrayList;

public class PropertyWildCard extends PropertyCard {
    private ArrayList<PropertyCard> cards = new ArrayList<>();
    private boolean isDoubleColor;
    private boolean isFullColor;

    // Panchromatic card constructor
    public PropertyWildCard(ArrayList<PropertyCard> cards) {
        super();
        this.isPayable = false;
        this.isDoubleColor = false;
        this.isFullColor = true;
        this.color = CardColor.fullColor;
        this.cards.addAll(cards);
    }

    // Two color card constructor
    public PropertyWildCard(int turnMoney, PropertyCard card1, PropertyCard card2) {
        super();
        this.isDoubleColor = true;
        this.isFullColor = false;
        this.turnMoney = turnMoney;
        this.cards.add(card1);
        this.cards.add(card2);
        this.color = CardColor.valueOf(card1.getColor().toString() + "_" + card2.getColor());
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.ownPropertyPhase && game.getSelectButton().isIfActive()) {
                // full color
                if (isFullColor) {
                    color = game.getColors().get(game.getCurrPropertyIndex()); // Color fix
                    for (PropertyCard card : cards) {
                        if (card.getColor() == color) {
                            rents.addAll(card.getRents()); //Identify the property as the corresponding color
                            break;
                        }
                    }
                    isPhaseOver = true;
                    //double color
                } else {
                    if (cards.get(0).getColor() == game.getColors().get(game.getCurrPropertyIndex())) {
                        color = cards.get(0).color;
                        rents.addAll(cards.get(0).getRents());
                        isPhaseOver = true;
                    } else if (cards.get(1).getColor() == game.getColors().get(game.getCurrPropertyIndex())) {
                        color = cards.get(1).color;
                        rents.addAll(cards.get(1).getRents());
                        isPhaseOver = true;
                    }
                }
                game.getSelectButton().resetButton();
            }
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        if (isFullColor) {
            isPayable = false;
            isFullColor = false;
        } else if (isDoubleColor) {
            isDoubleColor = false;
        }
    }

    @Override
    public String toString() {
        if (isFullColor) return "null"; // full color card
        else if (isDoubleColor) return cards.get(0).getColor().toString() + "_" + cards.get(1).getColor().toString();
        else return color.toString();
    }
}
