package mdc.components.piles;

import mdc.components.cards.CardColor;
import mdc.components.cards.ICard;
import mdc.components.cards.properties.PropertyCard;

import java.util.*;
import java.util.List;

/**
 * Player property card pile
 */
public class OwnProperty extends AbstractPile {
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
    }

    public Map<CardColor, Stack<PropertyCard>> getProperties() {
        return properties;
    }

    public Stack<PropertyCard> getProperty(CardColor color) {
        return properties.get(color);
    }

    /**
     * Add a card to the deck
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
     * Remove a card from the top of the deck
     *
     * @param color
     */
    public void takeCard(CardColor color, AbstractPile targetPile) {
        Stack<PropertyCard> property = properties.get(color);
        if (property.size() > 0) targetPile.addCard(property.pop()); //Add to the deck as you remove cards
    }

    public boolean takeProperty(CardColor color, AbstractPile targetPile, boolean needFullSet) {
        Stack<PropertyCard> property = properties.get(color);
        if ((!needFullSet && property.size() < maxStackSizes.get(color)) || (needFullSet && property.size() == maxStackSizes.get(color))) {
            while (!property.isEmpty()) {
                targetPile.addCard(property.pop());
            }
            return true;
        } return false;
    }

    public void clear(CardColor color) {
        Stack<PropertyCard> property = properties.get(color);
        property.clear();
    }

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
            return 0;
        } else {
            return property.peek().getRent(size);
        }
    }

    public CardColor getHouseColor() {
        return houseColor;
    }

    public CardColor getHotelColor() {
        return hotelColor;
    }

    public Map<CardColor, Integer> getPropertySizes() {
        Map<CardColor, Integer> propertySizes = new HashMap<>();
        for (Map.Entry<CardColor, Stack<PropertyCard>> entry : properties.entrySet()) {
            CardColor color = entry.getKey();
            Stack<PropertyCard> stack = entry.getValue();
            propertySizes.put(color, stack.size());
        }
        return propertySizes;
    }

    /**
     * Additional rent
     * @param addValue
     * @param color
     */
    public String addRent(int addValue, CardColor color) {
        if (hasHotel) return "You already own the house and the hotel!";
        if (hasHouse && addValue == 2) return "You already own the house!";
        if (!hasHouse && addValue == 4) return "You don't own a house yet!";
        if (hasHouse && color == houseColor) return "You've made this your house!";
        Stack<PropertyCard> property = properties.get(color);
        if (property.size() != maxStackSizes.get(color)) return "The current property is not full!";
        PropertyCard topCard = property.peek();
        topCard.addRent(addValue);
        if (hasHouse) {
            hasHotel = true;
            hotelColor = color;
        } else {
            hasHouse = true;
            houseColor = color;
        }
        return null;
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
