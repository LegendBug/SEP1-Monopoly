package tools;

public class Config {
    private Screen screen;
    private GameInfo gameInfo;
    private Rectangle rectangle;
    private ImagePath imagePath;

    public Screen getScreen() {
        return screen;
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public ImagePath getImagePath() {
        return imagePath;
    }

    // Getters and setters...

    public static class Screen {
        private int titleBar;
        private int width;
        private int height;

        public int getTitleBar() {
            return titleBar;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        // Getters and setters...
    }

    public static class GameInfo {
        private int levels;
        private int initialLives;

        public int getLevels() {
            return levels;
        }

        public int getInitialLives() {
            return initialLives;
        }

        // Getters and setters...
    }

    public static class Rectangle {
        // Assuming there are some properties here, add them as needed
        // Getters and setters...
    }

    public static class ImagePath {
        private String icon;

        public String getIcon() {
            return icon;
        }


        // Getters and setters...
    }
}
