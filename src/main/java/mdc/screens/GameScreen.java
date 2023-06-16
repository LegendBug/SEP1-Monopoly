package mdc.screens;

import mdc.components.buttons.Button;
import mdc.components.cards.AbstractCard;
import mdc.components.cards.CardColor;
import mdc.components.cards.ICard;
import mdc.components.cards.actioncards.AbstractActionCard;
import mdc.components.cards.actioncards.RentCard;
import mdc.components.cards.moneycards.MoneyCard;
import mdc.components.cards.properties.PropertyCard;
import mdc.components.players.Player;
import mdc.states.game.GameInfoBar;
import mdc.states.game.GamePhases;
import mdc.states.game.MDCGame;
import mdc.tools.Config;
import mdc.tools.GraphPainter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class is used to draw components on game screen
 * This part might look confusing...
 */
public class GameScreen extends JPanel {
    public static int screenWidth;
    public static int screenHeight;
    public static Rectangle backgroundRect;
    public static Rectangle playRect;
    public static Rectangle saveRect;
    public static Rectangle discardRect;
    public static Rectangle selectRect;
    public static Rectangle cancelRect;
    public static Rectangle infoBarRect;

    public static ArrayList<Rectangle> propertyRects = new ArrayList<>();
    public static Image play1;
    public static Image play2;
    public static Image play3;
    public static Image discard1;
    public static Image discard2;
    public static Image discard3;
    public static Image select1;
    public static Image select2;
    public static Image select3;
    public static Image cancel1;
    public static Image cancel2;
    public static Image cancel3;
    public static Image save1;
    public static Image save2;
    public static Image save3;
    private static int cardWidth;
    private static int cardHeight;
    private static int initialDrawPileX;
    private static int initialDrawPileY;
    private static Image desktop;
    private static Image cardBack;
    private static Image birthday;
    private static Image dealBreaker;
    private static Image debtCollector;
    private static Image doubleRent;
    private static Image forceDeal;
    private static Image hotel;
    private static Image house;
    private static Image justSayNo;
    private static Image passGo;
    private static Image slyDeal;
    private static Image M1;
    private static Image M2;
    private static Image M3;
    private static Image M4;
    private static Image M5;
    private static Image M10;
    private static Image darkBlue_green;
    private static Image utility_railRoad;
    private static Image lightBlue_brown;
    private static Image pink_orange;
    private static Image red_yellow;
    private static Image multiRent;
    private static Image brown1;
    private static Image brown_lightBlue;
    private static Image darkBlue1;
    private static Image green1;
    private static Image green_darkBlue;
    private static Image green_railRoad;
    private static Image lightBlue1;
    private static Image lightBlue_railRoad;
    private static Image multiProperty;
    private static Image orange1;
    private static Image pink1;
    private static Image orange_pink;
    private static Image railRoad1;
    private static Image railRoad_utility;
    private static Image red1;
    private static Image utility1;
    private static Image yellow1;
    private static Image yellow_red;
    private static Image brownProperty;
    private static Image darkBlueProperty;
    private static Image greenProperty;
    private static Image lightBlueProperty;
    private static Image orangeProperty;
    private static Image pinkProperty;
    private static Image railRoadProperty;
    private static Image redProperty;
    private static Image utilityProperty;
    private static Image yellowProperty;
    private static Image hotelProperty;
    private static Image houseProperty;
    private static ArrayList<Image> propertyImages = new ArrayList<>();
    private final MDCGame game;
    private Color color;

