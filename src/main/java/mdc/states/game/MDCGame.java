package mdc.states.game;

import mdc.components.buttons.Button;
import mdc.components.cards.AbstractCard;
import mdc.components.cards.CardColor;
import mdc.components.cards.actioncards.AbstractActionCard;
import mdc.components.cards.actioncards.RentCard;
import mdc.components.cards.moneycards.MoneyCard;
import mdc.components.cards.properties.AbstractPropertyCard;
import mdc.components.cards.properties.PropertyCard;
import mdc.components.cards.properties.PropertyWildCard;
import mdc.components.piles.DrawPile;
import mdc.components.piles.OwnBank;
import mdc.components.piles.OwnPlayerPile;
import mdc.components.piles.OwnProperty;
import mdc.components.players.Player;
import mdc.listeners.KeysListener;
import mdc.listeners.MousesListener;
import mdc.screens.GameScreen;
import mdc.states.State;
import mdc.tools.Config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the main control class of the game. Used to control different stages of the game.
 * The two most important methods are startState and updateState
 */
public class MDCGame implements State, Game {
    private final KeysListener keysListener;// Listener for keyboard inputs
    private final MousesListener mousesListener;// Listener for mouse inputs
    private final ArrayList<CardColor> colors = new ArrayList<>();// List of card colors
    private final Config config;// Game configuration
    public int currPlayerIndex;// Index of the current player
    public int currOpponentIndex;// Index of the selected opponent
    boolean[] keysListenerBos;
    boolean[] mouseListenerBos;
    private int playerNum;// Number of players
    private int playedCardNum;// Number of cards played in a round
    private int roundNum; // Current round number
    private int currButton;
    private int currPropertyIndex;
    private int currBankCardIndex;
    private int currPlayerCardIndex;
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
    private GameInfoBar gameInfoBar;
    private OwnPlayerPile currPlayerPile;
    private OwnBank currBankPile;
    private OwnProperty currPropertyPile;
    private DrawPile drawPile;
    private boolean isPause;
    private boolean isSelected;
    private boolean isPhaseOver;
    private boolean isSelectPhase;
    private boolean isStateOver;

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

    public MDCGame(KeysListener keysListener, MousesListener mousesListener, Config config) {
        this.config = config;
        this.keysListener = keysListener;
        this.mousesListener = mousesListener;
    }

    @Override
    public void startState() {
        this.playerNum = 5; // TODO Change number of players
        this.playedCardNum = 0;
        this.roundNum = 1;
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
        this.gameInfoBar = new GameInfoBar(6);
        this.gameInfoBar.add("Press Enter to start.");

        this.isPause = true;
        this.isPhaseOver = false;
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

    public void setCurrBankCardIndex(int index) {
        currBankCardIndex = index;
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

    public void setCurrPlayerPile(OwnPlayerPile currPlayerPile) {
        this.currPlayerPile = currPlayerPile;
    }

    public OwnBank getCurrBankPile() {
        return currBankPile;
    }

    public void setCurrBankPile(OwnBank currBankPile) {
        this.currBankPile = currBankPile;
    }

    public OwnProperty getCurrPropertyPile() {
        return currPropertyPile;
    }

    public void setCurrPropertyPile(OwnProperty currPropertyPile) {
        this.currPropertyPile = currPropertyPile;
    }

    public int getCurrOpponentIndex() {
        return currOpponentIndex;
    }

    public void setCurrOpponentIndex(int index) {
        currOpponentIndex = index;
    }

    public int getPlayerIndex(Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i].equals(player)) {
                return i; // Returns the index of the current player in the array
            }
        }
        return -1; // If no match is found, -1 is returned to indicate no match
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

    public GamePhases getCurrPhase() {
        return currPhase;
    }

    public GameInfoBar getGameInfoBar() {
        return gameInfoBar;
    }

    private void createPlayers() {
        // Hand pile, bank pile, and real estate pile were created in parallel
        for (int i = 0; i < playerNum; i++) {
            OwnPlayerPile playerPile = new OwnPlayerPile();
            OwnBank bankPile = new OwnBank();
            OwnProperty propertyPile = new OwnProperty();
            players[i] = new Player(bankPile, propertyPile, playerPile);
        }
        this.currPlayer = players[currPlayerIndex];
    }

    private void createPiles() {
        // Add to the draw pile
        this.drawPile = new DrawPile();
        createActionCards();
        createMoneyCards();
        createProperties();
        createRentCards();
        this.drawPile.shuffle();
        // Sets the current player's deck
        this.currPlayerPile = currPlayer.getOwnPlayerPile();
        this.currBankPile = currPlayer.getOwnBank();
        this.currPropertyPile = currPlayer.getOwnProperty();
    }

