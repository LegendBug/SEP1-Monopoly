package mdc.states.game;

import mdc.listeners.KeysListener;
import mdc.listeners.MousesListener;
import mdc.models.Entities.Ghost;
import mdc.models.Entities.PacMan;
import mdc.models.Level;
import mdc.states.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MDCGame implements State, Game {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;
    private static final int LEVELS = 14;
    private final KeysListener keysListener;
    private final MousesListener mousesListener;
    private int playerScore;
    private int playerLives;
    /**
     * Calculate whether the player reaches 10000 points, which is used to add lives
     */
    private int scoreCounter;
    private int currentLevel;
    /**
     * Calculate the running time of the current level to judge whether the fruit appears
     */
    private int currentTime;
    /**
     * A graph containing all points and paths between points with ghost recognition
     */
    /**
     * Player controlled Pac-Man
     */
    public PacMan pacMan;
    /**
     * List of ghosts
     */
    public List<Ghost> ghosts;
    /**
     * An array containing all levels
     */
    private Level[] levels;
    /**
     * List of current coordinates of ghosts
     */
    private List<int[]> ghostsPos;
    /**
     * List of adjacent coordinates to which each ghost is going
     */
    private List<int[]> targetsPos;
    /**
     * List of player coordinates (finishing point) to be reached by each ghost
     */
    private List<int[]> pacMansPos;
    /**
     * List of normal walls and walls that only ghosts can pass through
     */
    private List<Rectangle> wallsForPlayer;
    /**
     * List of recognizable path points of ghosts
     */
    private List<int[]> roadsForGhosts;
    private boolean ifPause;

    public MDCGame(KeysListener keysListener, MousesListener mousesListener) {
        this.keysListener = keysListener;
        this.mousesListener = mousesListener;
        startState();
    }

    @Override
    public void startState() {
        ifPause = true;
        playerLives = 3;
        playerScore = 0;
        currentLevel = 0;
        scoreCounter = 0;
        levels = new Level[LEVELS];
        levels[0] = new Level(1.00, 1, 20);
        levels[1] = new Level(1.05, 2, 20);
        levels[2] = new Level(1.10, 3, 20);
        levels[3] = new Level(1.15, 4, 20);
        levels[4] = new Level(1.20, 4, 18);
        levels[5] = new Level(1.25, 4, 16);
        levels[6] = new Level(1.30, 4, 14);
        levels[7] = new Level(1.35, 4, 12);
        levels[8] = new Level(1.40, 4, 10);
        levels[9] = new Level(1.45, 4, 8);
        levels[10] = new Level(1.50, 4, 6);
        levels[11] = new Level(1.55, 4, 5);
        levels[12] = new Level(1.60, 4, 5);
        levels[13] = new Level(1.60, 4, 5);
        pacMan = levels[currentLevel].pacMan;
        ghosts = levels[currentLevel].ghosts;
        ghostsPos = new ArrayList<>(Arrays.asList(new int[2], new int[2], new int[2], new int[2]));
        targetsPos = new ArrayList<>(Arrays.asList(new int[2], new int[2], new int[2], new int[2]));
        pacMansPos = new ArrayList<>(Arrays.asList(new int[2], new int[2], new int[2], new int[2]));
        currentTime = 0;
        getWallsForPlayer();
        getRoadsForGhosts();
    }

    @Override
    public void updateState() {
        if (!isPaused()) {
            currentTime++;
            checkForSkip();
            checkForScore();
            setResurrection();
            setPowerful();
            setRandom();
            setEaten();
            movePacMan();
            moveGhosts();
            eatDot();
            eatFruit();
            eatPowerPellet();
            eatPacMan();
            eatGhosts();
        }
    }

    @Override
    public int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    @Override
    public int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    @Override
    public void buttonController() {

    }

    @Override
    public int getPlayerScore() {
        return playerScore;
    }

    @Override
    public int getLives() {
        return playerLives;
    }

    public int getCurrentLevel() {
        return currentLevel + 1;
    }

    // The following methods are used to obtain each component rectangle
    public List<Rectangle> getNormalWallsRect() {
        return levels[currentLevel].getNormalWallsRect();
    }

    public List<Rectangle> getGhostWallsRect() {
        return levels[currentLevel].getGhostWallsRect();
    }

    public List<Rectangle> getDotsRect() {
        return levels[currentLevel].getDotsRect();
    }

    public List<Rectangle> getFruitsRect() {
        if (currentTime > 500 && currentTime < 2000) {
            return levels[currentLevel].getFruitsRect();
        }
        return null;
    }

    public List<Rectangle> getPowerPelletsRect() {
        return levels[currentLevel].getPowerPelletsRect();
    }

    public Rectangle getPacManGraphicRect() {
        return pacMan.getGraphicRect();
    }

    public List<Rectangle> getGhostsGraphicRect() {
        List<Rectangle> graphicRects = new ArrayList<>();
        for (Ghost ghost : ghosts) {
            graphicRects.add(ghost.getGraphicRect());
        }
        return graphicRects;
    }

    private List<Rectangle> getGhostsHitboxRect() {
        List<Rectangle> ghostsRect = new ArrayList<>();
        for (Ghost ghost : ghosts) {
            ghostsRect.add(ghost.getHitBox());
        }
        return ghostsRect;
    }

    private void getWallsForPlayer() {
        wallsForPlayer = getNormalWallsRect();
        wallsForPlayer.addAll(getGhostWallsRect());
    }

    private void getRoadsForGhosts() {
        roadsForGhosts = levels[currentLevel].getRoadsPos();
        map = new Graph(roadsForGhosts);
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
        return ifPause;
    }

    @Override
    public boolean isPlayerAlive() {
        return pacMan.isAlive();
    }

    @Override
    public boolean isLevelFinished() {
        if (currentLevel < LEVELS) {
            return levels[currentLevel].dots.size() == 0 &&
                    levels[currentLevel].powerPellets.size() == 0;
        } else {
            return true;
        }
    }

    @Override
    public boolean isStateOver() {
        if (!(playerLives > 0)) {
            return true;
        } else return currentLevel == LEVELS - 1 && isLevelFinished();
    }

    @Override
    public void checkForPause() {
        if (keysListener.hasPressedExit()) {
            ifPause = !ifPause;
        }
    }

    /**
     * This method is used to skip the current level for testing
     */
    private void checkForSkip() {
        if (keysListener.hasPressedEnter()) {
            if (currentLevel < LEVELS - 1) {
                moveToNextLevel();
            } else {
                playerLives = 0;
            }
        }
    }

    /**
     * This method is used to determine whether the current score is a multiple of 10000 and add player lives
     */
    private void checkForScore() {
        if (scoreCounter >= 10000) {
            scoreCounter = playerScore % 10000;
            playerLives++;
        }
    }

    @Override
    public void resetDestroyedPlayer() {
        levels[currentLevel].resetPacMan();
        levels[currentLevel].resetGhosts();
        pacMan = levels[currentLevel].pacMan;
        ghosts = levels[currentLevel].ghosts;
        ghostsPos = new ArrayList<>(Arrays.asList(new int[2], new int[2], new int[2], new int[2]));
        targetsPos = new ArrayList<>(Arrays.asList(new int[2], new int[2], new int[2], new int[2]));
        pacMansPos = new ArrayList<>(Arrays.asList(new int[2], new int[2], new int[2], new int[2]));
    }

    @Override
    public void moveToNextLevel() {
        ifPause = true;
        if (currentLevel < LEVELS - 1) {
            currentLevel++;
            currentTime = 0;
        }
        resetDestroyedPlayer();
    }

    @Override
    public void moveToOtherStates() {
        keysListener.resetKeyPresses();
        mousesListener.resetMousePressed();
    }

    /**
     * This method control the player's movement. When the player presses the direction key, judge whether there is a wall in front of it, and if not, move forward.
     */
    public void movePacMan() {
        if (keysListener.hasPressedUp()) {
            Rectangle newRect = new Rectangle(pacMan.getX(), pacMan.getY() - 2, 40, 40);
            if (getIntersecting(newRect, wallsForPlayer) == -1) {
                pacMan.move(0, -4);
            }
        }
        if (keysListener.hasPressedDown()) {
            Rectangle newRect = new Rectangle(pacMan.getX(), pacMan.getY() + 2, 40, 40);
            if (getIntersecting(newRect, wallsForPlayer) == -1) {
                pacMan.move(0, 4);
            }
        }
        if (keysListener.hasPressedRight()) {
            Rectangle newRect = new Rectangle(pacMan.getX() + 2, pacMan.getY(), 40, 40);
            if (getIntersecting(newRect, wallsForPlayer) == -1) {
                pacMan.move(4, 0);
            }
        }
        if (keysListener.hasPressedLeft()) {
            Rectangle newRect = new Rectangle(pacMan.getX() - 2, pacMan.getY(), 40, 40);
            if (getIntersecting(newRect, wallsForPlayer) == -1) {
                pacMan.move(-4, 0);
            }
        }

    }

    /**
     * This method control the ghosts' movement.
     *
     * <p>Since the moving distance of ghost is not an integer, forced movement will be performed here. When the coordinates of the ghost's upper left corner are within the range of four pixels in the upper left corner of its block, it is judged that it has reached the point, and the next adjacent point is obtained at the same time.
     *
     * @param ghostIndex The index of the currently controlled ghost
     * @param targetPos  The next adjacent point of the currently controlled ghost
     */
    public void moveGhost(int ghostIndex, int[] targetPos) {
        // Judge whether to reach the target point (i.e. the coordinates of the upper left corner of the block)
        if (targetPos[0] * 40 < ghosts.get(ghostIndex).getX() + 4 && targetPos[0] * 40 > ghosts.get(ghostIndex).getX() - 4 &&
                targetPos[1] * 40 < ghosts.get(ghostIndex).getY() + 4 && targetPos[1] * 40 > ghosts.get(ghostIndex).getY() - 4) {
            int[] initialPos = ghosts.get(ghostIndex).INITIAL_POS.get(0);
            // If the point the ghost arrives at is the rebirth point, cancel all its negative states
            if (Arrays.equals(targetPos, initialPos) && Arrays.equals(pacMansPos.get(ghostIndex), initialPos)) {
                ghosts.get(ghostIndex).ifEaten = false;
                ghosts.get(ghostIndex).ifPowerless = false;
                ghosts.get(ghostIndex).powerlessTime = 0;
            } else {
                ghosts.get(ghostIndex).x = targetPos[0] * 40; // Forced moving coordinate
                ghosts.get(ghostIndex).y = targetPos[1] * 40; // Forced moving coordinate
                // Select a new neighboring coordinate, and keep moving
                targetsPos.set(ghostIndex, getTargetsPos(ghostIndex, pacMansPos.get(ghostIndex)));
                if (!Arrays.equals(targetsPos.get(ghostIndex), pacMansPos.get(ghostIndex))) {
                    moveGhost(ghostIndex, targetsPos.get(ghostIndex));
                }
            }
            // If not, move towards the target point
        } else if (targetPos[0] == ghosts.get(ghostIndex).getX() / 40.0 && targetPos[1] * 40 < ghosts.get(ghostIndex).y) {
            ghosts.get(ghostIndex).move(0, -levels[currentLevel].ghostsSpeed);
        } else if (targetPos[0] == ghosts.get(ghostIndex).getX() / 40.0 && targetPos[1] * 40 > ghosts.get(ghostIndex).y) {
            ghosts.get(ghostIndex).move(0, levels[currentLevel].ghostsSpeed);
        } else if (targetPos[0] * 40 > ghosts.get(ghostIndex).x && targetPos[1] == ghosts.get(ghostIndex).getY() / 40.0) {
            ghosts.get(ghostIndex).move(levels[currentLevel].ghostsSpeed, 0);
        } else if (targetPos[0] * 40 < ghosts.get(ghostIndex).x && targetPos[1] == ghosts.get(ghostIndex).getY() / 40.0) {
            ghosts.get(ghostIndex).move(-levels[currentLevel].ghostsSpeed, 0);
        }
    }

    /**
     * The shortest path is obtained through breadth first search algorithm, and the next nearest point (target point) from the current coordinate is obtained
     */
    public int[] getTargetsPos(int ghostIndex, int[] pacManPos) {
        int[] ghostPos = new int[2];
        ghostPos[0] = ghosts.get(ghostIndex).getX() / 40 + ghosts.get(ghostIndex).getX() / 20 % 2;
        ghostPos[1] = ghosts.get(ghostIndex).getY() / 40 + ghosts.get(ghostIndex).getY() / 20 % 2;
        ghostsPos.set(ghostIndex, ghostPos);
        int s = 0, w = 0; // Index of current point and target location
        for (int i = 0; i < roadsForGhosts.size(); i++) { // get the index of current point
            if (Arrays.equals(roadsForGhosts.get(i), ghostsPos.get(ghostIndex))) {
                s = i;
                break;
            }
        }
        for (int i = 0; i < roadsForGhosts.size(); i++) {
            if (Arrays.equals(roadsForGhosts.get(i), pacManPos)) {
                w = i;
                break;
            }
        }
        BreadthFirstPaths bfs = new BreadthFirstPaths(map, s); // Breadth First Search
        return roadsForGhosts.get(bfs.getNextPoint(w));
    }

    /**
     * Action mode when ghost chasing player. At this time, the finishing point of the ghost is player's point.
     */
    public void chaseMode(int ghostIndex) {
        int[] pacManPos = new int[2];
        pacManPos[0] = pacMan.getX() / 40 + pacMan.getX() / 20 % 2;
        pacManPos[1] = pacMan.getY() / 40 + pacMan.getY() / 20 % 2;
        pacMansPos.set(ghostIndex, pacManPos); // 写入
        if (Arrays.equals(new int[]{0, 0}, targetsPos.get(ghostIndex))) {
            targetsPos.set(ghostIndex, getTargetsPos(ghostIndex, pacMansPos.get(ghostIndex))); // 写入
        }
        moveGhost(ghostIndex, targetsPos.get(ghostIndex));
    }

    /**
     * Random action mode. At this time, the finishing point of the ghost is random.
     */
    public void randomMode(int ghostIndex) {
        if (ghosts.get(ghostIndex).ifRandom) {
            ghosts.get(ghostIndex).ifRandom = false;
            pacMansPos.set(ghostIndex, roadsForGhosts.get((int) (Math.random() * roadsForGhosts.size())));
            if (Arrays.equals(new int[]{0, 0}, targetsPos.get(ghostIndex))) {
                targetsPos.set(ghostIndex, getTargetsPos(ghostIndex, pacMansPos.get(ghostIndex))); // 写入
            }
        } else {
            if (Arrays.equals(targetsPos.get(ghostIndex), pacMansPos.get(ghostIndex))) {
                pacMansPos.set(ghostIndex, roadsForGhosts.get((int) (Math.random() * roadsForGhosts.size())));
            }
        }
        moveGhost(ghostIndex, targetsPos.get(ghostIndex));
    }

    /**
     * Action mode when ghosts was eaten players. At this time, the finishing point of the ghost is its rebirth point
     */
    public void eatenMode(int ghostIndex) {
        pacMansPos.set(ghostIndex, ghosts.get(ghostIndex).INITIAL_POS.get(0));
        moveGhost(ghostIndex, targetsPos.get(ghostIndex));
    }

    /**
     * General control of ghost movement
     */
    public void moveGhosts() {
        for (int i = 0; i < ghosts.size(); i++) {
            Ghost ghost = ghosts.get(i);
            if (ghost.ifResurrection) {
                if (ghost.ifPowerless) {
                    if (ghost.ifEaten) {
                        eatenMode(i);
                    } else {
                        randomMode(i); // 等Powerless自然结束
                    }
                } else if (i == 0) {
                    chaseMode(i);
                } else {
                    randomMode(i);
                }
            }
        }
    }

    // The following methods are related to eating
    public void eatPacMan() {
        int ghostIndex = getIntersecting(pacMan.getHitBox(), this.getGhostsHitboxRect());
        if (ghostIndex != -1) {
            if (!ghosts.get(ghostIndex).ifPowerless && !ghosts.get(ghostIndex).ifEaten) {
                playerLives--;
                if (playerLives > 0) {
                    resetDestroyedPlayer();
                }
                ifPause = true;
            }
        }
    }

    public void eatGhosts() {
        for (Ghost ghost : ghosts) {
            if (pacMan.ifPowerful && ghost.ifPowerless) {
                int ghostIndex = getIntersecting(pacMan.getHitBox(), this.getGhostsHitboxRect());
                if (ghostIndex != -1 && !ghosts.get(ghostIndex).ifEaten) {
                    ghosts.get(ghostIndex).ifEaten = true;
                    pacMan.eatenNumber++;
                    switch (pacMan.eatenNumber) {
                        case 1 -> {
                            playerScore += 200;
                            scoreCounter += 200;
                        }
                        case 2 -> {
                            playerScore += 400;
                            scoreCounter += 400;
                        }
                        case 3 -> {
                            playerScore += 800;
                            scoreCounter += 800;
                        }
                        case 4 -> {
                            playerScore += 1600;
                            scoreCounter += 1600;
                        }
                    }
                }
            }
        }
    }

    private void eatDot() {
        int dotIndex = getIntersecting(pacMan.getHitBox(), this.getDotsRect());
        if (dotIndex != -1) {
            playerScore += 10;
            scoreCounter += 10;
            levels[currentLevel].removeDot(dotIndex);
        }
    }

    private void eatFruit() {
        if (this.getFruitsRect() != null) {
            int fruitIndex = getIntersecting(pacMan.getHitBox(), this.getFruitsRect());
            if (fruitIndex != -1) {
                playerScore += 500;
                scoreCounter += 500;
                levels[currentLevel].removeFruit(fruitIndex);
            }
        }
    }

    private void eatPowerPellet() {
        int powerPelletIndex = getIntersecting(pacMan.getHitBox(), this.getPowerPelletsRect());
        if (powerPelletIndex != -1) {
            playerScore += 50;
            scoreCounter += 50;
            pacMan.powerfulTime = 300;
            pacMan.ifPowerful = true;
            pacMan.eatenNumber = 0;
            for (Ghost ghost : ghosts) {
                ghost.powerlessTime = 300;
                ghost.ifPowerless = true;
                ghost.randomTime = 0;
            }
            levels[currentLevel].removePowerPellet(powerPelletIndex);
        }
    }

    // The following methods are related to the state of players and ghosts
    private void setPowerful() {
        pacMan.setIfPowerful();
        for (Ghost ghost : ghosts) {
            ghost.setIfPowerless();
        }
    }

    private void setResurrection() {
        for (Ghost ghost : ghosts) {
            ghost.setIfResurrection();
        }
    }

    private void setRandom() {
        for (Ghost ghost : ghosts) {
            ghost.setIfRandom();
        }
    }

    private void setEaten() {
        for (Ghost ghost : ghosts) {
            ghost.setIfEaten();
        }
    }
}
