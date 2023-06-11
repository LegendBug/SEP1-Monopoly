package mdc.components.cards.properties;

import mdc.components.cards.CardColor;
import mdc.components.cards.CardPhase;
import mdc.states.game.MDCGame;

import java.util.ArrayList;

/**
 * 哪几种颜色，分别是几套，租金，钱,有全色
 */
public class PropertyWildCard extends PropertyCard {
    private ArrayList<PropertyCard> cards = new ArrayList<>();
    private boolean isDoubleColor;
    private boolean isFullColor;

    // 全色牌构造器
    public PropertyWildCard(ArrayList<PropertyCard> cards) {
        super();
        this.isPayable = false;
        this.isDoubleColor = false;
        this.isFullColor = true;
        this.color = CardColor.fullColor;
        this.cards.addAll(cards);
        for (PropertyCard card : cards) {
            System.out.println(card.rents.size());
        }
    }

    // 双色牌构造器
    public PropertyWildCard(int turnMoney, PropertyCard card1, PropertyCard card2) {
        super();
        this.isDoubleColor = true;
        this.isFullColor = false;
        this.turnMoney = turnMoney;
        this.cards.add(card1);
        this.cards.add(card2);
        this.color = CardColor.valueOf(card1.getColor().toString() + "_" + card2.getColor());
        for (PropertyCard card : cards) {
            System.out.println(card.rents.size());
        }
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.ownPropertyPhase && game.getSelectButton().isIfActive()) {
                // 全色
                if (isFullColor) {
                    color = game.getColors().get(game.getCurrPropertyIndex()); // 将颜色确定
                    for (PropertyCard card : cards) {
                        System.out.println("card" + card.getRents().size());
                        if (card.getColor() == color) rents.addAll(card.getRents()); // 将房产确定为对应颜色
                        System.out.println(rents.size());
                        break;
                    }
                    isPhaseOver = true;
                    // 双色
                } else {
                    if (cards.get(0).getColor() == game.getColors().get(game.getCurrPropertyIndex())) {
                        color = cards.get(0).color;
                        rents.addAll(cards.get(0).getRents());
                        isPhaseOver = true;
                    } else if (cards.get(1).getColor() == game.getColors().get(game.getCurrPropertyIndex())) {
                        color = cards.get(1).color;
                        rents.addAll(cards.get(1).getRents());
                        isPhaseOver = true;
                    } else System.out.println("wrong color");
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
        } else if (isDoubleColor) {
            isDoubleColor = false;
        }
    }

    @Override
    public String toString() {
        if (isFullColor) return "null"; // 全色牌
        else if (isDoubleColor) return cards.get(0).getColor().toString() + "_" + cards.get(1).getColor().toString();
        else return color.toString();
    }
}
