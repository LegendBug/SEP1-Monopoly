package mdc.components.cards.properties;

import mdc.components.cards.CardInterface;
import mdc.components.cards.actioncards.Hotel;
import mdc.components.cards.actioncards.House;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 单色土地
 * name:卡牌名
 * turnMoney:放银行是多少钱
 * value：几张相同能成一套
 * color：颜色
 * rent1-4：1-4张时分别能收多少租金
 */
public class Property implements PropertyInterface, CardInterface {
    private String name;
    private int turnMoney;
    private int value;
    private String color;
    private int rent1;
    private int rent2;
    private int rent3;
    private int fullsetRent;
    private List<Property> colors=new ArrayList<>();

    public Property(String color, int rent1, int rent2){
        this.value =2;
        this.color=color;
        this.rent1=rent1;
        this.fullsetRent=rent2;
    }

    public Property(String color, int rent1, int rent2, int rent3){
        this.value =3;
        this.color=color;
        this.rent1=rent1;
        this.rent2=rent2;
        this.fullsetRent=rent3;
    }

    public Property( String color, int rent1, int rent2, int rent3, int rent4){
        this.value =4;
        this.color=color;
        this.rent1=rent1;
        this.rent2=rent2;
        this.rent3=rent3;
        this.fullsetRent=rent4;
    }

    public void play(Player player,Property property){
        player.getOwnProperty(player).addProperty(property);
    }

    public void addHotel(Hotel hotel){
        if (hotel.isActing()){
            fullsetRent+=hotel.getValue();
        }
    }

    @Override
    public void addHouse(House house) {
        if (house.isActing()){
            fullsetRent+=house.getValue();
        }
    }

    public int getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }

    public int getTurnMoney() {
        return turnMoney;
    }

    public String getName() {
        return name;
    }

    public int getRent(int v){
        if (v==1){
            return rent1;
        }else if (v==2){
            return rent2;
        }else if (v==3){
            return rent3;
        }else if (v==4){
            return fullsetRent;
        }
        return 0;
    }

    @Override
    public void discard(DrawPile pile) {
        pile.addCards(this);
    }
}
