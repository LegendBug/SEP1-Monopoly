package mdc.states.game;

import mdc.components.buttons.Button;
import mdc.components.cards.*;
import mdc.components.cards.actioncards.AbstractActionCard;
import mdc.components.cards.actioncards.RentCard;
import mdc.components.cards.moneycards.MoneyCard;
import mdc.components.cards.properties.AbstractPropertyCard;
import mdc.components.cards.properties.PropertyCard;
import mdc.components.cards.properties.PropertyWildCard;
import mdc.components.piles.ActionPile;
import mdc.components.piles.DrawPile;
import mdc.components.piles.OwnBank;
import mdc.components.piles.OwnProperty;
import mdc.components.piles.OwnPlayerPile;
import mdc.components.players.Player;
import mdc.listeners.KeysListener;
import mdc.listeners.MousesListener;
import mdc.screens.GameScreen;
import mdc.states.State;
import mdc.tools.Config;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

public class MDCGame implements State, Game {
    private int playerNum;
    private int playedCardNum;
    private int roundNum;
    private int currTime;
    private int currButton;
    private int currPropertyIndex; // 相当于index
    private int currBankCardIndex;
    private int currPlayerCardIndex;
    public int currPlayerIndex;
    public int currOpponentIndex;
    public int tempPlayer;

    private AbstractCard currCard;
    private Player currPlayer;
    private Player[] players;

    private Button playButton;
    private Button saveButton;
    private Button selectButton;
    private Button cancelButton;
    private Button discardButton;
    private ArrayList<Button> buttons;

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
    private boolean isSelectPhase;
    private boolean isStateOver;
    boolean[] keysListenerBos;
    boolean[] mouseListenerBos;

    private final KeysListener keysListener;
    private final MousesListener mousesListener;
    private final ArrayList<CardColor> colors = new ArrayList<>();
    {
        colors.add(CardColor.brown);
        colors.add(CardColor.darkBlue);
        colors.add(CardColor.green);
        colors.add(CardColor.lightBlue);
        colors.add(CardColor.orange);
        colors.add(CardColor.pink);
        colors.add(CardColor.railRoad);
        colors.add(CardColor.red);
        colors.add(CardColor.utility);
        colors.add(CardColor.yellow);
    }
    private final Config config;

    public MDCGame(KeysListener keysListener, MousesListener mousesListener, Config config) {
        this.config = config;
        this.keysListener = keysListener;
        this.mousesListener = mousesListener;
        // TODO 优化初始化
    }

    @Override
    public void startState() {
        this.playerNum = 4; // TODO 改变游戏人数
        this.playedCardNum = 0;
        this.roundNum = 1;
        this.currTime = 0;
        this.currButton = -1;
        this.currOpponentIndex = -1;
        this.currBankCardIndex = -1;
        this.currPlayerCardIndex = -1;
        this.currPropertyIndex = -1;
        this.currPlayerIndex = 0;

        this.players = new Player[playerNum];

        this.playButton = new Button(GameScreen.playRect, GameScreen.play1, GameScreen.play2, GameScreen.play3);
        this.saveButton = new Button(GameScreen.saveRect, GameScreen.save1, GameScreen.save2, GameScreen.save3);
        this.selectButton = new Button(GameScreen.selectRect, GameScreen.select1, GameScreen.select2, GameScreen.select3);
        this.cancelButton = new Button(GameScreen.cancelRect, GameScreen.cancel1, GameScreen.cancel2, GameScreen.cancel3);
        this.discardButton = new Button(GameScreen.discardRect, GameScreen.discard1, GameScreen.discard2, GameScreen.discard3);
        this.buttons = new ArrayList<>();
        this.buttons.add(selectButton);

        this.lastPhase = null;
        this.currPhase = GamePhases.drawPhase;

        this.isPause = true;
        this.isPhaseOver = false;
        this.isActionPhase = false;
        this.isSelected = false;
        this.isSelectPhase = false;
        createPlayers();
        createPiles();
    }

