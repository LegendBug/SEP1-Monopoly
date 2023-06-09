package mdc.components.cards.actioncards;

import mdc.components.cards.CardPhase;
import mdc.components.cards.properties.AbstractPropertyCard;
import mdc.components.players.Player;
import mdc.states.game.MDCGame;

import java.util.LinkedList;

public abstract class AbstractActionCard extends AbstractPropertyCard {
    protected int initialPlayer;
    LinkedList<Player> opponents = new LinkedList<>();

    @Override
    public void play(MDCGame game) {
        super.play(game);
        if (phase == CardPhase.chooseOpponentsPhase) {
            chooseOpponents(game);
        }
    }

    protected void chooseOpponents(MDCGame game) {
        if (!needChooseOpponent) {
            // 全选对手,直接进入下一阶段
            for (int i = 1; i < game.getPlayers().length; i++) {
                opponents.addLast(game.getPlayers()[(i + game.currPlayerIndex) % game.getPlayers().length]);
            }
            System.out.println(opponents.size());
            if (needOtherBank) phase = CardPhase.otherBankPhase;
            else if (needOwnPropertyPile) phase = CardPhase.otherPropertyPhase;
            else System.out.println("no next phase????????");
        } else {
            // 选择对手
            System.out.println(phase);
            if (game.getSelectButton().isIfActive()) {
                // 计算当前对手在玩家数组中的index
                System.out.println("chosen");
                int realOpponentIndex = (game.getCurrOpponentIndex() + game.currPlayerIndex + 1) / game.getPlayerNum();
                opponents.addLast(game.getPlayers()[realOpponentIndex]);
                if (needOtherBank) phase = CardPhase.otherBankPhase;
                else if (needOwnPropertyPile) phase = CardPhase.otherPropertyPhase;
                game.getSelectButton().resetButton();
            }
        }
    }
}
