package mdc.tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This class is used to provide methods for printing pictures or text in Graphic2D
 */
public class GraphPainter {
    /**
     * Draw text
     *
     * @param g2d     - Graphics object for creating Graphic2D
     * @param text  - Text to be printed
     * @param name  - Font name, such as "Arial", must be true type
     * @param style - Font style, such as bold, italic, etc
     * @param size  - Font size
     * @param color - Font color
     * @param pos   - Text position, 0 left, 1 center, 2 right
     * @param rect  - Rectangle of the position occupied by the text
     */
    public static void drawString(Graphics2D g2d, String text, String name, int style, int size, Color color, int pos, Rectangle rect) {
        g2d.setFont(new Font(name, style, size));
        FontMetrics metrics = g2d.getFontMetrics();
        int x, y;
        switch (pos) {
            case 0 -> {
                x = rect.x;
                y = rect.y + metrics.getAscent();
            }
            case 1 -> {
                x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
                y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
            }
            case 2 -> {
                x = rect.x + (rect.width - metrics.stringWidth(text));
                y = rect.y + metrics.getAscent();
            }
            default -> {
                x = rect.x;
                y = rect.y;
            }
        }
        g2d.setColor(color);
        g2d.drawString(text, x, y);
    }

    /**
     * Draw rectangle
     *
     * @param g2d - Graphics object for creating Graphic2D
     */
    public static void drawRectangle(Graphics2D g2d, Color color, Rectangle rect) {
        g2d.setColor(color);
        g2d.fill(rect);
    }

    /**
     * Draw an oval image
     *
     * @param g2d       - Graphics object for creating Graphic2D
     * @param color   - Image Color
     * @param x       - Upper left corner x
     * @param y       - Upper left corner y
     * @param width   - Image width
     * @param height- Image height
     */
    public static void drawOval(Graphics2D g2d, Color color, int x, int y, int width, int height) {
        g2d.setColor(color);
        g2d.fillOval(x, y, width, height);
    }

    /**
     * Draw a sector
     *
     * @param g2d     - Graphics object for creating Graphic2D
     * @param color - Image Color
     */
    public static void drawAct(Graphics2D g2d, Color color, int x, int y, int width, int height, int startAngle, int arcAngle) {
        g2d.setColor(color);
        g2d.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    /**
     * Draw a polygon
     *
     * @param g2d     - Graphics object for creating Graphic2D
     * @param color - Image Color
     */
    public static void drawPolygon(Graphics2D g2d, Color color, int[] xPoints, int[] yPoints, int nPoints) {
        g2d.setColor(color);
        g2d.fillPolygon(xPoints, yPoints, nPoints);
    }

    /**
     * Get image from resources
     */
    public static Image getImage(String path) throws IOException {
        return ImageIO.read(new FileInputStream(path));
    }

    /**
     * Draw an image on the Graphics context
     *
     * @param g2d     - Graphics object for creating Graphics2D
     * @param image - The image to be drawn
     * @param rect     - The rect of the image
     */
    public static void drawImage(Graphics2D g2d, Image image, Rectangle rect) throws IOException {
        g2d.drawImage(image, rect.x, rect.y, rect.width, rect.height, null);
    }

}