    public GameScreen(MDCGame game, Config config) throws IOException {
        this.game = game;
        screenWidth = MenuScreen.screenWidth;
        screenHeight = MenuScreen.screenHeight;
        cardWidth = screenWidth * 5 / 48;
        cardHeight = screenHeight * 13 / 48;
        initialDrawPileX = screenWidth * 21 / 48;
        initialDrawPileY = screenHeight * 14 / 48;
        backgroundRect = new Rectangle(0, 0, screenWidth, screenHeight);

        playRect = new Rectangle(screenWidth * 30 / 48, screenHeight * 23 / 48, screenWidth * 5 / 48, screenHeight * 3 / 48);
        discardRect = new Rectangle(screenWidth * 30 / 48, screenHeight * 23 / 48, screenWidth * 5 / 48, screenHeight * 3 / 48);
        saveRect = new Rectangle(screenWidth * 36 / 48, screenHeight * 23 / 48, screenWidth * 5 / 48, screenHeight * 3 / 48);
        selectRect = new Rectangle(screenWidth * 36 / 48, screenHeight * 23 / 48, screenWidth * 5 / 48, screenHeight * 3 / 48);
        cancelRect = new Rectangle(screenWidth * 42 / 48, screenHeight * 23 / 48, screenWidth * 5 / 48, screenHeight * 3 / 48);
        infoBarRect = new Rectangle(screenWidth * 30 / 48, screenHeight / 6, screenWidth * 16 / 48, screenHeight * 10 / 48);
        for (int i = 0; i < 10; i++) {
            int row = i / 5 + 1;
            int col = i % 5 + 1;
            Rectangle rect = new Rectangle(screenWidth - col * cardWidth * 4 / 5, screenHeight - row * cardHeight * 4 / 5, cardWidth * 4 / 5, cardHeight * 4 / 5);
            propertyRects.add(rect);
        }

        desktop = GraphPainter.getImage(config.getImagePath().getDesktop());
        cardBack = GraphPainter.getImage(config.getImagePath().getCardBack());

        play1 = GraphPainter.getImage(config.getImagePath().getPlay1());
        play2 = GraphPainter.getImage(config.getImagePath().getPlay2());
        play3 = GraphPainter.getImage(config.getImagePath().getPlay3());
        discard1 = GraphPainter.getImage(config.getImagePath().getDiscard1());
        discard2 = GraphPainter.getImage(config.getImagePath().getDiscard2());
        discard3 = GraphPainter.getImage(config.getImagePath().getDiscard3());
        select1 = GraphPainter.getImage(config.getImagePath().getSelect1());
        select2 = GraphPainter.getImage(config.getImagePath().getSelect2());
        select3 = GraphPainter.getImage(config.getImagePath().getSelect3());
        cancel1 = GraphPainter.getImage(config.getImagePath().getCancel1());
        cancel2 = GraphPainter.getImage(config.getImagePath().getCancel2());
        cancel3 = GraphPainter.getImage(config.getImagePath().getCancel3());
        save1 = GraphPainter.getImage(config.getImagePath().getSave1());
        save2 = GraphPainter.getImage(config.getImagePath().getSave2());
        save3 = GraphPainter.getImage(config.getImagePath().getSave3());

        birthday = GraphPainter.getImage(config.getImagePath().getBirthday());
        dealBreaker = GraphPainter.getImage(config.getImagePath().getDealBreaker());
        debtCollector = GraphPainter.getImage(config.getImagePath().getDebtCollector());
        doubleRent = GraphPainter.getImage(config.getImagePath().getDoubleRent());
        forceDeal = GraphPainter.getImage(config.getImagePath().getForceDeal());
        hotel = GraphPainter.getImage(config.getImagePath().getHotel());
        house = GraphPainter.getImage(config.getImagePath().getHouse());
        justSayNo = GraphPainter.getImage(config.getImagePath().getJustSayNo());
        passGo = GraphPainter.getImage(config.getImagePath().getPassGo());
        slyDeal = GraphPainter.getImage(config.getImagePath().getSlyDeal());

        M1 = GraphPainter.getImage(config.getImagePath().getM1());
        M2 = GraphPainter.getImage(config.getImagePath().getM2());
        M3 = GraphPainter.getImage(config.getImagePath().getM3());
        M4 = GraphPainter.getImage(config.getImagePath().getM4());
        M5 = GraphPainter.getImage(config.getImagePath().getM5());
        M10 = GraphPainter.getImage(config.getImagePath().getM10());

        darkBlue_green = GraphPainter.getImage(config.getImagePath().getDarkBlue_green());
        utility_railRoad = GraphPainter.getImage(config.getImagePath().getUtility_railRoad());
        lightBlue_brown = GraphPainter.getImage(config.getImagePath().getLightBlue_brown());
        pink_orange = GraphPainter.getImage(config.getImagePath().getPink_orange());
        red_yellow = GraphPainter.getImage(config.getImagePath().getRed_yellow());
        multiRent = GraphPainter.getImage(config.getImagePath().getMultiRent());

        brown1 = GraphPainter.getImage(config.getImagePath().getBrown1());
        brown_lightBlue = GraphPainter.getImage(config.getImagePath().getBrown_lightBlue());
        darkBlue1 = GraphPainter.getImage(config.getImagePath().getDarkBlue1());
        green1 = GraphPainter.getImage(config.getImagePath().getGreen1());
        green_darkBlue = GraphPainter.getImage(config.getImagePath().getGreen_darkBlue());
        green_railRoad = GraphPainter.getImage(config.getImagePath().getGreen_railRoad());
        lightBlue1 = GraphPainter.getImage(config.getImagePath().getLightBlue1());
        lightBlue_railRoad = GraphPainter.getImage(config.getImagePath().getLightBlue_railRoad());
        multiProperty = GraphPainter.getImage(config.getImagePath().getMultiProperty());
        orange1 = GraphPainter.getImage(config.getImagePath().getOrange1());
        pink1 = GraphPainter.getImage(config.getImagePath().getPink1());
        orange_pink = GraphPainter.getImage(config.getImagePath().getOrange_pink());
        railRoad1 = GraphPainter.getImage(config.getImagePath().getRailRoad1());
        railRoad_utility = GraphPainter.getImage(config.getImagePath().getRailRoad_utility());
        red1 = GraphPainter.getImage(config.getImagePath().getRed1());
        utility1 = GraphPainter.getImage(config.getImagePath().getUtility1());
        yellow1 = GraphPainter.getImage(config.getImagePath().getYellow1());
        yellow_red = GraphPainter.getImage(config.getImagePath().getYellow_red());

        brownProperty = GraphPainter.getImage(config.getImagePath().getBrownProperty());
        darkBlueProperty = GraphPainter.getImage(config.getImagePath().getDarkBlueProperty());
        greenProperty = GraphPainter.getImage(config.getImagePath().getGreenProperty());
        lightBlueProperty = GraphPainter.getImage(config.getImagePath().getLightBlueProperty());
        orangeProperty = GraphPainter.getImage(config.getImagePath().getOrangeProperty());
        pinkProperty = GraphPainter.getImage(config.getImagePath().getPinkProperty());
        railRoadProperty = GraphPainter.getImage(config.getImagePath().getRailRoadProperty());
        redProperty = GraphPainter.getImage(config.getImagePath().getRedProperty());
        utilityProperty = GraphPainter.getImage(config.getImagePath().getUtilityProperty());
        yellowProperty = GraphPainter.getImage(config.getImagePath().getYellowProperty());
        propertyImages.add(brownProperty);
        propertyImages.add(darkBlueProperty);
        propertyImages.add(greenProperty);
        propertyImages.add(lightBlueProperty);
        propertyImages.add(orangeProperty);
        propertyImages.add(pinkProperty);
        propertyImages.add(railRoadProperty);
        propertyImages.add(redProperty);
        propertyImages.add(utilityProperty);
        propertyImages.add(yellowProperty);
        hotelProperty = GraphPainter.getImage(config.getImagePath().getHotelProperty());
        houseProperty = GraphPainter.getImage(config.getImagePath().getHouseProperty());
    }

