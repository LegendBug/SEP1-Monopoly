
package mdc.components.piles.playerpiles;

import mdc.components.cards.actioncards.ActionInterface;
import mdc.components.piles.drawpile.DrawPile;

import java.util.ArrayList;
import java.util.List;
/**
 * 每回合放行动牌的地方
 */
public class ActionPile {
    private List<ActionInterface> cards=new ArrayList<>();

    public List<ActionInterface> getCards() {
        return cards;
    }

    public void addCards(ActionInterface card){
        cards.add(card);
    }

    public void DrawCards(DrawPile pile){
        for (ActionInterface card:cards){
            card.setActing(true);
            card.discard(pile);
            cards.clear();
        }
    }

    public void clearCards(){
        cards.clear();
    }
}
