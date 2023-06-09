package mdc.components.cards;

import mdc.components.buttons.Button;
import mdc.components.piles.DrawPile;
import mdc.states.game.MDCGame;

import java.util.ArrayList;

public abstract class AbstractCard implements ICard {
    protected int turnMoney; // 面值

    protected boolean isPayable; // 是否可作为钱
    protected boolean isMoney; // 是否为钱
    protected boolean isPhaseOver; // 卡牌行动是否结束
    protected boolean needOwnPropertyPile;
    protected boolean needChooseOpponent;
    protected boolean needOtherPile;
    protected boolean needOtherBank;
    protected boolean needOtherProperty;
    protected boolean needCardAttached; // 是否需要其他牌被选中
    protected boolean canCardAttach; // 是否可附带打出别的牌
    protected CardPhase phase;

    public AbstractCard() {
        this.isPayable = true;
        this.isMoney = false;
        this.isPhaseOver = false;
        this.needOwnPropertyPile = false;
        this.needChooseOpponent = false;
        this.needOtherPile = false;
        this.needOtherBank = false;
        this.needOtherProperty = false;
        this.needCardAttached = false;
        this.canCardAttach = false;
        this.phase = CardPhase.waitingPhase;
    }

    public CardPhase getPhase() {
        return phase;
    }

    public int getTurnMoney() {
        return turnMoney;
    }

    public boolean isPhaseOver() {
        return isPhaseOver;
    }

    public void play(MDCGame game) {
        ArrayList<Button> buttons = game.getButtons();
        Button saveButton = game.getSaveButton();
        if (phase == CardPhase.waitingPhase) { // 控制等待阶段
            if (isPayable) {
                if (!buttons.contains(saveButton)) buttons.add(saveButton); // 可当做卡牌加入存钱按键
                else if (saveButton.isIfActive()) {
                    isPhaseOver = true; // 点击了存钱按键，结束
                    saveButton.resetButton();
                }
            } else buttons.remove(saveButton);
        }
    }

    public boolean isPayable() {
        return isPayable;
    }

    @Override
    public void discard(DrawPile pile) {

    }

    @Override
    public void deal(DrawPile pile) {

    }

    public void resetCard() {
        this.isPayable = true;
        this.isMoney = true;
        this.isPhaseOver = false;
        this.needOwnPropertyPile = false;
        this.needChooseOpponent = false;
        this.needOtherPile = false;
        this.needOtherBank = false;
        this.needOtherProperty = false;
        this.needCardAttached = false;
        this.canCardAttach = false;
        this.phase = CardPhase.waitingPhase;
    }
}
