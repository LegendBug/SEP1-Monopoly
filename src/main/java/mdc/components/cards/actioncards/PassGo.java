package mdc.components.cards.actioncards;

import mdc.components.cards.CardPhase;
import mdc.components.cards.ICard;
import mdc.components.piles.DrawPile;
import mdc.components.players.Player;
import mdc.states.game.MDCGame;

/**
 * 摸两张
 *
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para value:发几张牌
 * @para isActing:判断当前行动卡是否在生效
 */
public class PassGo extends AbstractActionCard {

    public PassGo(int turnMoney) {
        super(turnMoney);
        this.needOwnPile = true;
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.ownPilePhase) {
                game.getDrawPile().deal(game.getCurrPlayer(), 2);
                isPhaseOver = true;
            }
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.needOwnPile = true;
    }
}
