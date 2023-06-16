package mdc;

import mdc.engine.GameEngine;
import mdc.listeners.KeysListener;
import mdc.listeners.MousesListener;
import mdc.screens.GameScreen;
import mdc.screens.MenuScreen;
import mdc.states.game.MDCGame;
import mdc.states.menu.MDCMenu;
import mdc.tools.Config;
import mdc.tools.ConfigLoader;
import mdc.tools.GraphPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * Initialize the game window and start the game
 */
public class ApplicationStart {
    public static void main(String[] args) throws IOException {
        Config config = ConfigLoader.loadConfig("src/main/resources/config/config.json");
        assert config != null;

        // some setups
        JFrame window = new JFrame("Monopoly Deal Cards");
        window.setIconImage(GraphPainter.getImage(config.getImagePath().getIcon()));

        // create listeners
        KeysListener keysListener = new KeysListener();
        MousesListener mousesListener = new MousesListener();

        // Add listener
        window.setFocusable(true);
        window.addKeyListener(keysListener);
        window.addMouseListener(mousesListener);
        window.addMouseMotionListener(mousesListener);

        // Initialize the window and engine
        MDCGame game = new MDCGame(keysListener, mousesListener, config);
        MDCMenu menu = new MDCMenu(keysListener, mousesListener, config);
        MenuScreen menuScreen = new MenuScreen(menu, config);
        GameScreen gameScreen = new GameScreen(game, config);
        GameEngine engine = new GameEngine(game, menu, window, menuScreen, gameScreen);

        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Cancel the close key to exit the program directly
        window.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                engine.exitGame();
            }
        });
        // Gets the current screen resolution
        Insets insets = window.getInsets();
        window.setSize(GameScreen.screenWidth + insets.left + insets.right,
                GameScreen.screenHeight + insets.top + insets.bottom);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        // start the game engine
        engine.run();
    }
}