    private void createActionCards() {
        String packageName = "mdc.components.cards.actioncards.";
        Config.GameInfo.ActionCards actionCardsInfo = config.getGameInfo().getActionCards();
        Field[] fields = Config.GameInfo.ActionCards.class.getDeclaredFields(); // Use mapping
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String[] info = field.get(actionCardsInfo).toString().split(" "); // Gets data from config in the mapping
                String className = packageName + field.getName(); // The class name is the same as the field name, so use field.getname () directly
                Class<?> cardClass = Class.forName(className); //Load class
                for (int i = 0; i < Integer.parseInt(info[0]); i++) {
                    AbstractActionCard card = (AbstractActionCard) cardClass.getConstructor(int.class).newInstance(Integer.parseInt(info[1])); // 创建卡牌对象
                    this.drawPile.addCard(card); // Add cards
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
                if (fieldName.split("_").length == 1) { // Add monochrome
                    CardColor color = CardColor.valueOf(fieldName);
                    String[] info = field.get(propertiesInfo).toString().split(" ");
                    colorInfo.put(color, info); // Add monochrome card information to Map for subsequent card creation
                    createPropertyCards(color, info);
                } else if (fieldName.equals("multi_property")) { //Add full color cards
                    int num = (int) field.get(propertiesInfo);
                    for (int i = 0; i < num; i++) createMultiPropertyCard(colorInfo);
                } else if (fieldName.split("_").length == 2) { // Add two-color cards
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
        for (Map.Entry<CardColor, String[]> entry : colorInfo.entrySet()) { // Go through all the monochrome, create a single color card, add all to the full color card
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
                    for (int i = 0; i < Integer.parseInt(info[0]); i++)
                        this.drawPile.addCard(new RentCard(Integer.parseInt(info[1])));
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
            else {
                currCard = currPlayer.getOwnPlayerPile().getCards().get(currPlayerCardIndex);
                // Listening event
                buttonController();
                // Executive card
                currCard.play(this);
                // Perform the actions of the card after different periods of operation
                switch (currCard.getPhase()) {
                    case waitingPhase -> {
                        if (cancelButton.isIfActive()) {
                            cancelButton.resetButton();
                            isSelected = false;
                        }
                        //Determine if the card is over
                        if (currCard.isPhaseOver()) {
                            currCard.resetCard();
                            currPlayerPile.takeCard(currPlayerCardIndex, currBankPile); // Remove cards
                            currPlayerCardIndex = currPlayerPile.size() - 1;
                            playedCardNum++;
                            isSelected = false;
                        }
                    }
                    case chooseOpponentsPhase -> {
                        // Select enemy players
                        int currOpponents = getPlayerNum() - 1;
                        if (keysListenerBos[5])
                            currOpponentIndex = (currOpponentIndex == -1 || currOpponentIndex == 0) ? currOpponents - 1 : currOpponentIndex - 1;
                        else if (keysListenerBos[4])
                            currOpponentIndex = (currOpponentIndex + 1) % currOpponents;
                    }
                    case ownPropertyPhase -> {
                        // choose property
                        if (keysListenerBos[4])
                            currPropertyIndex = (currPropertyIndex == -1 || currPropertyIndex == 0) ? 9 : currPropertyIndex - 1;
                        else if (keysListenerBos[5])
                            currPropertyIndex = (currPropertyIndex + 1) % 10;
                        // Determine if the card is over
                        if (currCard.isPhaseOver()) {
                            currCard.resetCard();
                            if (currCard instanceof PropertyCard) // Add the property tag to the property
                                currPlayerPile.takeCard(currPlayerCardIndex, currPropertyPile);
                            else if (currCard instanceof AbstractActionCard) // It's an action card. Remove it
                                currPlayerPile.removeCard(currPlayerCardIndex);
                            currPlayerCardIndex = currPlayerPile.size() - 1;
                            currPropertyIndex = -1;
                            playedCardNum++;
                            isSelected = false;
                        }
                    }
                    case otherBankPhase -> {
                        // Select a card in the enemy player's bank
                        int currCards = currBankPile.size();
                        if (keysListenerBos[5])
                            currBankCardIndex = (currBankCardIndex == -1 || currBankCardIndex == 0) ? currCards - 1 : currBankCardIndex - 1;
                        else if (keysListenerBos[4])
                            currBankCardIndex = (currBankCardIndex + 1) % currCards;
                        else if (currBankCardIndex == -1)
                            currBankCardIndex = 0;
                        if (currCard.isPhaseOver()) {
                            currCard.resetCard();
                            currPlayerPile = currPlayer.getOwnPlayerPile();
                            currBankPile = currPlayer.getOwnBank();
                            currPropertyPile = currPlayer.getOwnProperty();

                            currPlayerPile.removeCard(currPlayerCardIndex); // Remove the action card
                            currPlayerCardIndex = currPlayerPile.size() - 1;
                            currOpponentIndex = -1;
                            currBankCardIndex = -1;
                            currPropertyIndex = -1;
                            playedCardNum++;
                            isSelected = false;
                        }
                    }
                    case otherPropertyPhase -> {
                        // Choose from enemy player properties
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

                            currPlayerPile.removeCard(currPlayerCardIndex); // Remove the action card
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
                            currPlayerPile.removeCard(currPlayerCardIndex); // Remove the action card
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
                } else if (discardButton.isIfActive()) {
                    currPlayerPile.removeCard(currPlayerCardIndex);// Remove cards
                    currPlayerCardIndex = currPlayerPile.size() - 1;
                    discardButton.setActive(false);
                    isSelected = false;
                }
            }
        } else {
            isPhaseOver = true;
        }
    }

    private void runSelectCardsPhase() {
        int currCards = currPlayer.getOwnPlayerPile().size();
        if (keysListenerBos[5])
            currPlayerCardIndex = (currPlayerCardIndex == -1 || currPlayerCardIndex == 0) ? currCards - 1 : currPlayerCardIndex - 1;
        else if (keysListenerBos[4])
            currPlayerCardIndex = (currPlayerCardIndex + 1) % currCards;

        buttonController();

        if (currPlayerCardIndex != -1 && selectButton.isIfActive()) {
            isSelected = true; // Card pick
            isPhaseOver = true;
        }
        selectButton.setActive(false); // Prevent checking when you shouldn't
        if (lastPhase == GamePhases.playPhase && keysListenerBos[6]) {
            playedCardNum = 3;
            isPhaseOver = true;
        }
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
            // Directional key control
            for (int i = 0; i < buttons.size(); i++) {
                Button button = buttons.get(i);
                button.setSelected(i == currButton);
                if (button.checkForState(mousesListener.getMousePoint(), mouseListenerBos[1],
                        mouseListenerBos[2], keysListenerBos[1])) {
                    currButton = i;
                    button.setSelected(true); // Mouse hover and select
                } else if (keysListenerBos[0] || mouseListenerBos[0]) {
                    currButton = -1;
                    button.setSelected(false);
                }
                if (i != currButton) buttons.get(i).setSelected(false); // Set other cards to unchecked
            }
        }
    }

    /**
     * Controls the stage of a turn at which the current player plays
     */
    private void phaseController() {
        if (isSelectPhase) {
            isSelectPhase = false; // Prevent repeated phase Settings
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
                        gameInfoBar.add("Player " + currPlayerIndex + ", it's now your turn.");
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
                case selectPhase -> {
                    if (isPhaseOver) {
                        currPhase = GamePhases.valueOf(lastPhase.toString());
                        currButton = -1;
                        selectButton.resetButton();
                        buttons.clear();
                        buttons.add(cancelButton);
                        if (currPhase == GamePhases.discardPhase) buttons.add(discardButton);
                        isPhaseOver = false;
                    } else runSelectCardsPhase();
                }
                case discardPhase -> {
                    if (isPhaseOver) {
                        currPlayerCardIndex = -1;
                        currButton = -1;
                        currPhase = GamePhases.drawPhase;
                        isPhaseOver = false;
                        gameInfoBar.add("End of Player " + currPlayerIndex + " discard.");
                        moveToNextPlayer(); // Move on to the next player
                    } else runDiscardsPhase();
                }
            }
        }
    }

    @Override
    public boolean isPaused() {
        return isPause;
    }

    @Override
    public boolean isStateOver() {
        if (isStateOver) return true;
        return isPause && keysListener.hasPressedExit(); // Press esc to exit when pausing
    }

    @Override
    public void checkForPause() {
        if (!isPause && keysListener.hasPressedExit()) {
            gameInfoBar.add("Game pauses.");
            isPause = true; // Press esc to pause
        } else if (isPause && keysListener.hasPressedEnter()) {
            if (roundNum == 1) gameInfoBar.add("Game starts!");
            else gameInfoBar.add("Game continues.");
            isPause = false; // Press enter to cancel the pause
        }
    }

    public void checkForWinner() {
        for (Player player : players) {
            OwnProperty propertyPile = player.getOwnProperty();
            if (propertyPile.achievedVictory()) {
                System.out.println("The winner is Player " + currPlayerIndex + "!!!!!");
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