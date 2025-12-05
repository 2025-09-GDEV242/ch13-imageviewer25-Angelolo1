import java.awt.Color;
/**
 * A Warhol-style filter that creates a 2x2 composite of
 * the original image and three tinted/mirrored versions 
 * of the original (reg, green, blue).
 *
 * @author Angelo Martino
 * @version 1.0
 */
public class WarholMirrorFilter extends Filter
{
    /**
     * Constructor for objects of class WarholMirrorFilter
     */
    public WarholMirrorFilter(String name)
    {
        super(name);
    }

    public void apply(OFImage image)
    {
        int width  = image.getWidth();
        int height = image.getHeight();
        
        int halfWidth  = width / 2;
        int halfHeight = height / 2;
        
        // Create quarter-size images
        OFImage topLeft     = new OFImage(halfWidth, halfHeight);
        OFImage topRight    = new OFImage(halfWidth, halfHeight);
        OFImage bottomLeft  = new OFImage(halfWidth, halfHeight);
        OFImage bottomRight = new OFImage(halfWidth, halfHeight);
        
        // Resize original into quarters
        for (int y = 0; y < halfHeight; y++) {
            for (int x = 0; x < halfWidth; x++) {
                int srcX = x * width / halfWidth;
                int srcY = y * height / halfHeight;
                Color pixel = image.getPixel(srcX, srcY);
                
                topLeft.setPixel(x, y, pixel);
                topRight.setPixel(x, y, pixel);
                bottomLeft.setPixel(x, y, pixel);
                bottomRight.setPixel(x, y, pixel);
            }
        }
        
        // Apply existing tint filters
        new RedTintFilter("Red Tint").apply(topRight);
        new GreenTintFilter("Green Tint").apply(bottomLeft);
        new BlueTintFilter("Blue Tint").apply(bottomRight);
        
        // Mirror the tinted images directly in loops
        // Top-right: horizontal mirror (swap across vertical center)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
               Color temp = topRight.getPixel(x, y);
               topRight.setPixel(x, y, topRight.getPixel(halfWidth - 1 - x, y));
               topRight.setPixel(x, halfWidth - 1 - y, temp);
            }
        }
        
        // Bottom-left: vertical mirror (swap across horizontal center)
        for (int y = 0; y < halfHeight / 2; y++) {
            for (int x = 0; x < halfWidth; x++) {
               Color temp = bottomLeft.getPixel(x, y);
               bottomLeft.setPixel(x, y, bottomLeft.getPixel(x, halfHeight - 1 - y));
               bottomLeft.setPixel(x, halfHeight - 1 - y, temp);
            }
        }
        
        //Bottom-right: horizontal + vertical mirror
        for (int y = 0; y < halfHeight / 2; y++) {
            for (int x = 0; x < halfWidth; x++) {
               int oppX = halfWidth - 1 - x;
               int oppY = halfHeight -1 - y;
                
               Color temp = bottomRight.getPixel(x, y);
               bottomLeft.setPixel(x, y, bottomRight.getPixel(oppX, oppY));
               bottomLeft.setPixel(oppX, oppY, temp);
               
               Color temp2 = bottomRight.getPixel(x, oppY);
               bottomLeft.setPixel(x, oppY, bottomRight.getPixel(oppX, y));
               bottomLeft.setPixel(oppX, y, temp);
            }
        }
        
        // Copy quarters into result
        OFImage result = new OFImage(width, height);
        copyImage(topLeft, result, 0, 0);
        copyImage(topRight, result, halfWidth, 0);
        copyImage(bottomLeft, result, 0, halfHeight);
        copyImage(bottomRight, result, halfWidth, halfHeight);
        
        // Copy back to original image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                image.setPixel(x, y, result.getPixel(x, y));
            }
        }
    }
    
    private void copyImage(OFImage src, OFImage dest, int startX, int startY)
    {
        int w = src.getWidth();
        int h = src.getHeight();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                dest.setPixel(startX + x, startY + y, src.getPixel(x, y));
            }
        }
    }
}