package mdc.components.cards.actioncards;

import mdc.components.cards.AbstractCard;
import mdc.components.cards.CardColor;
import mdc.components.cards.CardPhase;
import mdc.states.game.MDCGame;

import java.util.ArrayList;

public class RentCard extends Birthday {
    private boolean isFullColor;
    private boolean isDoubleColor;

    // Create full-color cards
    public RentCard(int turnMoney) {
        super(turnMoney);
        this.needOwnPropertyPile = true;
        this.needChooseOpponent = true; // focus on one player
        this.color = CardColor.fullColor;
        this.isFullColor = true;
        this.isDoubleColor = false;
    }

    // Create double-color cards
    public RentCard(int turnMoney, CardColor color1, CardColor color2) {
        super(turnMoney);
        this.needOwnPropertyPile = true;
        this.color = CardColor.valueOf(color1.toString() + "_" + color2.toString());
        this.isFullColor = false;
        this.isDoubleColor = true;
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.ownPropertyPhase && game.getSelectButton().isIfActive()) {
                chooseColor(game);
                game.getSelectButton().resetButton();
            } else if (phase == CardPhase.ownPilePhase) {
                // Remove buttons
                if (game.getButtons().contains(game.getSelectButton())) {
                    game.getButtons().clear();
                    game.getButtons().add(game.getPlayButton()); // Added a button that asks if you typed justSayNo
                    game.getButtons().add(game.getCancelButton());
                }
                // Ask if you typed DoubleRent
                askForDoubleRent(game);
            }
        }
    }

    private void chooseColor(MDCGame game) {
        // full color
        if (isFullColor) {
            isFullColor = false;
            phase = CardPhase.ownPilePhase; // Switch to ownPile to ask if it doubles
            color = game.getColors().get(game.getCurrPropertyIndex()); // Color fix
            // double color
        } else if (isDoubleColor) {
            isDoubleColor = false;
            String[] colors = color.toString().split("_");
            if (CardColor.valueOf(colors[0]) == game.getColors().get(game.getCurrPropertyIndex())) {
                color = CardColor.valueOf(colors[0]);
                phase = CardPhase.ownPilePhase; // Switch to ownPile to ask if it doubles
            } else if (CardColor.valueOf(colors[1]) == game.getColors().get(game.getCurrPropertyIndex())) {
                color = CardColor.valueOf(colors[1]);
                phase = CardPhase.ownPilePhase; // Switch to ownPile to ask if it doubles
            }
        }
        game.getGameInfoBar().add("Do you want to double the rent?");
    }

    private void askForDoubleRent(MDCGame game) {
        if (game.getPlayButton().isIfActive() || game.getCancelButton().isIfActive()) {
            int multiplier = game.getPlayButton().isIfActive() && checkForCard(game, "DoubleRent") ? 2 : 1;
            expectToPay = game.getCurrPropertyPile().getRent(color) * multiplier;
            remainToPay = expectToPay;
            if (expectToPay == 0) {
                phase = CardPhase.ownPilePhase;
                game.getGameInfoBar().add("This property is empty!");
                isPhaseOver = true;
            } else {
                phase = CardPhase.chooseOpponentsPhase;
            }
            game.setCurrOpponentIndex(game.getCurrOpponentIndex() + 1); // Add one to the current opponent index
            resetButtons(game);
        }
    }

    @Override
    protected boolean checkForCard(MDCGame game, String cardName) {
        if (game.getPlayedCardNum() == 2) return false; // Two cards already played cannot be doubled
        ArrayList<AbstractCard> playerCards = game.getCurrPlayerPile().getCards();
        for (AbstractCard card : playerCards) {
            if (card.getClass().getSimpleName().equals(cardName)) {
                int index = playerCards.indexOf(card);
                game.setPlayedCardNum(game.getPlayedCardNum() + 1);
                game.getCurrPlayerPile().removeCard(index); // Hit the first JustSayNo, remove it
                if (index < game.getCurrPlayerCardIndex())
                    game.setCurrPlayerCardIndex(game.getCurrPlayerCardIndex() - 1);
                return true;
            }
        }
        return false;
    }

    private void resetButtons(MDCGame game) {
        game.getButtons().clear();
        game.getButtons().add(game.getSelectButton());
        game.getCancelButton().resetButton();
        game.getPlayButton().resetButton();
    }

    @Override
    public String toString() {
        if (isFullColor) return "null_null"; // full color card
        else return color.toString();
    }
}