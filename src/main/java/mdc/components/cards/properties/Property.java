package mdc.components.cards.properties;

import mdc.components.cards.Colors;
import mdc.components.cards.actioncards.Hotel;
import mdc.components.cards.actioncards.House;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.players.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 单色土地
 * name:卡牌名
 * turnMoney:放银行是多少钱
 * value：几张相同能成一套
 * color：颜色
 * rent1-4：1-4张时分别能收多少租金
 */
public class Property extends AbstractPropertyCard {
    private int turnMoney;
    private int value;
    private Colors color;
    private int rent1;
    private int rent2;
    private int rent3;
    private int fullSetRent;
    private boolean isFullColor;
    private List<Colors> fullColors;

    public Property(int turnMoney, Colors color, int rent1, int rent2) {
        this.turnMoney = turnMoney;
        this.value = 2;
        this.color = color;
        this.rent1 = rent1;
        this.fullSetRent = rent2;
    }

    public Property(int turnMoney, Colors color, int rent1, int rent2, int rent3) {
        this.turnMoney = turnMoney;
        this.value = 3;
        this.color = color;
        this.rent1 = rent1;
        this.rent2 = rent2;
        this.fullSetRent = rent3;
    }

    public Property(int turnMoney, Colors color, int rent1, int rent2, int rent3, int rent4) {
        this.turnMoney = turnMoney;
        this.value = 4;
        this.color = color;
        this.rent1 = rent1;
        this.rent2 = rent2;
        this.rent3 = rent3;
        this.fullSetRent = rent4;
        this.isFullColor = false;
    }

    public Property() {
        this.isFullColor = true;
        fullColors = new ArrayList<>();
        fullColors.addAll(
                Arrays.stream(Colors.values())
                        .limit(10)
                        .collect(Collectors.toSet())
        );
    }

    @Override
    public void deal(DrawPile pile) {
        pile.addCard(this);
    }

    public void play(Player player, Property property) {
        player.getOwnProperty().addProperty(property);
    }

    public void addHotel(Hotel hotel) {
        if (hotel.isActing()) {
            fullSetRent += hotel.getValue();
        }
    }

    @Override
    public void addHouse(House house) {
        if (house.isActing()) {
            fullSetRent += house.getValue();
        }
    }

    public int getValue() {
        return value;
    }

    public Colors getColor() {
        return color;
    }

    @Override
    public int getTurnMoney() {
        return turnMoney;
    }

    public int getRent(int v) {
        if (v == 1) {
            return rent1;
        } else if (v == 2) {
            return rent2;
        } else if (v == 3) {
            return rent3;
        } else if (v == 4) {
            return fullSetRent;
        }
        return 0;
    }

    @Override
    public void discard(DrawPile pile) {
        pile.addCard(this);
    }

    public boolean isFullColor() {
        return isFullColor;
    }

    public List<Colors> getFullColors() {
        return fullColors;
    }
}
