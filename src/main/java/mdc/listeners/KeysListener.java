package mdc.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;

/**
 * This class is used to control the keys that need to be pressed in the menu screen, about screen and score screen
 */
public class KeysListener implements KeysCommand, KeyListener {
    private boolean ifExit, ifEnter, ifUp, ifDown, ifRight, ifLeft, ifSpace;
    private boolean lastExit, lastEnter, lastUp, lastDown, lastRight, lastLeft, lastSpace;
    private Timer timer;

    public KeysListener() {
        timer = new Timer();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE -> ifExit = !lastExit;
            case KeyEvent.VK_ENTER -> ifEnter = !lastEnter;
            case KeyEvent.VK_UP -> schedulePress(() -> ifUp = true, () -> lastUp = false, () -> lastUp);
            case KeyEvent.VK_DOWN -> schedulePress(() -> ifDown = true, () -> lastDown = false, () -> lastDown);
            case KeyEvent.VK_RIGHT -> schedulePress(() -> ifRight = true, () -> lastRight = false, () -> lastRight);
            case KeyEvent.VK_LEFT -> schedulePress(() -> ifLeft = true, () -> lastLeft = false, () -> lastLeft);
            case KeyEvent.VK_SPACE -> schedulePress(() -> ifSpace = true, () -> lastSpace = false, () -> lastSpace);
        }
    }

    private void schedulePress(Runnable initialPress, Runnable repeatPress, Supplier<Boolean> lastKey) {
        if (!lastKey.get()) { // Check whether the X key has been pressed. If not, perform the following operations
            initialPress.run(); // If not pressed, set the current ifX to true (in subsequent havePressedX, confirm to the main program to press once, lastX will be set to true)
            long pressTime = System.currentTimeMillis();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - pressTime >= 300) { // 300 milliseconds after the first press,
                        repeatPress.run(); // Starts running lastX = false repeatedly, causing the listener to forget that X has already been pressed
                    }
                }
            }, 300, 200);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_ESCAPE -> {
                ifExit = false;
                lastExit = false;
            }
            case KeyEvent.VK_ENTER -> {
                ifEnter = false;
                lastEnter = false;
            }
            case KeyEvent.VK_UP -> {
                ifUp = false;
                lastUp = false;
                timer.cancel();
                timer = new Timer();
            }
            case KeyEvent.VK_DOWN -> {
                ifDown = false;
                lastDown = false;
                timer.cancel();
                timer = new Timer();
            }
            case KeyEvent.VK_RIGHT -> {
                ifRight = false;
                lastRight = false;
                timer.cancel();
                timer = new Timer();
            }
            case KeyEvent.VK_LEFT -> {
                ifLeft = false;
                lastLeft = false;
                timer.cancel();
                timer = new Timer();
            }
            case KeyEvent.VK_SPACE -> {
                ifSpace = false;
                lastSpace = false;
                timer.cancel();
                timer = new Timer();
            }
        }
    }

    @Override
    public boolean hasPressedEnter() {
        if (ifEnter && !lastEnter) {
            lastEnter = true; // Record that the key has been pressed, and subsequent ifEnter, even if it is true, will not tell the main program that it has been pressed
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPressedExit() {
        if (ifExit && !lastExit) {
            lastExit= true;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPressedUp() {
        if (ifUp && !lastUp) {
            lastUp = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPressedDown() {
        if (ifDown && !lastDown) {
            lastDown = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPressedRight() {
        if (ifRight && !lastRight) {
            lastRight = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPressedLeft() {
        if (ifLeft && !lastLeft) {
            lastLeft = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPressedSpace() {
        if (ifSpace && !lastSpace) {
            lastSpace = true;
            return true;
        }
        return false;
    }

    public boolean[] getBos() {
        return new boolean[]{hasPressedExit(), hasPressedEnter(), hasPressedUp(),
                hasPressedDown(), hasPressedRight(), hasPressedLeft(), hasPressedSpace()};
    }

    @Override
    public void resetKeyPresses() {
        ifExit = false;
        ifEnter = false;
        ifUp = false;
        ifDown = false;
        ifRight = false;
        ifLeft = false;
        ifSpace = false;
        lastExit = false;
        lastEnter = false;
        lastUp = false;
        lastDown = false;
        lastRight = false;
        lastLeft = false;
        lastSpace = false;
    }
}