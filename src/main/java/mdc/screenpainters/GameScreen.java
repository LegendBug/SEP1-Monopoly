package mdc.screenpainters;

import mdc.components.cards.ICard;
import mdc.components.cards.actioncards.AbstractActionCard;
import mdc.components.cards.actioncards.RentCard;
import mdc.components.cards.moneycards.MoneyCard;
import mdc.components.cards.properties.AbstractPropertyCard;
import mdc.components.players.Player;
import mdc.states.ButtonStates;
import mdc.states.game.GameButtons;
import mdc.states.game.GamePhases;
import mdc.tools.Config;
import mdc.tools.GraphPainter;
import mdc.states.game.MDCGame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * This class is used to draw components on game screen
 */
public class GameScreen extends JPanel {

    public static final int SCREEN_WIDTH = MenuScreen.SCREEN_WIDTH;
    public static final int SCREEN_HEIGHT = MenuScreen.SCREEN_HEIGHT;
    private static final int CARD_WIDTH = SCREEN_WIDTH * 5 / 48;
    private static final int CARD_HEIGHT = SCREEN_HEIGHT * 13 / 48;
    private static final int INITIAL_DRAW_PILE_X = 0;
    private static final int INITIAL_DRAW_PILE_Y = SCREEN_HEIGHT * 18 / 48 - 1;
    public static final Rectangle BACKGROUND_RECT = new Rectangle(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
    public static final Rectangle PLAY_RECT = new Rectangle(SCREEN_WIDTH  * 17 / 48, SCREEN_HEIGHT * 32 / 48, SCREEN_WIDTH  * 5 / 48, SCREEN_HEIGHT * 3 / 48);
    public static final Rectangle DISCARD_RECT = new Rectangle(SCREEN_WIDTH  * 17 / 48, SCREEN_HEIGHT * 32 / 48, SCREEN_WIDTH  * 5 / 48, SCREEN_HEIGHT * 3 / 48);
    public static final Rectangle SELECT_RECT = new Rectangle(SCREEN_WIDTH  * 21 / 48, SCREEN_HEIGHT * 32 / 48, SCREEN_WIDTH  * 5 / 48, SCREEN_HEIGHT * 3 / 48);
    public static final Rectangle CANCEL_RECT = new Rectangle(SCREEN_WIDTH  * 26 / 48, SCREEN_HEIGHT * 32 / 48, SCREEN_WIDTH  * 5 / 48, SCREEN_HEIGHT * 3 / 48);

    private final MDCGame game;
    public final Image desktop;
    public final Image actionCard;
    public final Image moneyCard;
    public final Image propertyCard;
    public final Image cardBack;
    public final Image play1;
    public final Image play2;
    public final Image play3;
    public final Image discard1;
    public final Image discard2;
    public final Image discard3;
    public final Image select1;
    public final Image select2;
    public final Image select3;
    public final Image cancel1;
    public final Image cancel2;
    public final Image cancel3;

    private Color color;

    public GameScreen(MDCGame game, Config config) throws IOException {
        this.game = game;
        desktop = GraphPainter.getImage(config.getImagePath().getDesktop());
        actionCard = GraphPainter.getImage(config.getImagePath().getActionCard());
        moneyCard = GraphPainter.getImage(config.getImagePath().getMoneyCard());
        propertyCard = GraphPainter.getImage(config.getImagePath().getPropertyCard());
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
    }

    private void drawInfo(Graphics2D g2d) {
        GraphPainter.drawString(g2d, "player:" + game.currPlayer, "Courier New", 1, SCREEN_HEIGHT / 32, Color.WHITE, 0,
                new Rectangle(SCREEN_WIDTH * 40 / 48, SCREEN_HEIGHT * 20 / 48, 200, 100));
        GraphPainter.drawString(g2d, "phase:" + game.getCurrPhase(), "Courier New", 1, SCREEN_HEIGHT / 32, Color.WHITE, 0,
                new Rectangle(SCREEN_WIDTH * 40 / 48, SCREEN_HEIGHT * 21 / 48, 200, 100));
        GraphPainter.drawString(g2d, "draw pile:" + game.getDrawPileNum(), "Courier New", 1, SCREEN_HEIGHT / 32, Color.WHITE, 0,
                new Rectangle(SCREEN_WIDTH * 40 / 48, SCREEN_HEIGHT * 22 / 48, 200, 100));
        GraphPainter.drawString(g2d, "curr card:" + game.getCurrCard(), "Courier New", 1, SCREEN_HEIGHT / 32, Color.WHITE, 0,
                new Rectangle(SCREEN_WIDTH * 40 / 48, SCREEN_HEIGHT * 23 / 48, 200, 100));
        GraphPainter.drawString(g2d, "curr cards:" + game.getCurrPlayer().getOwnPlayerPile().size(), "Courier New", 1, SCREEN_HEIGHT / 32, Color.WHITE, 0,
                new Rectangle(SCREEN_WIDTH * 40 / 48, SCREEN_HEIGHT * 24 / 48, 200, 100));
    }



    private void drawBackground(Graphics2D g2d) throws IOException {
        GraphPainter.drawImage(g2d, desktop, BACKGROUND_RECT, false);
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

    private void drawButton(Graphics2D g2d) throws  IOException {
        GamePhases phase = game.getCurrPhase();
        ButtonStates state = game.getButtonState();
        if (game.getCurrCard() != -1) {
            switch (phase) {
                case playCards -> {
                    GraphPainter.drawImage(g2d, play1, PLAY_RECT, false);
                    GraphPainter.drawImage(g2d, cancel1, CANCEL_RECT, false);
                    drawButtonState(g2d, GameButtons.PLAY, PLAY_RECT, play2, play3, state);
                    drawButtonState(g2d, GameButtons.CANCEL, CANCEL_RECT, cancel2, cancel3, state);
                }
                case discardCards -> {
                    GraphPainter.drawImage(g2d, discard1, DISCARD_RECT, false);
                    GraphPainter.drawImage(g2d, cancel1, CANCEL_RECT, false);
                    drawButtonState(g2d, GameButtons.DISCARD, DISCARD_RECT, discard2, discard3, state);
                    drawButtonState(g2d, GameButtons.CANCEL, CANCEL_RECT, cancel2, cancel3, state);
                }
                case selectCards -> {
                    GraphPainter.drawImage(g2d, select1, SELECT_RECT, false);
                    drawButtonState(g2d, GameButtons.SELECT, SELECT_RECT, select2, select3, state);
                }
            }
        }
    }


    private void drawDrawPile(Graphics2D g2d) throws IOException {
        int num = Math.min(game.getDrawPileNum(), 10);
        for (int i = 0; i < num; i++) {
            Rectangle rect = new Rectangle(INITIAL_DRAW_PILE_X + 5 * i, INITIAL_DRAW_PILE_Y, CARD_WIDTH, CARD_HEIGHT);
            GraphPainter.drawImage(g2d, cardBack, rect, false);
        }
    }

    private void drawDiscardPile(Graphics2D g2d) throws IOException {
        // TODO 画弃牌堆
    }

    private void drawCurrentPlayerPile(Graphics2D g2d) throws IOException {
        Player currPlayer = game.getCurrPlayer();
        List<ICard> cards = currPlayer.getOwnPlayerPile().getCards();
        for (int i = 0; i < cards.size(); i++) {
            int addHeight = (i == game.getCurrCard() && game.isSelected()) ? CARD_HEIGHT * 2 / 3 : 0;
            boolean toBeSelected = (i == game.getCurrCard() && addHeight == 0);
            Rectangle rect = new Rectangle((CARD_WIDTH / 3) * i, SCREEN_HEIGHT - CARD_HEIGHT - addHeight, CARD_WIDTH, CARD_HEIGHT);
            ICard card = cards.get(i);
            Image cardImage;
            if (card instanceof RentCard) {
                cardImage = actionCard;
            } else if (card instanceof AbstractActionCard) {
                cardImage = actionCard;
            } else if (card instanceof MoneyCard) {
                cardImage = moneyCard;
            } else if (card instanceof AbstractPropertyCard) {
                cardImage = propertyCard;
            } else {
                continue;
            }
            GraphPainter.drawImage(g2d, cardImage, rect, toBeSelected);
        }
    }



    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        try {
            drawBackground(g2d);
            drawInfo(g2d);
            drawButton(g2d);
            drawDrawPile(g2d);
            drawCurrentPlayerPile(g2d);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
