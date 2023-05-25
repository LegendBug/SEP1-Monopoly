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
    private boolean ifExit, ifEnter, ifUp, ifDown, ifRight, ifLeft;
    private boolean lastExit, lastEnter, lastUp, lastDown, lastRight, lastLeft;
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
        }
    }

    private void schedulePress(Runnable initialPress, Runnable repeatPress, Supplier<Boolean> lastKey) {
        if (!lastKey.get()) { // 检测是否已按过X键，若未按过，执行下面的操作
            initialPress.run(); // 未按过，将当前ifX设为true（在后续的havePressedX中，给主程序确认按下一次，lastX会被设置为true）
            long pressTime = System.currentTimeMillis();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (System.currentTimeMillis() - pressTime >= 300) { // 第一次按下的300毫秒后，
                        repeatPress.run(); // 开始重复运行lastX = false，使得listener忘记已经按过X
                    }
                }
            }, 300, 200); // TODO 可更改响应时间
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
        }
    }

    @Override
    public boolean hasPressedEnter() {
        if (ifEnter && !lastEnter) {
            lastEnter = true; // 记录已按下该键，后续ifEnter即使为true也无法告知主程序已按下
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
    public void resetKeyPresses() {
        ifExit = false;
        ifEnter = false;
        ifUp = false;
        ifDown = false;
        ifRight = false;
        ifLeft = false;
        lastExit = false;
        lastEnter = false;
        lastUp = false;
        lastDown = false;
        lastRight = false;
        lastLeft = false;
    }
}
