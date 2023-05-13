package mdc.components.piles.ownproperties;

import mdc.components.cards.actioncards.Hotel;
import mdc.components.cards.actioncards.House;
import mdc.components.cards.properties.PropertyInterface;
import mdc.components.players.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 个人土地
 */
public class OwnProperty {
    private int asMoney;
    private Player player;
    private List<PropertyInterface> properties;
    private Map<String,List<PropertyInterface>> property;

    public OwnProperty(Player player){
        this.player=player;
        property=new HashMap<>();
        asMoney=0;
    }

    public void clear(){
        properties.clear();
        asMoney=0;
    }

    //临时方法，让用户用土地陪，涉及到listenner到时候再改，卡的数值总合大于等于value，删除选择的卡,返回陪卡的列表
    public List<PropertyInterface> choosePayCard(int value){
        List<PropertyInterface> cards=new ArrayList<>();
        return cards;
    }


    public int getAsMoney() {
        return asMoney;
    }

    public void addMoney(int m) {
        this.asMoney += m;
    }


    public Player getPlayer() {
        return player;
    }


    public void takeCards(PropertyInterface property){
        properties.remove(property);
        asMoney-= property.getTurnMoney();
    }

    public Map<String,List<PropertyInterface>> getProperty() {
        return property;
    }

    public void addProperty(PropertyInterface p){
        if (property.containsKey(p.getColor())){
            if (property.get(p.getColor()).size()<p.getValue()-1){
                property.get(p.getColor()).add(p);
            }
        }else {
            property.put(p.getColor(),new ArrayList<>());
            property.get(p.getColor()).add(p);
        }
        asMoney+=p.getTurnMoney();
    }
    //fullset：判断是不是偷一整套，是则ture
    public List<PropertyInterface> takeProperty(PropertyInterface p,boolean fullSet){
        if (fullSet){
            List<PropertyInterface> ca=property.get(p.getColor());
            property.remove(p.getColor());
            return ca;
        }else {
            List<PropertyInterface> ca=new ArrayList<>();
            ca.add(p);
            property.get(p.getColor()).remove(property.get(p.getColor()).size()-1);
            return ca;
        }
    }

    public boolean ifFullSet(PropertyInterface card){
        return property.get(card.getColor()).size()==card.getValue();
    }

    public int getSize(String color){
        return property.get(color).size();
    }

    public int getRent(String color){
        int v=getSize(color);
        return property.get(color).get(0).getRent(v);
    }

    public void addHotel(Hotel hotel,String color){
        if (property.containsKey(color)){
            List<PropertyInterface> p=property.get(color);
            for (PropertyInterface pr:p){
                pr.addHotel(hotel);
            }
        }
    }

    public void addHouse(House house,String color){
        if (property.containsKey(color)){
            List<PropertyInterface> p=property.get(color);
            for (PropertyInterface pr:p){
                pr.addHouse(house);
            }
        }
    }
}
