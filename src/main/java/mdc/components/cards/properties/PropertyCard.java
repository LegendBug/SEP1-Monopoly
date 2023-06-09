package mdc.components.cards.properties;

import mdc.components.cards.CardColor;
import mdc.components.cards.CardPhase;
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
    protected int value;
    protected ArrayList<Integer> rents = new ArrayList<>();

    public PropertyCard() {
        super();
        this.needOwnPropertyPile = true;
    }

    // 二卡
    public PropertyCard(int turnMoney, CardColor color, int rent1, int rent2) {
        this();
        this.turnMoney = turnMoney;
        this.value = 2;
        this.color = color;
        this.rents.add(rent1);
        this.rents.add(rent2);
    }

    // 三卡
    public PropertyCard(int turnMoney, CardColor color, int rent1, int rent2, int rent3) {
        this();
        this.turnMoney = turnMoney;
        this.value = 3;
        this.color = color;
        this.rents.add(rent1);
        this.rents.add(rent2);
        this.rents.add(rent3);
    }

    // 四卡
    public PropertyCard(int turnMoney, CardColor color, int rent1, int rent2, int rent3, int rent4) {
        this();
        this.turnMoney = turnMoney;
        this.value = 4;
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

    public void addHotel(Hotel hotel) {

    }

    public void addHouse(House house) {

    }

    public int getValue() {
        return value;
    }

    public CardColor getColor() {
        return color;
    }

    public int getRent(int v) {
//        switch ()
//        if (v == 1) {
//            return rent1;
//        } else if (v == 2) {
//            return rent2;
//        } else if (v == 3) {
//            return rent3;
//        } else if (v == 4) {
//            return fullSetRent;
//        }
        return 0;
    }

    @Override
    public void discard(DrawPile pile) {
        pile.addCard(this);
    }

    @Override
    public void resetCard() {
        super.resetCard();
    }

    @Override
    public String toString() {
        return color.toString();
    }
}
