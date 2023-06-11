package mdc.components.cards.actioncards;

import mdc.components.cards.ICard;
import mdc.components.piles.DrawPile;
import mdc.components.piles.ActionPile;
import mdc.states.game.MDCGame;

/**
 * 跟一个rent卡一起用，加倍
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 */

public class DoubleRent extends AbstractActionCard {

    public DoubleRent(int turnMoney){
        super(turnMoney);
        this.isPlayable = false;
    }

    @Override
    public void play(MDCGame game){
        if (!isPhaseOver) {
            super.play(game);
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.isPlayable = false;
    }
}
