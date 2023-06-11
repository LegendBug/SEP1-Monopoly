package mdc.components.cards.actioncards;

import mdc.components.cards.CardColor;
import mdc.components.cards.CardPhase;
import mdc.states.game.MDCGame;

/**
 * 偷一整套
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 */

public class DealBreaker extends JustSayNo {
    protected boolean needFullSet;

    public DealBreaker(int turnMoney){
        super(turnMoney);
        this.isPlayable = true;
        this.needChooseOpponent = true;
        this.needOtherProperty = true;
        this.needFullSet = true;
    }

    @Override
    public void play(MDCGame game){
        if (!isPhaseOver) {
            super.play(game);
            if (!needOwnPropertyPile && phase == CardPhase.otherPropertyPhase) {
                if (game.getSelectButton().isIfActive()) {
                    // 取房产加入自己牌堆
                    int cardIndex = game.getCurrPropertyIndex();
                    CardColor color = game.getColors().get(cardIndex);
                    game.getCurrPlayer().getOwnProperty().clear(color); // 清除当前牌堆
                    tempOpponent.getOwnProperty().takeProperty(color, game.getCurrPlayer().getOwnProperty(), needFullSet);
                    game.getSelectButton().resetButton();
                    isPhaseOver = true;
                }
            }
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.isPlayable = true;
        this.needChooseOpponent = true;
        this.needOtherProperty = true;
        this.needFullSet = true;
    }
}
