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
        new RedTintFilter("red").apply(topRight);
        new GreenTintFilter("green").apply(bottomLeft);
        new BlueTintFilter("blue").apply(bottomRight);
        
        // Apply required mirrors
        mirrorHorizontal(topRight);
        mirrorVertical(bottomLeft);
        mirrorHorizontal(bottomRight);
        mirrorVertical(bottomRight);
        
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
    
    private void mirrorHorizontal(OFImage img)
    {
        int w = img.getWidth();
        int h = img.getHeight();
        
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w / 2; x++) {
                Color temp = img.getPixel(x,y);
                img.setPixel(x, y, img.getPixel(w - 1 - x, y));
                img.setPixel(w - 1 - x, y, temp);
            }
        }
    }
    
    private void mirrorVertical(OFImage img)
    {
        int w = img.getWidth();
        int h = img.getHeight();
        
        for (int y = 0; y < h / 2; y++) {
            for (int x = 0; x < w; x++) {
                Color temp = img.getPixel(x,y);
                img.setPixel(x, y, img.getPixel(x, h - 1 - y));
                img.setPixel(x, h - 1 - y, temp);
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