package mdc.components.cards.actioncards;

import mdc.components.cards.Colors;
import mdc.components.cards.ICard;
import mdc.components.piles.actionpile.ActionPile;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.players.Player;


/**
 * 收租,双色和全色，收多少，钱
 *
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 * @para value:收多少
 */

public class RentCard extends AbstractActionCard {
    private int turnMoney;
    private boolean isActing;
    private Colors color1, color2, finalColor;
    private boolean isFullColor;

    public RentCard(int turnMoney, Colors color1, Colors color2) {
        this.turnMoney = turnMoney;
        this.color1 = color1;
        this.color2 = color2;
        this.isActing = true;
        this.isFullColor = false;
    }

    //创建全色卡牌
    public RentCard(int turnMoney) {
        this.turnMoney = turnMoney;
        isActing = true;
        this.isFullColor = true;
    }

    @Override
    public void deal(DrawPile pile) {
        pile.addCard(this);
    }

    public void play(ActionPile pile, Player player, Player[] players) {
        if (isActing) {
            int rent = player.getOwnProperty().getRent(this.finalColor);
            for (Player p : players) {
                if (p != player) {
                    player.takeMoney(player, p, rent);
                }
            }
            pile.addCards(this);
        }
    }

    public void play(ActionPile pile, Player player, boolean full, Player payPlayer) {
        if (isActing) {
            int rent = player.getOwnProperty().getRent(this.finalColor);
            player.takeMoney(player, payPlayer, rent);
            pile.addCards(this);
        }
    }

    public void play(ActionPile pile, Player player, Player[] players, boolean doubleRent) {
        if (isActing) {
            int rent = player.getOwnProperty().getRent(this.finalColor) * 2;
            for (Player p : players) {
                if (p != player) {
                    player.takeMoney(player, p, rent);
                }
            }
            pile.addCards(this);
        }
    }

    public void play(ActionPile pile, Player player, boolean full, Player payPlayer, boolean doubleRent) {
        if (isActing) {
            int rent = player.getOwnProperty().getRent(this.finalColor) * 2;
            player.takeMoney(player, payPlayer, rent);
            pile.addCards(this);
        }
    }

    public void chooseColor(Colors c) {
        finalColor = c;
    }

    public int getTurnMoney() {
        return turnMoney;
    }

    @Override
    public boolean isActing() {
        return isActing;
    }

    @Override
    public void setActing(boolean act) {
        isActing = act;
    }

    @Override
    public void discard(DrawPile pile) {
        pile.addCard((ICard) this);
    }

    public Colors getFinalColor() {
        return finalColor;
    }

    public String getColor() {
        return color1 + " " + color2;
    }

    public boolean isFullColor() {
        return isFullColor;
    }
}

