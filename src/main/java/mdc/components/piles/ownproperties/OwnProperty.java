package mdc.components.piles.ownproperties;

import mdc.components.cards.Colors;
import mdc.components.cards.actioncards.Hotel;
import mdc.components.cards.actioncards.House;
import mdc.components.cards.properties.AbstractPropertyCard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人土地
 */
public class OwnProperty {
    private int asMoney;
    private List<AbstractPropertyCard> properties;
    private Map<Colors, List<AbstractPropertyCard>> property;

    public OwnProperty() {
        property = new HashMap<>();
        asMoney = 0;
    }

    public void clear() {
        properties.clear();
        asMoney = 0;
    }

    //临时方法，让用户用土地陪，涉及到listener到时候再改，卡的数值总合大于等于value，删除选择的卡,返回陪卡的列表
    public List<AbstractPropertyCard> choosePayCard(int value) {
        List<AbstractPropertyCard> cards = new ArrayList<>();
        return cards;
    }


    public int getAsMoney() {
        return asMoney;
    }

    public void addMoney(int m) {
        this.asMoney += m;
    }

    public void takeCards(AbstractPropertyCard property) {
        properties.remove(property);
        asMoney -= property.getTurnMoney();
    }

    public Map<Colors, List<AbstractPropertyCard>> getProperty() {
        return property;
    }

    public void addProperty(AbstractPropertyCard p) {
        if (property.containsKey(p.getColor())) {
            if (property.get(p.getColor()).size() < p.getValue() - 1) {
                property.get(p.getColor()).add(p);
            }
        } else {
            property.put(p.getColor(), new ArrayList<>());
            property.get(p.getColor()).add(p);
        }
        asMoney += p.getTurnMoney();
    }

    //fullSet：判断是不是偷一整套，是则ture
    public List<AbstractPropertyCard> takeProperty(AbstractPropertyCard p, boolean fullSet) {
        if (fullSet) {
            List<AbstractPropertyCard> ca = property.get(p.getColor());
            property.remove(p.getColor());
            return ca;
        } else {
            List<AbstractPropertyCard> ca = new ArrayList<>();
            ca.add(p);
            property.get(p.getColor()).remove(property.get(p.getColor()).size() - 1);
            return ca;
        }
    }

    public boolean ifFullSet(AbstractPropertyCard card) {
        return property.get(card.getColor()).size() == card.getValue();
    }

    public int getSize(Colors color) {
        return property.get(color).size();
    }

    public int getRent(Colors color) {
        int v = getSize(color);
        return property.get(color).get(0).getRent(v);
    }

    public void addHotel(Hotel hotel, Colors color) {
        if (property.containsKey(color)) {
            List<AbstractPropertyCard> p = property.get(color);
            for (AbstractPropertyCard pr : p) {
                pr.addHotel(hotel);
            }
        }
    }

    public void addHouse(House house, Colors color) {
        if (property.containsKey(color)) {
            List<AbstractPropertyCard> p = property.get(color);
            for (AbstractPropertyCard pr : p) {
                pr.addHouse(house);
            }
        }
    }
}
