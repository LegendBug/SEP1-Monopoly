package mdc.components.cards.actioncards;

import mdc.components.cards.CardPhase;
import mdc.components.players.Player;
import mdc.states.game.MDCGame;

/**
 * 所有人给两块
 *
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 * @para takeMoney:规定卡牌打出后每人给多少钱
 */

public class Birthday extends AbstractActionCard {
    protected int payValue;
    protected int remainToPay;
    protected boolean isOpponentTurn;
    protected Player tempPlayer;

    public Birthday(int turnMoney) {
        this.turnMoney = turnMoney;
        this.payValue = 2;
        this.remainToPay = 2;
        this.isOpponentTurn = false;
        this.needChooseOpponent = false;
        this.needOtherBank = true;
        this.tempPlayer = null;
    }

    @Override
    public void play(MDCGame game) {
        // ActionPile pile, Player player, Player[] players
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.otherBankPhase) {
                if (isOpponentTurn) {
                    // 取钱
//                    System.out.println("index" + game.getCurrOpponentIndex());
                    if (game.getSelectButton().isIfActive()) {
                        int cardIndex = game.getCurrBankCardIndex();
                        remainToPay -= tempPlayer.getOwnBank().getCards().get(cardIndex).getTurnMoney();
                        tempPlayer.getOwnBank().takeCard(cardIndex, game.getCurrPlayer().getOwnBank()); // 将选中的钱加入出牌玩家的牌堆中
                        game.getSelectButton().resetButton();
                    }
                    if (remainToPay <= 0 || tempPlayer.getOwnBank().size() == 0) {
                        remainToPay = payValue;
                        isOpponentTurn = false;
                    }
                } else {
                    // 没有剩余对手，出牌结束
                    if (opponents.size() == 0) isPhaseOver = true;
                        // 在队列中切换对手
                    else {
                        tempPlayer = opponents.removeFirst(); // 将队首的玩家移出
                        game.setCurrOpponentIndex(game.getCurrOpponentIndex() + 1); // 当前对手index加一
                        game.setCurrPlayerPile(tempPlayer.getOwnPlayerPile()); // 将展示牌堆换成对手，不直接访问当前出牌玩家
                        game.setCurrBankPile(tempPlayer.getOwnBank());
                        game.setCurrPropertyPile(tempPlayer.getOwnProperty());
                        isOpponentTurn = true;
                    }
                }
            }
        }
    }
}
