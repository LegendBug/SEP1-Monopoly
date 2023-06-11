package mdc.components.cards.actioncards;

import mdc.components.cards.AbstractCard;
import mdc.components.cards.CardColor;
import mdc.components.cards.CardPhase;
import mdc.components.players.Player;
import mdc.states.game.MDCGame;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @para name:名字
 * @para turnMoney:放入银行多少钱
 * @para isActing:判断当前行动卡是否在生效
 */
public class JustSayNo extends AbstractActionCard {
    protected boolean needOtherPile; // 需要对方打出无懈可击
    protected boolean needOtherBank;
    protected boolean needOtherProperty;
    protected boolean needChooseOpponent; // 是否需要选择对手(否为全选对手，不是不选)
    protected boolean hasSayNo;
    protected boolean hasMoveToNext;
    protected boolean isOpponentTurn; // 是否由对手进行操作
    protected Player tempOpponent; // 当前进行操作的对手
    protected LinkedList<Player> opponents;

    public JustSayNo(int turnMoney) {
        super(turnMoney);
        this.isPlayable = false; // 删除play键
        this.needOtherPile = true;
        this.needOtherBank = false;
        this.needOtherProperty = false;
        this.needChooseOpponent = false;
        this.hasSayNo = false;
        this.hasMoveToNext = false;
        this.isOpponentTurn = false;
        this.tempOpponent = null;
        this.opponents = new LinkedList<>();
    }

    @Override
    public void play(MDCGame game) {
        if (!isPhaseOver) {
            super.play(game);
            if (phase == CardPhase.chooseOpponentsPhase) {
                // 进入选对手阶段
                chooseOpponents(game);
            } else if (phase == CardPhase.otherPilePhase) {
                // 选完了，进入询问无懈可击阶段
                askForSayNo(game);
            }
        }
    }

    protected void chooseOpponents(MDCGame game) {
        if (!needChooseOpponent) {
            // 全选对手,直接进入下一阶段
            for (int i = 1; i < game.getPlayers().length; i++) {
                opponents.addLast(game.getPlayers()[(i + game.currPlayerIndex) % game.getPlayers().length]);
            }
            game.setCurrOpponentIndex(game.getCurrOpponentIndex() + 1);
            phase = CardPhase.otherPilePhase;
        } else {
            // 选择对手
            if (game.getSelectButton().isIfActive()) {
                // 计算当前对手在玩家数组中的index
                int realOpponentIndex = (game.getCurrOpponentIndex() + game.currPlayerIndex + 1) % game.getPlayerNum();
                opponents.addLast(game.getPlayers()[realOpponentIndex]);
                phase = CardPhase.otherPilePhase;
                game.getSelectButton().resetButton();
            }
        }
    }

    protected void askForSayNo(MDCGame game) {
        if (!hasMoveToNext) {
            if (opponents.size() == 0) {
                if (needOtherBank) phase = CardPhase.otherBankPhase;
                else if (needOtherProperty) phase = CardPhase.otherPropertyPhase;
                isPhaseOver = true;
            } else moveToNext(game);
            // 切换至下一对手
        } else if (game.getPlayButton().isIfActive()) {
            // 询问对手是否出justSayNo
            if (checkForCard(game, "JustSayNo")) {
                hasMoveToNext = false; // 打出JustSayNo，进入下一个对手
            }
            game.getPlayButton().resetButton();
        } else if (game.getCancelButton().isIfActive()) {
            // 不打出，切换至其他阶段
            // TODO debug
            System.out.println("点击cancel");
            if (needOtherBank) phase = CardPhase.otherBankPhase;
            else if (needOtherProperty) {
                phase = CardPhase.otherPropertyPhase;
                game.setCurrPropertyIndex(game.getColors().indexOf(CardColor.yellow)); // 设为黄色
            }
            game.getButtons().clear();
            game.getButtons().add(game.getSelectButton());
            game.getCancelButton().resetButton();
        }
    }

    protected void moveToNext(MDCGame game) {
        System.out.println("移除一个");
        tempOpponent = opponents.removeFirst(); // 将队首的玩家移出
        game.setCurrPlayerPile(tempOpponent.getOwnPlayerPile()); // 将展示牌堆换成对手，不直接访问当前出牌玩家
        game.setCurrBankPile(tempOpponent.getOwnBank());
        game.setCurrPropertyPile(tempOpponent.getOwnProperty());
        game.getButtons().clear();
        game.getButtons().add(game.getPlayButton()); // 加入询问是否打出justSayNo的按键
        game.getButtons().add(game.getCancelButton());
        hasMoveToNext = true;
    }

    protected boolean checkForCard(MDCGame game, String cardName) {
        ArrayList<AbstractCard> playerCards = game.getCurrPlayerPile().getCards();
        for (AbstractCard card : playerCards) {
            if (card.getClass().getSimpleName().equals(cardName)) {
                int index = playerCards.indexOf(card);
                game.getCurrPlayerPile().removeCard(index); // 打出第一张JustSayNo，移除
                return true;
            }
        }
        return false;
    }

    @Override
    public void resetCard() {
        super.resetCard();
        this.isPlayable = false;
        this.needChooseOpponent = false;
        this.needOtherPile = false;
        this.needOtherBank = false;
        this.needOtherProperty = false;
        this.opponents = new LinkedList<>();
    }
}
