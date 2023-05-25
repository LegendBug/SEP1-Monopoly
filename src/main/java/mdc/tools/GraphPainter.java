package mdc.tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
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
     * @param selected - Is the image chosen by player
     */
    public static void drawImage(Graphics2D g2d, Image image, Rectangle rect, boolean selected) throws IOException {
        int x = rect.x;
        int y = rect.y;
        int width = rect.width;
        int height = rect.height;
        g2d.drawImage(image, x, y, width, height, null);
        if (selected) {
            Color darkColor = new Color(0, 0, 0, 255);
            // Convert original image to BufferedImage with the given size
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D gResized = resizedImage.createGraphics();
            gResized.drawImage(image, 0, 0, width, height, null);
            gResized.dispose();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int argb = resizedImage.getRGB(i, j); // get the pixel data
                    // calculate the alpha (the first 8 bits from the left)
                    int alpha = (argb >> 24) & 0xFF;
                    // add a dark layer keeping the original alpha
                    if (alpha != 0) resizedImage.setRGB(i, j, darkColor.getRGB());
                }
            }
            Composite oldComposite = g2d.getComposite(); // Save original composite
            // Set the composite mode to multiply, which will darken the image
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
            g2d.drawImage(resizedImage, x, y, null);
            g2d.setComposite(oldComposite); // Restore original composite
        }
    }

}
