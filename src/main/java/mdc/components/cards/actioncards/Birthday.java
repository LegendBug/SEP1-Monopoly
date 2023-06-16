package mdc.components.cards.actioncards;

import mdc.components.cards.CardPhase;
import mdc.states.game.MDCGame;

/**
 * The action card birthday, used to collect money from all players. Inherits from JustSayNo
 */
public class Birthday extends JustSayNo {
    protected int expectToPay; // The amount the opponent is expected to pay after the current action card is played
    protected int remainToPay; // Record how much the opponent has left to pay

    public Birthday(int turnMoney) {
        super(turnMoney);
        this.isPlayable = true;
        this.needChooseOpponent = false;
        this.needOtherBank = true;

        this.expectToPay = 2;
        this.remainToPay = 2;
    }

    @Override
    public void play(MDCGame game) {
        super.play(game);
        if (!isPhaseOver) {
            if (phase == CardPhase.otherBankPhase) {
                if (remainToPay <= 0 || tempOpponent.getOwnBank().size() == 0) {
                    // enough money, finish
                    game.setCurrOpponentIndex(game.getCurrOpponentIndex() + 1); // Add one to the current opponent index
                    remainToPay = expectToPay;
                    phase = CardPhase.otherPilePhase;
                    hasMoveToNext = false;
                } else if (game.getSelectButton().isIfActive()) {
                    // draw money
                    int cardIndex = game.getCurrBankCardIndex() == -1 ? 0 : game.getCurrBankCardIndex();
                    remainToPay -= tempOpponent.getOwnBank().getCards().get(cardIndex).getTurnMoney();
                    tempOpponent.getOwnBank().takeCard(cardIndex, game.getCurrPlayer().getOwnBank()); // Add the selected money to the playing player's deck
                    game.setCurrBankCardIndex(game.getCurrBankPile().size() - 1); // Change the index to the last one
                    game.getSelectButton().resetButton();
                }
            }
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.isPlayable = true;
        this.needChooseOpponent = false;
        this.needOtherBank = true;
        this.expectToPay = 2;
        this.remainToPay = 2;
        this.isOpponentTurn = false;
        this.tempOpponent = null;
    }
}
