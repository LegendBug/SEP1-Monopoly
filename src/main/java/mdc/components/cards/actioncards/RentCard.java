package mdc.components.cards.actioncards;

import mdc.components.cards.AbstractCard;
import mdc.components.cards.CardColor;
import mdc.components.cards.CardPhase;
import mdc.components.cards.ICard;
import mdc.components.cards.properties.PropertyCard;
import mdc.components.piles.ActionPile;
import mdc.components.piles.DrawPile;
import mdc.components.players.Player;
import mdc.states.game.MDCGame;

import java.util.ArrayList;


/**
 * 收租,双色和全色，收多少，钱
 *
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 * @para value:收多少
 */

public class RentCard extends Birthday {
    private boolean isFullColor;
    private boolean isDoubleColor;

    //创建全色卡牌
    public RentCard(int turnMoney) {
        super(turnMoney);
        this.needOwnPropertyPile = true;
        this.needChooseOpponent = true; // 针对一人
        this.color = CardColor.fullColor;
        this.isFullColor = true;
        this.isDoubleColor = false;
    }

    public RentCard(int turnMoney, CardColor color1, CardColor color2) {
        super(turnMoney);
        this.needOwnPropertyPile = true;
        this.color = CardColor.valueOf(color1.toString() + "_" + color2.toString());
        this.isFullColor = false;
        this.isDoubleColor = true;
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.ownPropertyPhase && game.getSelectButton().isIfActive()) {
                chooseColor(game);
                game.getSelectButton().resetButton();
            } else if (phase == CardPhase.ownPilePhase) {
                // 移除按键
                if (game.getButtons().contains(game.getSelectButton())) {
                    game.getButtons().clear();
                    game.getButtons().add(game.getPlayButton()); // 加入询问是否打出justSayNo的按键
                    game.getButtons().add(game.getCancelButton());
                }
                // 询问是否打出DoubleRent
                askForDoubleRent(game);
            }
        }
    }


    private void chooseColor(MDCGame game) {
        // 全色
        if (isFullColor) {
            isFullColor = false;
            phase = CardPhase.ownPilePhase; // 切换至ownPile询问是否加倍
            color = game.getColors().get(game.getCurrPropertyIndex()); // 将颜色确定
            game.setCurrOpponentIndex(game.getCurrOpponentIndex() + 1); // 当前对手index加一
            // 双色
        } else if (isDoubleColor){
            isDoubleColor = false;
            String[] colors = color.toString().split("_");
            if (CardColor.valueOf(colors[0]) == game.getColors().get(game.getCurrPropertyIndex())) {
                color = CardColor.valueOf(colors[0]);
                phase = CardPhase.ownPilePhase; // 切换至ownPile询问是否加倍
            } else if (CardColor.valueOf(colors[1]) == game.getColors().get(game.getCurrPropertyIndex())){
                color = CardColor.valueOf(colors[1]);
                phase = CardPhase.ownPilePhase; // 切换至ownPile询问是否加倍
            } else System.out.println("wrong color");
        }
    }

    private void askForDoubleRent(MDCGame game) {
        if (game.getPlayButton().isIfActive() || game.getCancelButton().isIfActive()) {
            int multiplier = game.getPlayButton().isIfActive() && checkForCard(game, "DoubleRent") ? 2 : 1;
            expectToPay = game.getCurrPropertyPile().getRent(color) * multiplier;
            remainToPay = expectToPay;
            phase = expectToPay == 0 ? CardPhase.ownPilePhase : CardPhase.chooseOpponentsPhase;
            isPhaseOver = expectToPay == 0;
            resetButtons(game);
        }
    }

    private void resetButtons(MDCGame game) {
        game.getButtons().clear();
        game.getButtons().add(game.getSelectButton());
        game.getCancelButton().resetButton();
        game.getPlayButton().resetButton();
    }

    @Override
    protected boolean checkForCard(MDCGame game, String cardName) {
        if (game.getPlayedCardNum() == 2) return false; // 已经打出两张牌无法加倍
        ArrayList<AbstractCard> playerCards = game.getCurrPlayerPile().getCards();
        for (AbstractCard card : playerCards) {
            if (card.getClass().getSimpleName().equals(cardName)) {
                int index = playerCards.indexOf(card);
                game.setPlayedCardNum(game.getPlayedCardNum() + 1);
                game.getCurrPlayerPile().removeCard(index); // 打出第一张JustSayNo，移除
                if (index < game.getCurrPlayerCardIndex())
                    game.setCurrPlayerCardIndex(game.getCurrPlayerCardIndex() - 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if (isFullColor) return "null_null"; // 全色牌
        else return color.toString();
    }
}

