package mdc.components.cards.actioncards;

import mdc.components.cards.CardPhase;
import mdc.components.cards.properties.AbstractPropertyCard;
import mdc.states.game.MDCGame;

public abstract class AbstractActionCard extends AbstractPropertyCard {
    protected boolean needOwnPile;

    public AbstractActionCard(int turnMoney) {
        super();
        this.turnMoney = turnMoney;
        this.needOwnPile = false;
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            // 检测play按键是否按下(如需选择房产在父类中处理)
            if (!needOwnPropertyPile && phase == CardPhase.waitingPhase) {
                // 无需自己手牌，进入选择对手阶段
                if (!needOwnPile && game.getPlayButton().isIfActive()) {
                    // 需要选择对手，则进入选择对手阶段
                    System.out.println("switch to chooseOpponents");
                    phase = CardPhase.chooseOpponentsPhase;
                    game.getButtons().clear();
                    game.getButtons().add(game.getSelectButton());
                    game.getPlayButton().resetButton();
                } else if (needOwnPile && game.getPlayButton().isIfActive()) {
                    // 需要自己手牌(PassGo)
                    System.out.println("switch to ownPile");
                    phase = CardPhase.ownPilePhase;
                    game.getPlayButton().resetButton();
                }
            }
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.needOwnPile = false;
    }
}
