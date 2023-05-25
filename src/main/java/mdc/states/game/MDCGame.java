package mdc.states.game;

import mdc.components.cards.Colors;
import mdc.components.cards.actioncards.AbstractActionCard;
import mdc.components.cards.actioncards.RentCard;
import mdc.components.cards.moneycards.MoneyCard;
import mdc.components.cards.properties.AbstractPropertyCard;
import mdc.components.cards.properties.Property;
import mdc.components.cards.properties.PropertyWild;
import mdc.components.piles.actionpile.ActionPile;
import mdc.components.piles.drawpile.DrawPile;
import mdc.components.piles.ownbank.OwnBank;
import mdc.components.piles.ownproperties.OwnProperty;
import mdc.components.piles.playerpile.OwnPlayerPile;
import mdc.components.players.Player;
import mdc.listeners.KeysListener;
import mdc.listeners.MousesListener;
import mdc.screenpainters.GameScreen;
import mdc.states.ButtonStates;
import mdc.states.State;
import mdc.tools.Config;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MDCGame implements State, Game {
    private final KeysListener keysListener;
    private final MousesListener mousesListener;
    private final Config config;
    private int playerNum;
    private int roundNum;
    private Player[] players;
    private int currTime;
    public int currPlayer;
    private int currCard;
    private ButtonStates buttonState;
    private GameButtons gameButton;
    private GamePhases lastPhase;
    private GamePhases currPhase;
    private OwnPlayerPile currPlayerPile;
    private OwnBank currBankPile;
    private OwnProperty currPropertyPile;
    private ActionPile actionPile;
    private DrawPile drawPile;
    private DrawPile discardPile;
    // TODO 添加2~5人玩家的功能
    private boolean isPause;
    private boolean isSelected;
    private boolean isPhaseOver;
    private boolean isActionPhase;
    private boolean isSelectCards;

    public boolean isSelected() {
        return isSelected;
    }

    public MDCGame(KeysListener keysListener, MousesListener mousesListener, Config config) {
        this.config = config;
        this.keysListener = keysListener;
        this.mousesListener = mousesListener;
        // TODO 优化初始化
    }

    @Override
    public void startState() {
        this.isPause = true;
        this.currTime = 0;
        this.currPlayer = 0;
        this.currCard = -1;
        this.lastPhase = null;
        this.buttonState = ButtonStates.NORMAL;
        this.gameButton = GameButtons.NULL;
        this.currPhase = GamePhases.drawCards;
        this.playerNum = 5;
        this.roundNum = 1;
        this.players = new Player[playerNum];
        this.isPhaseOver = false;
        this.isActionPhase = false;
        createPlayers();
        createPiles();
    }

    @Override
    public void updateState() {
        checkForPause();
        if (!isPaused()) {
            listenerController();
            phaseController();
            currTime++;
        }
    }

    @Override
    public int getScreenWidth() {
        return GameScreen.SCREEN_WIDTH;
    }

    @Override
    public int getScreenHeight() {
        return GameScreen.SCREEN_HEIGHT;
    }

    public int getCurrCard() {
        return currCard;
    }

    public Player getCurrPlayer() {
        return players[currPlayer];
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getDrawPileNum() {
        return this.drawPile.size();
    }

    public GameButtons getGameButton() {
        return gameButton;
    }

    public ButtonStates getButtonState() {
        return buttonState;
    }

    public GamePhases getCurrPhase() {
        return currPhase;
    }

    private void createPlayers() {
        // 手牌堆、银行牌堆、房产牌堆顺带创建了
        for (int i = 0; i < playerNum; i++) {
            OwnPlayerPile playerPile = new OwnPlayerPile();
            OwnBank bankPile = new OwnBank();
            OwnProperty propertyPile = new OwnProperty();
            players[i] = new Player(bankPile, propertyPile, playerPile);
        }
    }

    private void createPiles() {
        this.actionPile = new ActionPile();
        // 加入抽牌堆
        this.drawPile = new DrawPile();
        createActionCards();
        createMoneyCards();
        createProperties();
        createRentCards();
        this.drawPile.shuffle();
        // 加入弃牌堆
        this.discardPile = new DrawPile();
        // 设置当前玩家牌堆
        this.currPlayerPile = players[currPlayer].getOwnPlayerPile();
        this.currBankPile = players[currPlayer].getOwnBank();
        this.currPropertyPile = players[currPlayer].getOwnProperty();
    }

    private void createActionCards() {
        String packageName = "mdc.components.cards.actioncards.";
        Config.GameInfo.ActionCards actionCardsInfo = config.getGameInfo().getActionCards();
        Field[] fields = Config.GameInfo.ActionCards.class.getDeclaredFields(); // 使用映射
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String[] info = field.get(actionCardsInfo).toString().split(" "); // 获取映射中config的数据
                String className = packageName + field.getName(); // 类名和字段名相同，所以直接使用field.getName()
                Class<?> cardClass = Class.forName(className); // 加载类
                for (int i = 0; i < Integer.parseInt(info[0]); i++) {
                    AbstractActionCard card = (AbstractActionCard) cardClass.getConstructor(int.class).newInstance(Integer.parseInt(info[1])); // 创建卡牌对象
                    this.drawPile.addCard(card); // 添加入卡牌
                }
            } catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InstantiationException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void createMoneyCards() {
        String packageName = "mdc.components.cards.moneycards.";
        Config.GameInfo.MoneyCards moneyCardsInfo = config.getGameInfo().getMoneyCards();
        Field[] fields = Config.GameInfo.MoneyCards.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                int num = (int) field.get(moneyCardsInfo);
                int moneyValue = Integer.parseInt(field.getName().substring(1, 2));
                String className = packageName + "MoneyCard";
                for (int i = 0; i < num; i++) {
                    MoneyCard card = (MoneyCard) Class.forName(className).getConstructor(int.class).newInstance(moneyValue);
                    this.drawPile.addCard(card);
                }
            } catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InstantiationException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void createProperties() {
        Config.GameInfo.Properties propertiesInfo = config.getGameInfo().getProperties();
        Field[] fields = Config.GameInfo.Properties.class.getDeclaredFields();
        Map<Colors, String[]> colorInfo = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                if (fieldName.equals("multiColour")) { // 添加全色牌
                    int num = (int) field.get(propertiesInfo);
                    for (int i = 0; i < num; i++) this.drawPile.addCard(new Property());
                } else if (fieldName.split("_").length == 1) { // 添加单色牌
                    Colors color = Colors.valueOf(fieldName);
                    String[] info = field.get(propertiesInfo).toString().split(" ");
                    colorInfo.put(color, info);
                    createPropertyCards(color, info);
                } else if (fieldName.split("_").length == 2) { // 添加双色牌
                    String[] colors = fieldName.split("_");
                    String[] info = field.get(propertiesInfo).toString().split(" ");
                    Colors color1 = Colors.valueOf(colors[0]);
                    Colors color2 = Colors.valueOf(colors[1]);
                    String[] info1 = colorInfo.get(color1);
                    String[] info2 = colorInfo.get(color2);
                    createPropertyWildCards(info, color1, color2, info1, info2);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Property createPropertyCard(Colors color, int[] values) {
        String className = "mdc.components.cards.properties.Property";
        Property card;
        try {
            Class<?> cardClass = Class.forName(className);
            Constructor<?> constructor;
            switch (values.length) {
                case 3 -> {
                    constructor = cardClass.getConstructor(int.class, Colors.class, int.class, int.class);
                    card = (Property) constructor.newInstance(values[0], color, values[1], values[2]);
                }
                case 4 -> {
                    constructor = cardClass.getConstructor(int.class, Colors.class, int.class, int.class, int.class);
                    card = (Property) constructor.newInstance(values[0], color, values[1], values[2], values[3]);
                }
                case 5 -> {
                    constructor = cardClass.getConstructor(int.class, Colors.class, int.class, int.class, int.class, int.class);
                    card = (Property) constructor.newInstance(values[0], color, values[1], values[2], values[3], values[4]);
                }
                default -> throw new IllegalArgumentException("Invalid number of arguments for Property card");
            }
            return card;
        } catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException | InstantiationException |
                 InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createPropertyCards(Colors color, String[] info) {
        int[] values = new int[info.length - 1];
        for (int i = 1; i < info.length; i++) values[i - 1] = Integer.parseInt(info[i]);
        for (int i = 0; i < Integer.parseInt(info[0]); i++) {
            AbstractPropertyCard card = createPropertyCard(color, values);
            this.drawPile.addCard(card);
        }
    }

    private void createPropertyWildCards(String[] info, Colors color1, Colors color2, String[] info1, String[] info2) {
        int[] values1 = new int[info1.length - 1];
        for (int i = 1; i < info1.length; i++) values1[i - 1] = Integer.parseInt(info1[i]);
        int[] values2 = new int[info2.length - 1];
        for (int i = 1; i < info2.length; i++) values2[i - 1] = Integer.parseInt(info2[i]);

        for (int i = 0; i < Integer.parseInt(info[0]); i++) {
            Property card1 = createPropertyCard(color1, values1);
            Property card2 = createPropertyCard(color2, values2);
            PropertyWild card = new PropertyWild(Integer.parseInt(info[1]), card1, card2);
            this.drawPile.addCard(card);
        }
    }

    private void createRentCards() {
        String className = "mdc.components.cards.actioncards.RentCard";
        Config.GameInfo.RentCards rentCardsInfo = config.getGameInfo().getRentCards();
        Field[] fields = Config.GameInfo.RentCards.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                if (fieldName.equals("multiColour")) {
                    String[] info = field.get(rentCardsInfo).toString().split(" ");
                    for (int i = 0; i < Integer.parseInt(info[0]); i++) this.drawPile.addCard(new RentCard(Integer.parseInt(info[1])));
                } else {
                    String[] info = field.get(rentCardsInfo).toString().split(" ");
                    String[] colors = field.getName().split("_");
                    Colors color1 = Colors.valueOf(colors[0]);
                    Colors color2 = Colors.valueOf(colors[1]);
                    for (int i = 0; i < Integer.parseInt(info[0]); i++) {
                        Class<?> cardClass = Class.forName(className);
                        Constructor<?> constructor = cardClass.getConstructor(int.class, Colors.class, Colors.class);
                        RentCard card = (RentCard) constructor.newInstance(Integer.parseInt(info[1]), color1, color2);
                        this.drawPile.addCard(card);
                    }
                }

            } catch (IllegalAccessException | ClassNotFoundException | NoSuchMethodException |
                     InvocationTargetException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void runDrawCardsPhase() {
        if (roundNum == 1) for (Player player : players) this.drawPile.deal(player, 5);
        this.drawPile.deal(players[currPlayer], 2);
        isPhaseOver = true;
    }

    private void runPlayCardsPhase() {
        isPhaseOver = true;
    }

    private void runDiscardsPhase() {
        if (currPlayerPile.size() > 7) {
            if (!isSelected) {
                isSelectCards = true;
            } else {
                if (mousesListener.getMousePoint() != null) {
                    if (GameScreen.DISCARD_RECT.contains(mousesListener.getMousePoint())) {
                        gameButton = GameButtons.DISCARD;
                        if (mousesListener.hasPressedButton1()) buttonState = ButtonStates.PRESSED;
                        else if (mousesListener.hasReleasedButton1()) {
                            currPlayerPile.getCards().remove(currCard);// 移除卡牌
                            currCard = currPlayerPile.size() - 1;
                            isSelected = false;
                        } else buttonState = ButtonStates.HOVER;
                    } else if (GameScreen.CANCEL_RECT.contains(mousesListener.getMousePoint())) {
                        gameButton = GameButtons.CANCEL;
                        if (mousesListener.hasPressedButton1()) {
                            buttonState = ButtonStates.PRESSED;
                        } else if (mousesListener.hasReleasedButton1()) {
                            isSelected = false;
                            buttonState = ButtonStates.HOVER;
                        } else {
                            buttonState = ButtonStates.HOVER;
                        }
                    }

                }
            }
        } else {
            isPhaseOver = true;
        }
    }

    private void runSelectCards() {
        int currCards = getCurrPlayer().getOwnPlayerPile().size();
        if (keysListener.hasPressedLeft()) currCard = (currCard == -1 || currCard == 0) ? currCards - 1 : currCard - 1;
        else if (keysListener.hasPressedRight()) currCard = (currCard + 1) % currCards;

        if (mousesListener.getMousePoint() != null) {
            if (GameScreen.SELECT_RECT.contains(mousesListener.getMousePoint())) {
                gameButton = GameButtons.SELECT;
                if (mousesListener.hasPressedButton1()) buttonState = ButtonStates.PRESSED;
                else if (mousesListener.hasReleasedButton1()) {
                    isSelected = true;
                    isPhaseOver = true;
                } else buttonState = ButtonStates.HOVER;
            }
        }
    }

    private void runActionPhase() {

    }

    @Override
    public void listenerController() {
        if (keysListener.hasPressedUp()) {
            switch (currPhase) {
                case playCards, discardCards, actionPhase -> isPhaseOver = true;
            }
        } else if (currPhase == GamePhases.playCards && keysListener.hasPressedDown()) {
            isActionPhase = true;
        }
    }

    private void phaseController() {
        if (isActionPhase) {
            isActionPhase = false; // 防止重复设置phase
            this.lastPhase = GamePhases.valueOf(currPhase.toString());
            this.currPhase = GamePhases.actionPhase;
        } else if (isSelectCards) {
            isSelectCards = false;
            this.lastPhase = GamePhases.valueOf(currPhase.toString());
            this.currPhase = GamePhases.selectCards;
        } else {
            switch (currPhase) {
                case drawCards -> {
                    if (isPhaseOver) {
                        currCard = -1;
                        currPhase = GamePhases.playCards;
                        isPhaseOver = false;
                    } else runDrawCardsPhase();
                }
                case playCards -> {
                    if (isPhaseOver) {
                        currCard = -1;
                        currPhase = GamePhases.discardCards;
                        isPhaseOver = false;
                    } else runPlayCardsPhase();
                }
                case discardCards -> {
                    if (isPhaseOver) {
                        moveToNextPlayer(); // 转移到下一玩家
                        currPhase = GamePhases.drawCards;
                        isPhaseOver = false;
                    } else runDiscardsPhase();
                }
                case actionPhase -> {
                    if (isPhaseOver) {
                        currPhase = GamePhases.valueOf(lastPhase.toString());
                        isPhaseOver = false;
                    } else runActionPhase();
                }
                case selectCards -> {
                    if (isPhaseOver) {
                        currPhase = GamePhases.valueOf(lastPhase.toString());
                        isPhaseOver = false;
                    } else runSelectCards();
                }
            }
        }
    }

    /**
     * This method is used to determine whether there is a collision between entities. If yes, the index value of the collided entity is returned; Otherwise, return - 1
     *
     * @param rect    Entity causing collision
     * @param targets List of rect that may be collided
     */
    private int getIntersecting(Rectangle rect, List<Rectangle> targets) {
        for (int i = 0; i < targets.size(); i++) {
            if (rect.intersects(targets.get(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isPaused() {
        return isPause;
    }

    @Override
    public boolean isStateOver() {
        return isPause && keysListener.hasPressedExit(); // 暂停时按esc退出
    }

    @Override
    public void checkForPause() {
        if (!isPause && keysListener.hasPressedExit()) isPause = true; // 按esc暂停
        else if (isPause && keysListener.hasPressedEnter()) isPause = false; // 按enter取消暂停
    }

    public void moveToNextPlayer() {
        roundNum += 1;
        currPlayer = (currPlayer + 1) % playerNum;
        currPlayerPile = players[currPlayer].getOwnPlayerPile();
        currBankPile = players[currPlayer].getOwnBank();
        currPropertyPile = players[currPlayer].getOwnProperty();
    }

    @Override
    public void moveToOtherStates() {
        keysListener.resetKeyPresses();
        mousesListener.resetMousePressed();
    }
}