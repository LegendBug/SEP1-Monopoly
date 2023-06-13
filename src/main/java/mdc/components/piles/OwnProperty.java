package mdc.components.piles;

import mdc.components.cards.CardColor;
import mdc.components.cards.CardPhase;
import mdc.components.cards.ICard;
import mdc.components.cards.properties.PropertyCard;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * 个人土地
 */
public class OwnProperty extends AbstractPile {
    private int asMoney;
    private Stack<PropertyCard> brownProperty = new Stack<>();
    private Stack<PropertyCard> darkBlueProperty = new Stack<>();
    private Stack<PropertyCard> greenProperty = new Stack<>();
    private Stack<PropertyCard> lightBlueProperty = new Stack<>();
    private Stack<PropertyCard> orangeProperty = new Stack<>();
    private Stack<PropertyCard> pinkProperty = new Stack<>();
    private Stack<PropertyCard> railRoadProperty = new Stack<>();
    private Stack<PropertyCard> redProperty = new Stack<>();
    private Stack<PropertyCard> utilityProperty = new Stack<>();
    private Stack<PropertyCard> yellowProperty = new Stack<>();

    private Map<CardColor, Stack<PropertyCard>> properties;
    private Map<CardColor, Integer> maxStackSizes;

    private boolean hasHouse;
    private boolean hasHotel;

    private CardColor houseColor;
    private CardColor hotelColor;

    public OwnProperty() {
        properties = new HashMap<>();
        properties.put(CardColor.brown, brownProperty);
        properties.put(CardColor.darkBlue, darkBlueProperty);
        properties.put(CardColor.green, greenProperty);
        properties.put(CardColor.lightBlue, lightBlueProperty);
        properties.put(CardColor.orange, orangeProperty);
        properties.put(CardColor.pink, pinkProperty);
        properties.put(CardColor.railRoad, railRoadProperty);
        properties.put(CardColor.red, redProperty);
        properties.put(CardColor.utility, utilityProperty);
        properties.put(CardColor.yellow, yellowProperty);

        maxStackSizes = new HashMap<>();
        maxStackSizes.put(CardColor.brown, 2);
        maxStackSizes.put(CardColor.darkBlue, 2);
        maxStackSizes.put(CardColor.green, 3);
        maxStackSizes.put(CardColor.lightBlue, 3);
        maxStackSizes.put(CardColor.orange, 3);
        maxStackSizes.put(CardColor.pink, 3);
        maxStackSizes.put(CardColor.railRoad, 4);
        maxStackSizes.put(CardColor.red, 3);
        maxStackSizes.put(CardColor.utility, 2);
        maxStackSizes.put(CardColor.yellow, 3);

        hasHouse = false;
        hasHotel = false;

        asMoney = 0;
    }

    public Map<CardColor, Stack<PropertyCard>> getProperties() {
        return properties;
    }

    public Stack<PropertyCard> getProperty(CardColor color) {
        return properties.get(color);
    }

    /**
     * 向牌堆添加一张牌
     * @param newCard
     */
    @Override
    public void addCard(ICard newCard) {
        PropertyCard card = (PropertyCard) newCard;
        Stack<PropertyCard> property = properties.get(card.getColor());
        Integer maxStackSize = maxStackSizes.get(card.getColor());
        if (property.size() < maxStackSize) property.push(card);
        else {
            System.out.println("The stack for color " + card.getColor() + " is full.");
        }
    }

    public void addCards(Stack<PropertyCard> cards) {
        while (!cards.empty()) {
            addCard(cards.pop());
        }
    }

    /**
     * 从牌堆顶取走一张牌
     *
     * @param color
     */
    public void takeCard(CardColor color, AbstractPile targetPile) {
        Stack<PropertyCard> property = properties.get(color);
        if (property.size() > 0) targetPile.addCard(property.pop()); //移出卡牌的同时加入牌堆
        else System.out.println("没房子！");
    }

    public void takeProperty(CardColor color, AbstractPile targetPile, boolean needFullSet) {
        Stack<PropertyCard> property = properties.get(color);
        if ((!needFullSet && property.size() < maxStackSizes.get(color)) || (needFullSet && property.size() == maxStackSizes.get(color))) {
            while (!property.isEmpty()) {
                targetPile.addCard(property.pop());
            }
        } else {
            System.out.println("没满呢");
        }
    }

    public void clear(CardColor color) {
        Stack<PropertyCard> property = properties.get(color);
        property.clear();
    }

    //临时方法，让用户用土地陪，涉及到listener到时候再改，卡的数值总合大于等于value，删除选择的卡,返回陪卡的列表
    public List<PropertyCard> choosePayCard(int value) {
        List<PropertyCard> cards = new ArrayList<>();
        return cards;
    }

    public int getSize(CardColor color) {
        return properties.get(color).size();
    }

    public int getRent(CardColor color) {
        Stack<PropertyCard> property = properties.get(color);
        int size = property.size();
        if (size == 0) {
            System.out.println("空房子收不了租");
            return 0;
        } else {
            return property.peek().getRent(size);
        }
    }

    public boolean isHasHouse() {
        return hasHouse;
    }

    public boolean isHasHotel() {
        return hasHotel;
    }

    public CardColor getHouseColor() {
        return houseColor;
    }

    public CardColor getHotelColor() {
        return hotelColor;
    }

    /**
     * 加收租金
     * @param addValue
     * @param color
     */
    public void addRent(int addValue, CardColor color) {
        if (hasHouse && !hasHotel) {
            if (addValue == 2) System.out.println("已经有房子了别加了");
            else {
                Stack<PropertyCard> property = properties.get(color);
                if (property.size() == maxStackSizes.get(color) && color != houseColor) {
                    PropertyCard topCard = property.peek();
                    topCard.addRent(addValue);
                    hasHotel = true;
                    hotelColor = color;
                } else System.out.println("还没满呢，或者这已经是house了");
            }
        } else if (hasHotel){
            System.out.println("啥都有了别加了");
        } else {
            if (addValue == 4) System.out.println("还没有有房子不能加");
            else {
                Stack<PropertyCard> property = properties.get(color);
                if (property.size() == maxStackSizes.get(color)) {
                    PropertyCard topCard = property.peek();
                    topCard.addRent(addValue);
                    hasHouse = true;
                    houseColor = color;
                } else System.out.println("还没满呢");
            }
        }
    }

    public boolean achievedVictory() {
        int fullSets = 0;
        for (Map.Entry<CardColor, Stack<PropertyCard>> entry : properties.entrySet()) {
            CardColor color = entry.getKey();
            Stack<PropertyCard> stack = entry.getValue();
            if (stack.size() == maxStackSizes.get(color)) fullSets++;
            if (fullSets == 3) return true;
        }
        return false;
    }

}
