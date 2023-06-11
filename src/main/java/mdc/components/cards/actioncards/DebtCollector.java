package mdc.components.cards.actioncards;

import mdc.states.game.MDCGame;

/**
 * 指定一个人给赔钱
 *
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 * @para payValue:赔多少钱
 */

public class DebtCollector extends Birthday {
    public DebtCollector(int turnMoney) {
        super(turnMoney);
        this.needChooseOpponent = true;
        this.expectToPay = 5;
        this.remainToPay = 5;
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.needChooseOpponent = true;
        this.expectToPay = 5;
        this.remainToPay = 5;
    }
}
