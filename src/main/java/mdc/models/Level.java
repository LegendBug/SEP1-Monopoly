package mdc.models;

import mdc.models.Blocks.GhostWall;
import mdc.models.Blocks.NormalWall;
import mdc.models.Entities.*;
import mdc.tools.TextReader;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Level {
    public List<Dot> dots;
    public List<PowerPellet> powerPellets;
    public List<int[]> normalWallsPos;
    public double ghostsSpeed;
    public int ghostsNumber;
    public int resurrectionTime;
    public PacMan pacMan;
    public List<Ghost> ghosts;
    private List<NormalWall> normalWalls;
    private List<GhostWall> ghostWalls;
    private List<Fruit> fruits;
    private List<int[]> ghostWallsPos;
    private List<int[]> dotsPos;
    private List<int[]> fruitsPos;
    private List<int[]> powerPelletsPos;

    /**
     * This class is used to set the components and data of each level
     *
     * @param ghostsSpeed      The movement speed of ghosts in this level
     * @param ghostsNumber     Number of ghosts in this level
     * @param resurrectionTime The resurrection time of the ghost in this level
     */
    public Level(double ghostsSpeed, int ghostsNumber, int resurrectionTime) {
        this.ghostsSpeed = 3 * ghostsSpeed;
        this.ghostsNumber = ghostsNumber;
        this.resurrectionTime = resurrectionTime;
        reset();
    }

    public List<Rectangle> getNormalWallsRect() {
        List<Rectangle> normalWallsRect = new ArrayList<>();
        for (NormalWall normalWall : normalWalls) {
            normalWallsRect.add(normalWall.getHitBox());
        }
        return normalWallsRect;
    }

    public List<Rectangle> getGhostWallsRect() {
        List<Rectangle> ghostWallsRect = new ArrayList<>();
        for (GhostWall ghostWall : ghostWalls) {
            ghostWallsRect.add(ghostWall.getHitBox());
        }
        return ghostWallsRect;
    }

    public List<Rectangle> getDotsRect() {
        List<Rectangle> dotsRect = new ArrayList<>();
        for (Dot dot : dots) {
            dotsRect.add(dot.getHitBox());
        }
        return dotsRect;
    }

    public List<Rectangle> getFruitsRect() {
        List<Rectangle> fruitsRect = new ArrayList<>();
        for (Fruit fruit : fruits) {
            fruitsRect.add(fruit.getHitBox());
        }
        return fruitsRect;
    }

    public List<Rectangle> getPowerPelletsRect() {
        List<Rectangle> powerPelletsRect = new ArrayList<>();
        for (PowerPellet powerPellet : powerPellets) {
            powerPelletsRect.add(powerPellet.getHitBox());
        }
        return powerPelletsRect;
    }

    /**
     * Mark all positions except the normal wall as the path that ghosts can walk
     */
    public List<int[]> getRoadsPos() {
        List<int[]> roadsPos = TextReader.getComponents('P');
        assert roadsPos != null;
        roadsPos.addAll(Objects.requireNonNull(TextReader.getComponents('G')));
        roadsPos.addAll(ghostWallsPos);
        roadsPos.addAll(dotsPos);
        roadsPos.addAll(fruitsPos);
        roadsPos.addAll(powerPelletsPos);
        return roadsPos;
    }

    public void removeDot(int i) {
        dots.remove(i);
    }

    public void removeFruit(int i) {
        fruits.remove(i);
    }

    public void removePowerPellet(int i) {
        powerPellets.remove(i);
    }

    private void resetNormalWalls() {
        normalWalls = new ArrayList<>();
        normalWallsPos = TextReader.getComponents('W');
        for (int i = 0; i < Objects.requireNonNull(normalWallsPos).size(); i++) {
            normalWalls.add(new NormalWall(normalWallsPos.get(i)[0] * 40, normalWallsPos.get(i)[1] * 40));
        }
    }

    private void resetGhostWalls() {
        ghostWalls = new ArrayList<>();
        ghostWallsPos = TextReader.getComponents('-');
        assert ghostWallsPos != null;
        ghostWallsPos.addAll(Objects.requireNonNull(TextReader.getComponents('G')));
        for (int i = 0; i < Objects.requireNonNull(ghostWallsPos).size(); i++) {
            ghostWalls.add(new GhostWall(ghostWallsPos.get(i)[0] * 40, ghostWallsPos.get(i)[1] * 40));
        }
    }

    private void resetDots() {
        dots = new ArrayList<>();
        dotsPos = TextReader.getComponents('.');
        for (int i = 0; i < Objects.requireNonNull(dotsPos).size(); i++) {
            dots.add(new Dot(dotsPos.get(i)[0] * 40 + 15, dotsPos.get(i)[1] * 40 + 15));
        }
    }

    private void resetFruit() {
        fruits = new ArrayList<>();
        fruitsPos = TextReader.getComponents('F');
        for (int i = 0; i < Objects.requireNonNull(fruitsPos).size(); i++) {
            fruits.add(new Fruit(fruitsPos.get(0)[0] * 40, fruitsPos.get(0)[1] * 40));
        }
    }

    private void resetPowerPellets() {
        powerPellets = new ArrayList<>();
        powerPelletsPos = TextReader.getComponents('*');
        for (int i = 0; i < Objects.requireNonNull(powerPelletsPos).size(); i++) {
            powerPellets.add(new PowerPellet(powerPelletsPos.get(i)[0] * 40 + 5, powerPelletsPos.get(i)[1] * 40 + 5));
        }
    }

    public void resetPacMan() {
        pacMan = new PacMan();
    }

    public void resetGhosts() {
        ghosts = new ArrayList<>();
        Ghost blinky = new Ghost(0, Color.ORANGE);
        Ghost pinky = new Ghost(resurrectionTime, Color.GRAY);
        Ghost inky = new Ghost(resurrectionTime * 2, Color.GRAY);
        Ghost clyde = new Ghost(resurrectionTime * 3, Color.GRAY);
        ghosts.add(blinky);
        if (ghostsNumber == 2) {
            ghosts.add(pinky);
        } else if (ghostsNumber == 3) {
            ghosts.add(pinky);
            ghosts.add(inky);
        } else if (ghostsNumber == 4) {
            ghosts.add(pinky);
            ghosts.add(inky);
            ghosts.add(clyde);
        }
    }

    /**
     * Reset all components at the beginning of the level
     */
    private void reset() {
        resetNormalWalls();
        resetGhostWalls();
        resetDots();
        resetFruit();
        resetPowerPellets();
        resetPacMan();
        resetGhosts();
    }
}
