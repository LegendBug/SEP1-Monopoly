package mdc.components.cards.properties;

import mdc.components.cards.AbstractCard;
import mdc.components.cards.CardPhase;
import mdc.components.cards.CardColor;
import mdc.states.game.MDCGame;

public abstract class AbstractPropertyCard extends AbstractCard {
    protected boolean isPlayable;
    protected boolean needOwnPropertyPile;
    protected CardColor color;

    public AbstractPropertyCard() {
        super();
        this.isPlayable = true;
        this.needOwnPropertyPile = false;
    }

    @Override
    public void play(MDCGame game) {
        super.play(game);
        // 等待阶段设置按钮
        if (phase == CardPhase.waitingPhase && !game.getButtons().contains(game.getPlayButton())) {
            game.getButtons().add(game.getPlayButton());
            if (game.getButtons().contains(game.getSaveButton())) {
                game.getButtons().set(2, game.getSaveButton()); // 将存钱按键放在cancel左一位
                game.getButtons().set(1, game.getPlayButton());
            }
            if (!isPlayable) {
                game.getButtons().remove(game.getPlayButton());
                game.getPlayButton().resetButton();
            }
        }
        // 检测play按键是否按下，首先进入选择自己房产阶段
        if (phase == CardPhase.waitingPhase && needOwnPropertyPile && game.getPlayButton().isIfActive()) {
            // 切换至ownProperty
            System.out.println("switch to ownProperty");
            phase = CardPhase.ownPropertyPhase;
            setCurrPropertyColor(game);
            game.getButtons().clear();
            game.getButtons().add(game.getSelectButton());
            game.getPlayButton().resetButton();
        }

    }

    protected void setCurrPropertyColor(MDCGame game) {
        if (color == null || color == CardColor.fullColor) {
            game.setCurrPropertyIndex(game.getColors().indexOf(CardColor.yellow)); // 设为黄色
        } else if (color.toString().split("_").length == 2) {
            CardColor currColor = CardColor.valueOf(color.toString().split("_")[0]);
            game.setCurrPropertyIndex(game.getColors().indexOf(currColor)); // 设为一号颜色
        } else if (color.toString().split("_").length == 1) {
            game.setCurrPropertyIndex(game.getColors().indexOf(color)); // 设为当前颜色
        }
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.isPlayable = true;
        this.needOwnPropertyPile = false;
    }
}