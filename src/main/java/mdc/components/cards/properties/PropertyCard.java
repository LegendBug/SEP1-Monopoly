package mdc.components.cards.properties;

import mdc.components.cards.CardPhase;
import mdc.components.cards.CardColor;
import mdc.components.cards.actioncards.Hotel;
import mdc.components.cards.actioncards.House;
import mdc.components.piles.DrawPile;
import mdc.states.game.MDCGame;

import java.util.ArrayList;

/**
 * 单色土地
 * name:卡牌名
 * turnMoney:放银行是多少钱
 * value：几张相同能成一套
 * color：颜色
 * rent1-4：1-4张时分别能收多少租金
 */
public class PropertyCard extends AbstractPropertyCard {
    protected ArrayList<Integer> rents = new ArrayList<>();

    public PropertyCard() {
        super();
        this.needOwnPropertyPile = true;
    }

    // 二卡
    public PropertyCard(int turnMoney, CardColor color, int rent1, int rent2) {
        this();
        this.turnMoney = turnMoney;
        this.color = color;
        this.rents.add(rent1);
        this.rents.add(rent2);
    }

    // 三卡
    public PropertyCard(int turnMoney, CardColor color, int rent1, int rent2, int rent3) {
        this();
        this.turnMoney = turnMoney;
        this.color = color;
        this.rents.add(rent1);
        this.rents.add(rent2);
        this.rents.add(rent3);
    }

    // 四卡
    public PropertyCard(int turnMoney, CardColor color, int rent1, int rent2, int rent3, int rent4) {
        this();
        this.turnMoney = turnMoney;
        this.color = color;
        this.rents.add(rent1);
        this.rents.add(rent2);
        this.rents.add(rent3);
        this.rents.add(rent4);
    }

    @Override
    public void deal(DrawPile pile) {
        pile.addCard(this);
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.ownPropertyPhase) {
                // 当前为单色牌，直接结束
                if (game.getColors().get(game.getCurrPropertyIndex()) == color) {
                    isPhaseOver = true;
                }
            }
        }
    }

    public void addRent(int addRent) {
        int maxRent = rents.get(rents.size() - 1) + addRent;
        rents.set(rents.size() - 1, maxRent);
    }

    public CardColor getColor() {
        return color;
    }

    public int getRent(int size) {
        switch (size) {
            case 1 -> {
                return rents.get(0);
            }
            case 2 -> {
                return rents.get(1);
            }
            case 3 -> {
                return rents.get(2);
            }
            case 4 -> {
                return rents.get(3);
            }
        }
        return 0;
    }

    public ArrayList<Integer> getRents() {
        return rents;
    }

    @Override
    public void discard(DrawPile pile) {
        pile.addCard(this);
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.needOwnPropertyPile = true;
    }

    @Override
    public String toString() {
        return color.toString();
    }
}