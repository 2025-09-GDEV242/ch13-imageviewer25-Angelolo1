import java.awt.Color;
/**
 * A filter that applies a red tint to an image by boosting the
 * blue channel and reducing the red and green channels
 *
 * @author Angelo Martino
 * @version 1.0
 */
public class BlueTintFilter extends Filter
{
    /**
     * Constructor for BlueTintFilter.
     */
    public BlueTintFilter(String name)
    {
        super(name);
    }

    /**
     * Apply this filter to an image.
     */
    public void apply(OFImage image)
    {
        int height = image.getHeight();
        int width = image.getWidth();
        
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                
                Color pix = image.getPixel(x, y);
                
                int r = pix.getBlue() / 2;
                int g = pix.getBlue() / 2;
                int b = Math.min(255, pix.getRed() + 60);
                
                image.setPixel(x, y, new Color(r, g, b));
            }
        }
    }
}
