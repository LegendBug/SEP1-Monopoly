package mdc.components.cards.actioncards;

import mdc.components.cards.CardColor;
import mdc.components.cards.CardPhase;
import mdc.components.cards.properties.PropertyCard;
import mdc.states.game.MDCGame;

import java.util.Stack;

/**
 * 指定一个玩家交换一个不成套的
 *
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 */
public class ForceDeal extends DealBreaker {
    protected CardColor ownColor;
    protected CardColor opponentColor;
    protected Stack<PropertyCard> ownProperty; // 出牌者提供交换的牌

    public ForceDeal(int turnMoney) {
        super(turnMoney);
        this.needOwnPropertyPile = true;
        this.needFullSet = false;
        this.ownProperty = new Stack<>();
    }

    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.ownPropertyPhase && game.getSelectButton().isIfActive()) {
                // 选择自己要交换的房产
                ownColor = game.getColors().get(game.getCurrPropertyIndex());
                if (game.getCurrPlayer().getOwnProperty().getProperty(ownColor).size() != 0) {
                    while (!game.getCurrPlayer().getOwnProperty().getProperty(ownColor).isEmpty())
                        ownProperty.add(game.getCurrPlayer().getOwnProperty().getProperty(ownColor).pop());
                    phase = CardPhase.chooseOpponentsPhase;
                } else {
                    isPhaseOver = true;
                    System.out.println("当前房产为空");
                }
                game.getSelectButton().resetButton();
            } else if (phase == CardPhase.otherPropertyPhase && game.getSelectButton().isIfActive()) {
                // 交换房产
                int cardIndex = game.getCurrPropertyIndex();
                opponentColor = game.getColors().get(cardIndex); // 获取对手选中的房产颜色
                game.getCurrPlayer().getOwnProperty().clear(opponentColor); // 清除玩家要加入卡牌颜色的房产
                tempOpponent.getOwnProperty().takeProperty(opponentColor, game.getCurrPlayer().getOwnProperty(), needFullSet); // 将对手房产加入房产

                tempOpponent.getOwnProperty().clear(ownColor);// 清除对手被加入的房产
                tempOpponent.getOwnProperty().addCards(ownProperty); // 加入玩家提供的房产
                game.getSelectButton().resetButton();
                isPhaseOver = true;
            }
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.needOwnPropertyPile = true;
        this.needFullSet = false;
        this.ownProperty = new Stack<>();
    }
}
