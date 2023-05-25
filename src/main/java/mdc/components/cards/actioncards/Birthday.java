package mdc.components.cards.actioncards;

import mdc.components.cards.ICard;
import mdc.components.piles.actionpile.ActionPile;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.players.Player;

/**
 * 所有人给两块
 *
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 * @para takeMoney:规定卡牌打出后每人给多少钱
 */

public class Birthday extends AbstractActionCard {
    private int turnMoney;
    private int takeMoney;
    private boolean isActing;

    public Birthday(int turnMoney) {
        this.turnMoney = turnMoney;
        this.takeMoney = takeMoney;
        isActing = true;
    }

    public void play(ActionPile pile, Player player, Player[] players) {
        if (isActing) {
            for (Player p : players) {
                if (p != player) {
                    p.takeMoney(player, p, takeMoney);
                }
            }
            pile.addCards(this);
        }
    }

    @Override
    public int getTurnMoney() {
        return turnMoney;
    }

    @Override
    public boolean isActing() {
        return isActing;
    }

    @Override
    public void setActing(boolean act) {
        isActing = act;
    }

    @Override
    public void discard(DrawPile pile) {
        pile.addCard((ICard) this);
    }

    @Override
    public void deal(DrawPile pile) {
        pile.addCard(this);
    }
}
