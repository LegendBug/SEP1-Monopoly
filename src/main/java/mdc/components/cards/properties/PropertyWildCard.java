package mdc.components.cards.properties;

import mdc.components.cards.CardPhase;
import mdc.components.cards.CardColor;
import mdc.states.game.MDCGame;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 哪几种颜色，分别是几套，租金，钱,有全色
 */
public class PropertyWildCard extends PropertyCard {
    private PropertyCard card1, card2;
    private ArrayList<CardColor> colors = new ArrayList<>();

    private boolean isFullColor;

    // 全色牌构造器
    public PropertyWildCard() {
        super();
        this.isPayable = false;
        this.isFullColor = true;
        this.color = CardColor.fullColor;
        this.colors = new ArrayList<>(Arrays.stream(CardColor.values())
                .limit(10).distinct().toList());
    }

    // 双色牌构造器
    public PropertyWildCard(int turnMoney, PropertyCard card1, PropertyCard card2) {
        super();
        this.isFullColor = false;
        this.turnMoney = turnMoney;
        this.card1 = card1;
        this.card2 = card2;
        this.color = CardColor.valueOf(card1.getColor().toString() + "_" + card2.getColor());
        this.colors.add(card1.getColor());
        this.colors.add(card2.getColor());
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.ownPropertyPhase) {
                if (game.getSelectButton().isIfActive()) {
                    // 全色
                    if (isFullColor) {
                        color = game.getColors().get(game.getCurrPropertyIndex()); // 将颜色确定
                        System.out.println("not");
                        isPhaseOver = true;
                        // 双色
                    } else  {
                        if (colors.get(0) == game.getColors().get(game.getCurrPropertyIndex())) {
                            color = card1.color;
                            isPhaseOver = true;
                        } else if (colors.get(1) == game.getColors().get(game.getCurrPropertyIndex())) {
                            color = card2.color;
                            isPhaseOver = true;
                        } else {
                            System.out.println("wrong color:" + color + " " + card1.color + " " + card2.color);
                        }
                    }
                }
                game.getSelectButton().resetButton();
            }
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        if (isFullColor) {
            isPayable = false;
            isFullColor = false;
        }
    }

    @Override
    public String toString() {
        if (isFullColor) return "null"; // 全色牌
        return card1.toString() + "_" + card2.toString();
    }
}
