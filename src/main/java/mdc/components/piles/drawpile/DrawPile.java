package mdc.components.piles.drawpile;

import mdc.components.cards.CardInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * 弃牌堆，依托答辩
 */
public class DrawPile {
    private List<CardInterface> cards=new ArrayList<>();

    public void shuffle(){//洗牌
        for (int k=0;k<cards.size();k++){
            int a=(int)(Math.random()*cards.size());
            if (!cards.get(k).equals(cards.get(a))){
                CardInterface c=cards.get(k);
                cards.set(k,cards.get(a));
                cards.set(a,c);
            }
        }
    }

    public CardInterface takeCards(){
        return cards.remove(cards.size()-1);
    }

    public List<CardInterface> multipleTake(int value){
        for (int k=0;k<value;k++){
            List<CardInterface> cards=new ArrayList<>();
            cards.add(cards.remove(cards.size()-1));
        }
        return cards;
    }

    public void clearAll(){
        cards.clear();
    }

    public void addCards(CardInterface card){
        cards.add(card);
    }
}
