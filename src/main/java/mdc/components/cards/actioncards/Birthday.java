package mdc.components.cards.actioncards;

import mdc.components.cards.CardPhase;
import mdc.states.game.MDCGame;

/**
 * 所有人给两块
 *
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 * @para takeMoney:规定卡牌打出后每人给多少钱
 */

public class Birthday extends JustSayNo {
    protected int expectToPay; // 当前行动牌打出后，期望对手支付的额数
    protected int remainToPay; // 记录对手剩余需要支付的额数


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
                // TODO 不够用房产补，再不够就结束
                if (remainToPay <= 0 || tempOpponent.getOwnBank().size() == 0) {
                    // 钱给够了
                    game.setCurrOpponentIndex(game.getCurrOpponentIndex() + 1); // 当前对手index加一
                    remainToPay = expectToPay;
                    phase = CardPhase.otherPilePhase;
                    hasMoveToNext = false;
                } else if (game.getSelectButton().isIfActive()) {
                    // 取钱
                    int cardIndex = game.getCurrBankCardIndex() == -1 ? 0 : game.getCurrBankCardIndex();
                    remainToPay -= tempOpponent.getOwnBank().getCards().get(cardIndex).getTurnMoney();
                    tempOpponent.getOwnBank().takeCard(cardIndex, game.getCurrPlayer().getOwnBank()); // 将选中的钱加入出牌玩家的牌堆中
                    game.setCurrBankCardIndex(game.getCurrBankPile().size() - 1); // 将索引更改为最后一张
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
