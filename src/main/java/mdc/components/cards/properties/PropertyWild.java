package mdc.components.cards.properties;

import mdc.components.cards.Colors;
import mdc.components.cards.actioncards.Hotel;
import mdc.components.cards.actioncards.House;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.players.Player;

/**
 * 哪几种颜色，分别是几套，租金，钱,有全色
 */
public class PropertyWild extends AbstractPropertyCard {
    private int turnMoney;
    private int finalValue;
    private Property card1, card2;
    private Property finalCard;
    private Colors color;
    private Colors finalColor;

    public PropertyWild(int turnMoney, Property card1, Property card2) {
        this.turnMoney = turnMoney;
        this.card1 = card1;
        this.card2 = card2;
        setColor();
    }

    @Override
    public void deal(DrawPile pile) {
        pile.addCard(this);
    }

    public void play(Player player, AbstractPropertyCard card) {
        player.getOwnProperty().addProperty(card);
    }

    public void setColor() {
        this.color = Colors.valueOf(card1.getColor().toString() + "_" + card2.getColor());
    }

    public void chooseColor(Colors c) {
        finalColor = c;
        if (card1.getColor() == c) {
            finalCard = card1;
            finalValue = card1.getValue();
        } else {
            finalCard = card2;
            finalValue = card2.getValue();
        }
    }

    public int getTurnMoney() {
        return turnMoney;
    }

    @Override
    public Colors getColor() {
        return color;
    }

    public Colors getFinalColor() {
        return finalColor;
    }

    @Override
    public int getValue() {
        return finalValue;
    }

    public int getRent(int v) {
        return finalCard.getRent(v);
    }

    @Override
    public void addHotel(Hotel hotel) {
        finalCard.addHotel(hotel);
    }

    @Override
    public void addHouse(House house) {
        finalCard.addHouse(house);
    }

    @Override
    public void discard(DrawPile pile) {
        pile.addCard(this);
    }
}