    private void drawInfoBar(Graphics2D g2d) {
        GameInfoBar gameInfoBar = game.getGameInfoBar();
        int messageHeight = infoBarRect.height / 6;
        int index = 0;
        for (String info : gameInfoBar) {
            int yPos = infoBarRect.y + infoBarRect.height - (index * messageHeight);
            // Create a new infoBarRect for this message
            Rectangle messageRect = new Rectangle(infoBarRect.x, yPos, infoBarRect.width, messageHeight);
            GraphPainter.drawString(g2d, info, "Courier New", 1, screenHeight / 40, Color.WHITE, 0, messageRect);
            index++;
        }
    }

    private void drawBackground(Graphics2D g2d) throws IOException {
        GraphPainter.drawImage(g2d, desktop, backgroundRect, false);
    }

    private void drawButton(Graphics2D g2d) throws IOException {
        for (Button button : game.getButtonsCopy()) {
            GraphPainter.drawImage(g2d, button.getImage(), button.getRect(), false);
        }
    }

    private void drawDrawPile(Graphics2D g2d) throws IOException {
        int num = Math.min(game.getDrawPileNum(), 10);
        for (int i = 0; i < num; i++) {
            Rectangle rect = new Rectangle(initialDrawPileX + 5 * i, initialDrawPileY, cardWidth, cardHeight);
            GraphPainter.drawImage(g2d, cardBack, rect, false);
        }
    }

