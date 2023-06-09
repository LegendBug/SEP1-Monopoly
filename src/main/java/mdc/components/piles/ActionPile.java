
package mdc.components.piles;

import mdc.components.cards.actioncards.AbstractActionCard;

import java.util.ArrayList;
import java.util.List;
/**
 * 每回合放行动牌的地方
 */
public class ActionPile {
    private List<AbstractActionCard> cards=new ArrayList<>();

    public List<AbstractActionCard> getCards() {
        return cards;
    }

    public void addCards(AbstractActionCard card){
        cards.add(card);
    }

    public void DrawCards(DrawPile pile){
        for (AbstractActionCard card:cards){
//            card.setActing(true);
            card.discard(pile);
            cards.clear();
        }
    }

    public void clearCards(){
        cards.clear();
    }
}
