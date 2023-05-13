package mdc.components.cards.properties;

import mdc.components.cards.CardInterface;
import mdc.components.cards.actioncards.Hotel;
import mdc.components.cards.actioncards.House;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.players.Player;

/**
 * 哪几种颜色，分别是几套，租金，钱,有全色
 */
public class PropeityWildCard implements PropertyInterface, CardInterface {
    private String name;
    private int turnMoney;
    private int finalValue;
    private Property card1,card2;
    private Property finalCard;
    private String finalColor;

    public PropeityWildCard(String name, int turnMoney, Property card1, Property card2) {
        this.name = name;
        this.turnMoney = turnMoney;
        this.card1=card1;
        this.card2=card2;
    }

    public void play(Player player,PropeityWildCard card){
        player.getOwnProperty(player).addProperty(card);
    }

    public void chooseColor(String c){
        finalColor=c;
        if (card1.getColor()==c){
            finalCard=card1;
            finalValue= card1.getValue();
        }else {
            finalCard=card2;
            finalValue=card2.getValue();
        }
    }


    public String getName() {
        return name;
    }

    public int getTurnMoney() {
        return turnMoney;
    }

    @Override
    public String getColor() {
        return finalColor;
    }


    @Override
    public int getValue() {
        return finalValue;
    }

    public int getRent(int v){
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
        pile.addCards(this);
    }
}
