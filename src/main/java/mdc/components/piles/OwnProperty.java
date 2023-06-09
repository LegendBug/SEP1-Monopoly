package mdc.components.piles;

import mdc.components.cards.CardColor;
import mdc.components.cards.ICard;
import mdc.components.cards.actioncards.Hotel;
import mdc.components.cards.actioncards.House;
import mdc.components.cards.properties.PropertyCard;

import java.util.*;

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
        asMoney = 0;
    }

    public Map<CardColor, Stack<PropertyCard>> getProperties() {
        return properties;
    }

    /**
     * 向牌堆添加一张牌
     * @param card
     */
    @Override
    public void addCard(ICard newCard) {
        PropertyCard card = (PropertyCard) newCard;
        System.out.println(card.getColor());
        Stack<PropertyCard> property = properties.get(card.getColor());
        property.add(card);
    }

    /**
     * 从牌堆顶取走一张牌
     * @param color
     * @return 取走的牌
     */
    public PropertyCard takeCard(CardColor color) {
        Stack<PropertyCard> property = properties.get(color);
        return property.pop();
    }


    @Override
    public void clear() {
        properties.clear();
        asMoney = 0;
    }

    //临时方法，让用户用土地陪，涉及到listener到时候再改，卡的数值总合大于等于value，删除选择的卡,返回陪卡的列表
    public List<PropertyCard> choosePayCard(int value) {
        List<PropertyCard> cards = new ArrayList<>();
        return cards;
    }

    //fullSet：判断是不是偷一整套，是则ture
    public List<PropertyCard> takeProperty(PropertyCard p, boolean fullSet) {
        if (fullSet) {
            List<PropertyCard> ca = properties.get(p.getColor());
            properties.remove(p.getColor());
            return ca;
        } else {
            List<PropertyCard> ca = new ArrayList<>();
            ca.add(p);
            properties.get(p.getColor()).remove(properties.get(p.getColor()).size() - 1);
            return ca;
        }
    }

    public boolean ifFullSet(PropertyCard card) {
        return properties.get(card.getColor()).size() == card.getValue();
    }

    public int getSize(CardColor color) {
        return properties.get(color).size();
    }

    public int getRent(CardColor color) {
        int v = getSize(color);
        return properties.get(color).get(0).getRent(v);
    }

    public void addHotel(Hotel hotel, CardColor color) {
        if (properties.containsKey(color)) {
            List<PropertyCard> p = properties.get(color);
            for (PropertyCard pr : p) {
                pr.addHotel(hotel);
            }
        }
    }

    public void addHouse(House house, CardColor color) {
        if (properties.containsKey(color)) {
            List<PropertyCard> p = properties.get(color);
            for (PropertyCard pr : p) {
                pr.addHouse(house);
            }
        }
    }
}
