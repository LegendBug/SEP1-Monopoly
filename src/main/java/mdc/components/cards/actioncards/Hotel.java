package mdc.components.cards.actioncards;

import mdc.states.game.MDCGame;

/**
 * 放在成套的土地上收租加租金
 *
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para value:加多少钱
 * @para isActing:判断当前行动卡是否在生效
 */
public class Hotel extends House {
    protected int addRent;

    public Hotel(int turnMoney) {
        super(turnMoney);
        this.addRent = 4;
        this.needOwnPropertyPile = true;
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
    }
}