    private void drawBankPile(Graphics2D g2d) throws IOException {
        ArrayList<AbstractCard> cards = game.getCurrBankPile().getCards();
        for (int i = 0; i < cards.size(); i++) {
            int addHeight = (i == game.getCurrBankCardIndex() && game.isSelected()) ? cardHeight / 2 : 0;
            boolean toBeSelected = (i == game.getCurrBankCardIndex() && addHeight == 0);
            Rectangle rect = new Rectangle((cardWidth / 5) * i, screenHeight / 3 - addHeight, cardWidth * 3 / 4, cardHeight * 3 / 4);
            AbstractCard card = cards.get(i);
            Image cardImage = null;
            int money = card.getTurnMoney();
            cardImage = switch (money) {
                case 1 -> M1;
                case 2 -> M2;
                case 3 -> M3;
                case 4 -> M4;
                case 5 -> M5;
                case 10 -> M10;
                default -> cardImage;
            };
            GraphPainter.drawImage(g2d, cardImage, rect, toBeSelected);
        }
    }

    private void drawCurrentPlayerPile(Graphics2D g2d) throws IOException {
        ArrayList<AbstractCard> cards = game.getCurrPlayerPile().getCards();
        for (int i = 0; i < cards.size(); i++) {
            int addHeight = (i == game.getCurrPlayerCardIndex() && game.isSelected() && game.getCurrPlayerPile() == game.getCurrPlayer().getOwnPlayerPile()) ? cardHeight * 2 / 3 : 0; // 当有卡牌被选择后，将牌举高高
            boolean toBeSelected = (i == game.getCurrPlayerCardIndex() && addHeight == 0 && game.getCurrPlayerPile() == game.getCurrPlayer().getOwnPlayerPile());
            Rectangle rect = new Rectangle((cardWidth / 3) * i, screenHeight - cardHeight - addHeight, cardWidth, cardHeight);
            ICard card = cards.get(i);
            Image cardImage = null;
            if (card instanceof RentCard || card instanceof PropertyCard) {
                String color = card.toString();
                cardImage = switch (color) {
                    case "darkBlue_green" -> darkBlue_green;
                    case "utility_railRoad" -> utility_railRoad;
                    case "lightBlue_brown" -> lightBlue_brown;
                    case "pink_orange" -> pink_orange;
                    case "red_yellow" -> red_yellow;
                    case "null_null" -> multiRent;

                    case "brown" -> brown1;
                    case "darkBlue" -> darkBlue1;
                    case "green" -> green1;
                    case "lightBlue" -> lightBlue1;
                    case "orange" -> orange1;
                    case "pink" -> pink1;
                    case "railRoad" -> railRoad1;
                    case "red" -> red1;
                    case "utility" -> utility1;
                    case "yellow" -> yellow1;
                    case "brown_lightBlue" -> brown_lightBlue;
                    case "green_darkBlue" -> green_darkBlue;
                    case "green_railRoad" -> green_railRoad;
                    case "lightBlue_railRoad" -> lightBlue_railRoad;
                    case "orange_pink" -> orange_pink;
                    case "railRoad_utility" -> railRoad_utility;
                    case "yellow_red" -> yellow_red;
                    case "null" -> multiProperty;
                    default -> cardImage;
                };
            } else if (card instanceof AbstractActionCard) {
                String className = card.getClass().getSimpleName();
                String cardName = className.substring(0, 1).toLowerCase() + className.substring(1);
                cardImage = switch (cardName) {
                    case "birthday" -> birthday;
                    case "dealBreaker" -> dealBreaker;
                    case "debtCollector" -> debtCollector;
                    case "doubleRent" -> doubleRent;
                    case "forceDeal" -> forceDeal;
                    case "hotel" -> hotel;
                    case "house" -> house;
                    case "justSayNo" -> justSayNo;
                    case "passGo" -> passGo;
                    case "slyDeal" -> slyDeal;
                    default -> cardImage;
                };
            } else if (card instanceof MoneyCard) {
                int money = ((MoneyCard) card).getTurnMoney();
                cardImage = switch (money) {
                    case 1 -> M1;
                    case 2 -> M2;
                    case 3 -> M3;
                    case 4 -> M4;
                    case 5 -> M5;
                    case 10 -> M10;
                    default -> cardImage;
                };
            } else {
                continue;
            }
            GraphPainter.drawImage(g2d, cardImage, rect, toBeSelected);
        }
    }

