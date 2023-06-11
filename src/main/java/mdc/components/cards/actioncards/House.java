package mdc.components.cards.actioncards;

import mdc.components.cards.CardColor;
import mdc.components.cards.CardPhase;
import mdc.states.game.MDCGame;

/**
 * 放在成套的土地上收租加租金
 *
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para value:加多少钱
 * @para isActing:判断当前行动卡是否在生效
 */
public class House extends AbstractActionCard {

    public House(int turnMoney) {
        super(turnMoney);
        this.needOwnPropertyPile = true;
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.ownPropertyPhase && game.getSelectButton().isIfActive()) {
                CardColor color = game.getColors().get(game.getCurrPropertyIndex());
                if (color != CardColor.railRoad && color != CardColor.utility) {
                    game.getCurrPropertyPile().addRent(turnMoney, color);
                    isPhaseOver = true;
                }
                game.getSelectButton().resetButton();
            }
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.needOwnPropertyPile = true;
    }
}