package mdc.components.cards.actioncards;

import mdc.states.game.MDCGame;

/**
 * 偷一个不成套的地
 *
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 */
public class SlyDeal extends DealBreaker {

    public SlyDeal(int turnMoney) {
        super(turnMoney);
        this.needFullSet = false;
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
        this.needFullSet = false;
    }
}