    private void drawCurrentPropertyPile(Graphics2D g2d) throws IOException {
        for (int i = 0; i < 10; i++) {
            boolean isSelected = (i == game.getCurrPropertyIndex());
            CardColor color = game.getColors().get(i);
            GraphPainter.drawImage(g2d, propertyImages.get(i), propertyRects.get(i), isSelected);
            if (color == game.getCurrPropertyPile().getHotelColor())
                GraphPainter.drawImage(g2d, hotelProperty, propertyRects.get(i), isSelected);
            else if (color == game.getCurrPropertyPile().getHouseColor())
                GraphPainter.drawImage(g2d, houseProperty, propertyRects.get(i), isSelected);
            GraphPainter.drawString(g2d, String.valueOf(game.getCurrPropertyPile().getSize(color)), "Courier New",
                    1, screenHeight / 32, Color.BLACK, 1, propertyRects.get(i));
        }
    }

    private void drawOpponentsInfo(Graphics2D g2d) throws IOException {
        Player[] players = game.getPlayers();
        int i = 0;
        for (Player player : players) {
            if (player != game.getCurrPlayer()) {
                // 打印不同状态的对象
                Color color;
                boolean toBeSelected = (i == game.getCurrOpponentIndex() && game.getCurrPhase() == GamePhases.playPhase);
                if (toBeSelected) color = Color.RED;
                else color = Color.WHITE;
                int realOpponentIndex = (game.currPlayerIndex + i + 1) % game.getPlayerNum();
                // 打印玩家名称
                GraphPainter.drawString(g2d, String.valueOf(realOpponentIndex),
                        "Courier New", 1, screenHeight / 32, color, 0,
                        new Rectangle(i * screenWidth / 4, 0, screenWidth / 4, screenHeight / 6));
                // 打印手牌数量
                GraphPainter.drawString(g2d, "Hand Cards: " + player.getOwnPlayerPile().size(),
                        "Courier New", 1, screenHeight / 32, Color.WHITE, 0,
                        new Rectangle(i * screenWidth / 4, screenHeight / 48, screenWidth / 4, screenHeight / 6));
                // 打印银行钱数
                GraphPainter.drawString(g2d, "Deposit: " + player.getOwnBank().getMoney(),
                        "Courier New", 1, screenHeight / 32, Color.WHITE, 0,
                        new Rectangle(i * screenWidth / 4, 2 * screenHeight / 48, screenWidth / 4, screenHeight / 6));
                // 打印房产数量
                GraphPainter.drawString(g2d, "Properties: ",
                        "Courier New", 1, screenHeight / 32, Color.WHITE, 0,
                        new Rectangle(i * screenWidth / 4, 3 * screenHeight / 48, screenWidth / 4, screenHeight / 6));
                int j = 1;
                Map<CardColor, Integer> propertySizes = player.getOwnProperty().getPropertySizes();
                for (Map.Entry<CardColor, Integer> ps : propertySizes.entrySet()) {
                    Color col = switch (ps.getKey()) {
                        case darkBlue -> Color.BLUE;
                        case brown -> new Color(139, 69, 19); // 棕色
                        case utility -> new Color(144, 238, 144); // 浅绿色
                        case green -> Color.GREEN;
                        case yellow -> Color.YELLOW;
                        case red -> Color.RED;
                        case orange -> Color.ORANGE;
                        case pink -> Color.PINK;
                        case lightBlue -> Color.CYAN; // 浅蓝色
                        case railRoad -> Color.BLACK; // 黑色
                        default -> Color.WHITE;
                    };
                    GraphPainter.drawString(g2d, ps.getValue().toString(),
                            "Courier New", 1, screenHeight / 32, col, 0,
                            new Rectangle(i * screenWidth / 4 + j * screenWidth / 96 + screenWidth / 9, 3 * screenHeight / 48, screenWidth / 4, screenHeight / 6));
                    j++;
                }
                i++;
            }
        }
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            drawBackground(g2d);
            drawInfoBar(g2d);
            drawButton(g2d);
            drawDrawPile(g2d);
            drawBankPile(g2d);
            drawCurrentPlayerPile(g2d);
            drawCurrentPropertyPile(g2d);
            drawOpponentsInfo(g2d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}