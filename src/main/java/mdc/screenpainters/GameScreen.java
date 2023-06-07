package mdc.screenpainters;

import mdc.states.ButtonStates;
import mdc.states.game.GameButtons;
import mdc.states.game.MDCGame;
import mdc.tools.Config;
import mdc.tools.GraphPainter;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to draw components on game screen
 */
public class GameScreen extends JPanel {
    // TODO public和private
    public static int screenWidth;
    public static int screenHeight;
    public static Rectangle backgroundRect;
    public static Rectangle playRect;
    public static Rectangle saveRect;
    public static Rectangle discardRect;
    public static Rectangle selectRect;
    public static Rectangle cancelRect;
    public static ArrayList<Rectangle> propertyRects = new ArrayList<>();
    public static ArrayList<Rectangle> opponentRects = new ArrayList<>();
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
    private static int initialBankPileX;
    private static int initialBankPileY;
    private static int initialOpponentsPileX;
    private static int initialOpponentsPileY;
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
    }

    private void drawInfo(Graphics2D g2d) {
        GraphPainter.drawString(g2d, "player:" + game.getCurrPlayer(), "Courier New", 1, screenHeight / 32, Color.WHITE, 0,
                new Rectangle(screenWidth * 20 / 48, screenHeight * 1 / 48, 200, 100));
        GraphPainter.drawString(g2d, "player:" + game.currPlayer, "Courier New", 1, screenHeight / 32, Color.WHITE, 0,
                new Rectangle(screenWidth * 20 / 48, screenHeight * 2 / 48, 200, 100));
        GraphPainter.drawString(g2d, "phase:" + game.getCurrPhase(), "Courier New", 1, screenHeight / 32, Color.WHITE, 0,
                new Rectangle(screenWidth * 20 / 48, screenHeight * 3 / 48, 200, 100));
        GraphPainter.drawString(g2d, "draw pile:" + game.getDrawPileNum(), "Courier New", 1, screenHeight / 32, Color.WHITE, 0,
                new Rectangle(screenWidth * 20 / 48, screenHeight * 4 / 48, 200, 100));
        GraphPainter.drawString(g2d, "curr card:" + game.getCurrCard(), "Courier New", 1, screenHeight / 32, Color.WHITE, 0,
                new Rectangle(screenWidth * 20 / 48, screenHeight * 5 / 48, 200, 100));
        GraphPainter.drawString(g2d, "curr cards:" + game.getCurrPlayer().getOwnPlayerPile().size(), "Courier New", 1, screenHeight / 32, Color.WHITE, 0,
                new Rectangle(screenWidth * 20 / 48, screenHeight * 6 / 48, 200, 100));
    }


    private void drawBackground(Graphics2D g2d) throws IOException {
        GraphPainter.drawImage(g2d, desktop, backgroundRect, false);
    }

    private void drawButtonState(Graphics2D g2d, GameButtons button, Rectangle rect, Image image1, Image image2, ButtonStates state) throws IOException {
        switch (state) {
            case HOVER -> {
                if (game.getGameButton() == button)
                    GraphPainter.drawImage(g2d, image1, rect, false);
            }
            case PRESSED -> {
                if (game.getGameButton() == button)
                    GraphPainter.drawImage(g2d, image2, rect, false);
            }
        }
    }

//    private void drawButton(Graphics2D g2d) throws  IOException {
//        for (Button button : game.getButtonsCopy()) {
//            GraphPainter.drawImage(g2d, button.getImage(), button.getRect(), false);
//        }
//    }


//    private void drawDrawPile(Graphics2D g2d) throws IOException {
//        int num = Math.min(game.getDrawPileNum(), 10);
//        for (int i = 0; i < num; i++) {
//            Rectangle rect = new Rectangle(INITIAL_DRAW_PILE_X + 5 * i, INITIAL_DRAW_PILE_Y, CARD_WIDTH, CARD_HEIGHT);
//            GraphPainter.drawImage(g2d, cardBack, rect, false);
//        }
//    }

    private void drawDiscardPile(Graphics2D g2d) throws IOException {
        // TODO 画弃牌堆
    }

//    private void drawCurrentPlayerPile(Graphics2D g2d) throws IOException {
//        Player currPlayer = game.getCurrPlayer();
//        List<ICard> cards = currPlayer.getOwnPlayerPile().getCards();
//        for (int i = 0; i < cards.size(); i++) {
//            int addHeight = (i == game.getCurrCard() && game.isSelected()) ? CARD_HEIGHT * 2 / 3 : 0;
//            boolean toBeSelected = (i == game.getCurrCard() && addHeight == 0);
//            Rectangle rect = new Rectangle((CARD_WIDTH / 3) * i, SCREEN_HEIGHT - CARD_HEIGHT - addHeight, CARD_WIDTH, CARD_HEIGHT);
//            ICard card = cards.get(i);
//            Image cardImage;
//            if (card instanceof RentCard) {
//                cardImage = actionCard;
//            } else if (card instanceof AbstractActionCard) {
//                cardImage = actionCard;
//            } else if (card instanceof MoneyCard) {
//                cardImage = moneyCard;
//            } else if (card instanceof AbstractPropertyCard) {
//                cardImage = propertyCard;
//            } else {
//                continue;
//            }
//            GraphPainter.drawImage(g2d, cardImage, rect, toBeSelected);
//        }
//    }


    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            drawBackground(g2d);
            drawInfo(g2d);
//            drawButton(g2d);
//            drawDrawPile(g2d);
//            drawCurrentPlayerPile(g2d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
