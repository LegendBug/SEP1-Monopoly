package mdc.components.cards.actioncards;

import mdc.components.cards.AbstractCard;
import mdc.components.cards.CardColor;
import mdc.components.cards.CardPhase;
import mdc.components.players.Player;
import mdc.states.game.MDCGame;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Refuse to be influenced by your opponent's action card
 */
public class JustSayNo extends AbstractActionCard {
    protected boolean needOtherPile; // Whether card need to play justSayNo in other players pile
    protected boolean needOtherBank; // Whether it is necessary to operate the other bank
    protected boolean needOtherProperty; // Whether to operate the other's property
    protected boolean needChooseOpponent; // Whether to select an opponent (no all opponents, not no opponents)
    protected boolean hasSayNo; // Whether the other person typed JustSayNo
    protected boolean hasMoveToNext; // Whether to move to the next opponent
    protected boolean isOpponentTurn; // Whether it is operated by an adversary
    protected Player tempOpponent; // The opponent that is currently performing the operation
    protected LinkedList<Player> opponents; // The opponents' queue

    public JustSayNo(int turnMoney) {
        super(turnMoney);
        this.isPlayable = false; // Delete the play key
        this.needOtherPile = true;
        this.needOtherBank = false;
        this.needOtherProperty = false;
        this.needChooseOpponent = false;
        this.hasSayNo = false;
        this.hasMoveToNext = false;
        this.isOpponentTurn = false;
        this.tempOpponent = null;
        this.opponents = new LinkedList<>();
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.chooseOpponentsPhase) {
                // Enter the selection of opponents
                chooseOpponents(game);
            } else if (phase == CardPhase.otherPilePhase) {
                // We're done. We're on to the invulnerability stage
                askForSayNo(game);
            }
        }
    }

    protected void chooseOpponents(MDCGame game) {
        if (!needChooseOpponent) {
            // Pick your opponents and move on to the next stage
            for (int i = 1; i < game.getPlayers().length; i++) {
                opponents.addLast(game.getPlayers()[(i + game.currPlayerIndex) % game.getPlayers().length]);
            }
            phase = CardPhase.otherPilePhase;
        } else {
            // Select an opponent
            if (game.getSelectButton().isIfActive()) {
                // Calculates the index of the current opponent in the player array
                int realOpponentIndex = (game.getCurrOpponentIndex() + game.currPlayerIndex + 1) % game.getPlayerNum();
                opponents.addLast(game.getPlayers()[realOpponentIndex]);
                phase = CardPhase.otherPilePhase;
                game.getSelectButton().resetButton();
                game.getGameInfoBar().add("Player " + realOpponentIndex + " is selected!");
            }
        }

    }

    protected void askForSayNo(MDCGame game) {
        if (!hasMoveToNext) {
            if (opponents.size() == 0) {
                if (needOtherBank) phase = CardPhase.otherBankPhase;
                else if (needOtherProperty) phase = CardPhase.otherPropertyPhase;
                game.getGameInfoBar().add("End of " + game.getCurrCard().getClass().getSimpleName() + ".");
                isPhaseOver = true;
            } else moveToNext(game);
            // Switch to the next opponent
        } else if (game.getPlayButton().isIfActive()) {
            // Ask if the opponent plays justSayNo
            if (checkForCard(game, "JustSayNo")) {
                hasMoveToNext = false; // Play JustSayNo and move on to your next opponent
            }
            game.getPlayButton().resetButton();
        } else if (game.getCancelButton().isIfActive()) {
            // No typing, switch to another stage
            if (needOtherBank) phase = CardPhase.otherBankPhase;
            else if (needOtherProperty) {
                phase = CardPhase.otherPropertyPhase;
                game.setCurrPropertyIndex(game.getColors().indexOf(CardColor.yellow)); // Make it yellow
            }
            game.getButtons().clear();
            game.getButtons().add(game.getSelectButton());
            game.getCancelButton().resetButton();
        }
    }

    protected void moveToNext(MDCGame game) {
        tempOpponent = opponents.removeFirst(); // Remove the leader of the team
        game.setCurrPlayerPile(tempOpponent.getOwnPlayerPile()); //Replace the display deck with an opponent, without direct access to the current player
        game.setCurrBankPile(tempOpponent.getOwnBank());
        game.setCurrPropertyPile(tempOpponent.getOwnProperty());
        game.getButtons().clear();
        game.getButtons().add(game.getPlayButton()); // Added a button that asks if you typed justSayNo
        game.getButtons().add(game.getCancelButton());
        game.getGameInfoBar().add("Player " + game.getPlayerIndex(tempOpponent) + ", do you want to Just Say No?");
        hasMoveToNext = true;
    }

    protected boolean checkForCard(MDCGame game, String cardName) {
        ArrayList<AbstractCard> playerCards = game.getCurrPlayerPile().getCards();
        for (AbstractCard card : playerCards) {
            if (card.getClass().getSimpleName().equals(cardName)) {
                int index = playerCards.indexOf(card);
                game.getCurrPlayerPile().removeCard(index); // Hit the first JustSayNo, remove it
                game.getGameInfoBar().add("Player " + game.getPlayerIndex(tempOpponent) + " just said NO to Player " + game.currPlayerIndex);
                return true;
            }
        }
        return false;
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.isPlayable = false;
        this.needChooseOpponent = false;
        this.needOtherPile = false;
        this.needOtherBank = false;
        this.needOtherProperty = false;
        this.opponents = new LinkedList<>();
    }
}
