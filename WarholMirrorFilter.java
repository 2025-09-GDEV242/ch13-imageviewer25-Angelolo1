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
    public WarholMirrorFilter()
    {
        super("Flipped Warhol Filter");
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
    }
}