    @Override
    public void updateState() {
        checkForPause();
        if (!isPaused()) {
            listenerController();
            phaseController();
            checkForWinner();
            currTime++;
        }
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public int getScreenWidth() {
        return GameScreen.screenWidth;
    }

    @Override
    public int getScreenHeight() {
        return GameScreen.screenHeight;
    }

    public int getPlayerNum() {
        return playerNum;
    }

    public int getPlayedCardNum() {
        return playedCardNum;
    }

    public void setPlayedCardNum(int num) {
        playedCardNum = num;
    }

    public int getCurrBankCardIndex() {
        return currBankCardIndex;
    }

    public int getCurrPlayerCardIndex() {
        return currPlayerCardIndex;
    }

    public void setCurrPlayerCardIndex(int index) {
        currPlayerCardIndex = index;
    }

    public AbstractCard getCurrCard() {
        return currCard;
    }

    public DrawPile getDrawPile() {
        return drawPile;
    }

    public OwnPlayerPile getCurrPlayerPile() {
        return currPlayerPile;
    }

    public OwnBank getCurrBankPile() {
        return currBankPile;
    }

    public OwnProperty getCurrPropertyPile() {
        return currPropertyPile;
    }

    public int getCurrOpponentIndex() {
        return currOpponentIndex;
    }

    public int getCurrPlayerIndex() {
        return currPlayerIndex;
    }

    public Player getCurrPlayer() {
        return currPlayer;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Button getPlayButton() {
        return playButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Button getSelectButton() {
        return selectButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getDiscardButton() {
        return discardButton;
    }

    public ArrayList<Button> getButtons() {
        return buttons;
    }

    public ArrayList<Button> getButtonsCopy() {
        return new ArrayList<>(buttons);
    }

    public int getCurrPropertyIndex() {
        return currPropertyIndex;
    }

    public void setCurrPropertyIndex(int index) {
        currPropertyIndex = index;
    }

    public ArrayList<CardColor> getColors() {
        return colors;
    }

    public int getDrawPileNum() {
        return this.drawPile.size();
    }

    public int getBankPileNum() {
        return this.currBankPile.size();
    }

    public GamePhases getCurrPhase() {
        return currPhase;
    }

    public void setCurrOpponentIndex(int index) {
        currOpponentIndex = index;
    }

    public void setCurrBankCardIndex(int index) {
        currBankCardIndex = index;
    }

    public void setCurrPlayerPile(OwnPlayerPile currPlayerPile) {
        this.currPlayerPile = currPlayerPile;
    }

    public void setCurrBankPile(OwnBank currBankPile) {
        this.currBankPile = currBankPile;
    }

    public void setCurrPropertyPile(OwnProperty currPropertyPile) {
        this.currPropertyPile = currPropertyPile;
    }

    private void createPlayers() {
        // 手牌堆、银行牌堆、房产牌堆顺带创建了
        for (int i = 0; i < playerNum; i++) {
            OwnPlayerPile playerPile = new OwnPlayerPile();
            OwnBank bankPile = new OwnBank();
            OwnProperty propertyPile = new OwnProperty();
            players[i] = new Player(bankPile, propertyPile, playerPile);
        }
        this.currPlayer = players[currPlayerIndex];
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
        this.currPlayerPile = currPlayer.getOwnPlayerPile();
        this.currBankPile = currPlayer.getOwnBank();
        this.currPropertyPile = currPlayer.getOwnProperty();
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
        String className = "mdc.components.cards.moneycards.MoneyCard";
        Config.GameInfo.MoneyCards moneyCardsInfo = config.getGameInfo().getMoneyCards();
        Field[] fields = Config.GameInfo.MoneyCards.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                int num = (int) field.get(moneyCardsInfo);
                int moneyValue = Integer.parseInt(field.getName().substring(1, 2));
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
        Map<CardColor, String[]> colorInfo = new HashMap<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String fieldName = field.getName();
                if (fieldName.split("_").length == 1) { // 添加单色牌
                    CardColor color = CardColor.valueOf(fieldName);
                    String[] info = field.get(propertiesInfo).toString().split(" ");
                    colorInfo.put(color, info); // 将单色牌信息加入Map中，便于后续创卡
                    createPropertyCards(color, info);
                } else if (fieldName.equals("multi_property")) { // 添加全色牌
                    int num = (int) field.get(propertiesInfo);
                    for (int i = 0; i < num; i++) createMultiPropertyCard(colorInfo);
                } else if (fieldName.split("_").length == 2) { // 添加双色牌
                    String[] colors = fieldName.split("_");
                    String[] info = field.get(propertiesInfo).toString().split(" ");
                    CardColor color1 = CardColor.valueOf(colors[0]);
                    CardColor color2 = CardColor.valueOf(colors[1]);
                    String[] info1 = colorInfo.get(color1);
                    String[] info2 = colorInfo.get(color2);
                    createPropertyWildCards(info, color1, color2, info1, info2);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void createMultiPropertyCard(Map<CardColor, String[]> colorInfo) {
        ArrayList<PropertyCard> cards = new ArrayList<>();
        for (Map.Entry<CardColor, String[]> entry : colorInfo.entrySet()) { // 遍历全部单色，创建单色卡，全部加入全色卡中
            CardColor color = entry.getKey();
            String[] info = entry.getValue();
            int[] values = new int[info.length - 1];
            for (int i = 1; i < info.length; i++) values[i - 1] = Integer.parseInt(info[i]);
            cards.add(createPropertyCard(color, values));
        }
        PropertyWildCard card = new PropertyWildCard(cards);
        this.drawPile.addCard(card);
    }

    private PropertyCard createPropertyCard(CardColor color, int[] values) {
        String className = "mdc.components.cards.properties.PropertyCard";
        PropertyCard card;
        try {
            Class<?> cardClass = Class.forName(className);
            Constructor<?> constructor;
            switch (values.length) {
                case 3 -> {
                    constructor = cardClass.getConstructor(int.class, CardColor.class, int.class, int.class);
                    card = (PropertyCard) constructor.newInstance(values[0], color, values[1], values[2]);
                }
                case 4 -> {
                    constructor = cardClass.getConstructor(int.class, CardColor.class, int.class, int.class, int.class);
                    card = (PropertyCard) constructor.newInstance(values[0], color, values[1], values[2], values[3]);
                }
                case 5 -> {
                    constructor = cardClass.getConstructor(int.class, CardColor.class, int.class, int.class, int.class, int.class);
                    card = (PropertyCard) constructor.newInstance(values[0], color, values[1], values[2], values[3], values[4]);
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

    private void createPropertyCards(CardColor color, String[] info) {
        int[] values = new int[info.length - 1];
        for (int i = 1; i < info.length; i++) values[i - 1] = Integer.parseInt(info[i]);
        for (int i = 0; i < Integer.parseInt(info[0]); i++) {
            AbstractPropertyCard card = createPropertyCard(color, values);
            this.drawPile.addCard(card);
        }
    }

    private void createPropertyWildCards(String[] info, CardColor color1, CardColor color2, String[] info1, String[] info2) {
        int[] values1 = new int[info1.length - 1];
        for (int i = 1; i < info1.length; i++) values1[i - 1] = Integer.parseInt(info1[i]);
        int[] values2 = new int[info2.length - 1];
        for (int i = 1; i < info2.length; i++) values2[i - 1] = Integer.parseInt(info2[i]);

        for (int i = 0; i < Integer.parseInt(info[0]); i++) {
            PropertyCard card1 = createPropertyCard(color1, values1);
            PropertyCard card2 = createPropertyCard(color2, values2);
            PropertyWildCard card = new PropertyWildCard(Integer.parseInt(info[1]), card1, card2);
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
                String[] info = field.get(rentCardsInfo).toString().split(" ");
                if (fieldName.equals("multiRent")) {
                    for (int i = 0; i < Integer.parseInt(info[0]); i++) this.drawPile.addCard(new RentCard(Integer.parseInt(info[1])));
                } else {
                    String[] colors = field.getName().split("_");
                    CardColor color1 = CardColor.valueOf(colors[0]);
                    CardColor color2 = CardColor.valueOf(colors[1]);
                    for (int i = 0; i < Integer.parseInt(info[0]); i++) {
                        Class<?> cardClass = Class.forName(className);
                        Constructor<?> constructor = cardClass.getConstructor(int.class, CardColor.class, CardColor.class);
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
        this.drawPile.deal(currPlayer, 2);
        isPhaseOver = true;
    }

    private void runPlayCardsPhase() {
        if (playedCardNum < 3) {
            if (!isSelected) isSelectPhase = true;
            else  {
                currCard = currPlayer.getOwnPlayerPile().getCards().get(currPlayerCardIndex);
                // 监听事件
                buttonController();
                // 执行卡牌
                currCard.play(this);
                // 执行卡牌不同时期 运行后的行动
                switch (currCard.getPhase()) {
                    case waitingPhase -> {
                        if (cancelButton.isIfActive()) {
                            cancelButton.resetButton();
                            isSelected = false;
                        }
                        // 判断出牌是否结束
                        if (currCard.isPhaseOver()) {
                            currCard.resetCard();
                            currPlayerPile.takeCard(currPlayerCardIndex, currBankPile); // 移除卡牌
                            currPlayerCardIndex = currPlayerPile.size() - 1;
                            playedCardNum++;
                            isSelected = false;
                        }
                    }
                    case chooseOpponentsPhase -> {
                        // 选择敌方玩家
                        int currOpponents = getPlayerNum() - 1;
                        if (keysListenerBos[5])
                            currOpponentIndex = (currOpponentIndex == -1 || currOpponentIndex == 0) ? currOpponents - 1 : currOpponentIndex - 1;
                        else if (keysListenerBos[4])
                            currOpponentIndex = (currOpponentIndex + 1) % currOpponents;
                    }
                    case ownPropertyPhase -> {
                        // 选择property
                        if (keysListenerBos[4])
                            currPropertyIndex = (currPropertyIndex == -1 || currPropertyIndex == 0) ? 9 : currPropertyIndex - 1;
                        else if (keysListenerBos[5])
                            currPropertyIndex = (currPropertyIndex + 1) % 10;
                        // 判断出牌是否结束
                        if (currCard.isPhaseOver()) {
                            currCard.resetCard();
                            if (currCard instanceof PropertyCard) // 将房产牌加入房产
                                currPlayerPile.takeCard(currPlayerCardIndex, currPropertyPile);
                            else if (currCard instanceof AbstractActionCard) // 是行动牌，移除
                                currPlayerPile.removeCard(currPlayerCardIndex);
                            currPlayerCardIndex = currPlayerPile.size() - 1;
                            currPropertyIndex = -1;
                            playedCardNum++;
                            isSelected = false;
                        }
                    }
                    case otherBankPhase -> {
                        // 在敌方玩家银行中选择卡牌
                        int currCards = currBankPile.size();
                        if (keysListenerBos[5])
                            currBankCardIndex = (currBankCardIndex == -1 || currBankCardIndex == 0) ? currCards - 1 : currBankCardIndex - 1;
                        else if (keysListenerBos[4])
                            currBankCardIndex = (currBankCardIndex + 1) % currCards;
                        else if (currBankCardIndex == -1)
                            currBankCardIndex = 0;
                        if (currCard.isPhaseOver()) {
                            currCard.resetCard();
                            System.out.println("over");
                            currPlayerPile = currPlayer.getOwnPlayerPile();
                            currBankPile = currPlayer.getOwnBank();
                            currPropertyPile = currPlayer.getOwnProperty();

                            currPlayerPile.removeCard(currPlayerCardIndex); // 移除该行动牌
                            currPlayerCardIndex = currPlayerPile.size() - 1;
                            currOpponentIndex = -1;
                            currBankCardIndex = -1;
                            currPropertyIndex = -1;
                            playedCardNum++;
                            isSelected = false;
                        }
                    }
                    case otherPropertyPhase -> {
                        // 在敌方玩家房产中选择
                        if (keysListenerBos[4])
                            currPropertyIndex = (currPropertyIndex == -1 || currPropertyIndex == 0) ? 9 : currPropertyIndex - 1;
                        else if (keysListenerBos[5])
                            currPropertyIndex = (currPropertyIndex + 1) % 10;
                        else if (currPropertyIndex == -1)
                            currPropertyIndex = 0;

                        if (currCard.isPhaseOver()) {
                            currCard.resetCard();

                            currPlayerPile = currPlayer.getOwnPlayerPile();
                            currBankPile = currPlayer.getOwnBank();
                            currPropertyPile = currPlayer.getOwnProperty();

                            currPlayerPile.removeCard(currPlayerCardIndex); // 移除该行动牌
                            currPlayerCardIndex = currPlayerPile.size() - 1;
                            currOpponentIndex = -1;
                            currPropertyIndex = -1;
                            playedCardNum++;
                            isSelected = false;
                        }
                    }
                    case ownPilePhase -> {
                        if (currCard.isPhaseOver()) {
                            currCard.resetCard();
                            currPlayerPile.removeCard(currPlayerCardIndex); // 移除该行动牌
                            currPlayerCardIndex = currPlayerPile.size() - 1;
                            currPropertyIndex = -1;
                            playedCardNum++;
                            isSelected = false;
                        }
                    }
                }
            }

        } else {
            isPhaseOver = true;
        }
    }

    private void runDiscardsPhase() {
        if (currPlayerPile.size() > 7) {
            if (!isSelected) isSelectPhase = true;
            else {
                buttonController();
                if (cancelButton.isIfActive()) {
                    cancelButton.setActive(false);
                    isSelected = false;
                }
                else if (discardButton.isIfActive()) {
                    currPlayerPile.removeCard(currPlayerCardIndex);// 移除卡牌
                    currPlayerCardIndex = currPlayerPile.size() - 1;
                    discardButton.setActive(false);
                    isSelected = false;
                }
            }
        } else {
            isPhaseOver = true;
        }
    }

    private void runSelectCards() {
        int currCards = currPlayer.getOwnPlayerPile().size();
        if (keysListenerBos[5])
            currPlayerCardIndex = (currPlayerCardIndex == -1 || currPlayerCardIndex == 0) ? currCards - 1 : currPlayerCardIndex - 1;
        else if (keysListenerBos[4])
            currPlayerCardIndex = (currPlayerCardIndex + 1) % currCards;

        buttonController();

        if (currPlayerCardIndex != -1 && selectButton.isIfActive()) {
            isSelected = true; // 卡牌选中
            isPhaseOver = true;
        }
        selectButton.setActive(false); // 防止不该选中的时候选中
    }

    private void runActionPhase() {

    }

    @Override
    public void listenerController() {
        keysListenerBos = keysListener.getBos();
        mouseListenerBos = mousesListener.getBos();
    }

    private void buttonController() {
        if (keysListenerBos[2])
            currButton = (currButton + 1) % buttons.size();
        else if (keysListenerBos[3])
            currButton = (currButton == -1 || currButton == 0) ? buttons.size() - 1 : currButton - 1;
        if (mousesListener.getMousePoint() != null) {
            // 方向键控制
            for (int i = 0; i < buttons.size(); i++) {
                Button button = buttons.get(i);
                button.setSelected(i == currButton);
                if (button.checkForState(mousesListener.getMousePoint(), mouseListenerBos[1],
                        mouseListenerBos[2], keysListenerBos[1])) {
                    currButton = i;
                    button.setSelected(true); // 鼠标停留，选中
                } else if (keysListenerBos[0] || mouseListenerBos[0]) {
                    currButton = -1;
                    button.setSelected(false);
                }
                if (i != currButton) buttons.get(i).setSelected(false); // 将其他卡牌设为为未选中
            }
        }
    }

    private void phaseController() {
        if (isActionPhase) {
            isActionPhase = false; // 防止重复设置phase
            this.lastPhase = GamePhases.valueOf(currPhase.toString());
            this.currPhase = GamePhases.actionPhase;
        } else if (isSelectPhase) {
            isSelectPhase = false;
            buttons.clear();
            buttons.add(selectButton);
            this.lastPhase = GamePhases.valueOf(currPhase.toString());
            this.currPhase = GamePhases.selectPhase;
        } else {
            switch (currPhase) {
                case drawPhase -> {
                    if (isPhaseOver) {
                        currButton = -1;
                        currPhase = GamePhases.playPhase;
                        buttons.clear();
                        buttons.add(cancelButton);
                        buttons.add(playButton);
                        isPhaseOver = false;
                    } else runDrawCardsPhase();
                }
                case playPhase -> {
                    if (isPhaseOver) {
                        currPlayerCardIndex = -1;
                        playedCardNum = 0;
                        currButton = -1;
                        currPhase = GamePhases.discardPhase;
                        isPhaseOver = false;
                    } else runPlayCardsPhase();
                }
                case actionPhase -> { // TODO 特殊阶段
                    if (isPhaseOver) {
                        currPhase = GamePhases.valueOf(lastPhase.toString());
                        isPhaseOver = false;
                    } else runActionPhase();
                }
                case selectPhase -> {
                    if (isPhaseOver) {
                        currPhase = GamePhases.valueOf(lastPhase.toString());
                        currButton = -1;
                        selectButton.resetButton();
                        buttons.clear();
                        buttons.add(cancelButton);
                        if (currPhase == GamePhases.discardPhase) buttons.add(discardButton);
                        isPhaseOver = false;
                    } else runSelectCards();
                }
                case discardPhase -> {
                    if (isPhaseOver) {
                        currPlayerCardIndex = -1;
                        currButton = -1;
                        currPhase = GamePhases.drawPhase;
                        isPhaseOver = false;
                        moveToNextPlayer(); // 转移到下一玩家
                    } else runDiscardsPhase();
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
        if (isStateOver) return true;
        return isPause && keysListener.hasPressedExit(); // 暂停时按esc退出
    }

    @Override
    public void checkForPause() {
        if (!isPause && keysListener.hasPressedExit()) isPause = true; // 按esc暂停
        else if (isPause && keysListener.hasPressedEnter()) isPause = false; // 按enter取消暂停
    }

    public void checkForWinner() {
        for (Player player : players) {
            OwnProperty propertyPile = player.getOwnProperty();
            if (propertyPile.achievedVictory()) {
                System.out.println(currPlayerIndex + " is the winner!!!");
                isStateOver = true;
            }
        }
    }

    public void moveToNextPlayer() {
        roundNum += 1;
        currPlayerIndex = (currPlayerIndex + 1) % playerNum;
        currPlayer = players[currPlayerIndex];
        currPlayerPile = currPlayer.getOwnPlayerPile();
        currBankPile = currPlayer.getOwnBank();
        currPropertyPile = currPlayer.getOwnProperty();
    }

    @Override
    public void moveToOtherStates() {
        keysListener.resetKeyPresses();
        mousesListener.resetMousePressed();
        buttons.clear();
    }
}