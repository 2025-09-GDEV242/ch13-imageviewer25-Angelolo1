import java.awt.Color;
/**
 * A filter that applies a red tint to an image by boosting the
 * green channel and reducing the red and blue channels
 *
 * @author Angelo Martino
 * @version 1.0
 */
public class GreenTintFilter extends Filter
{
    /**
     * Constructor for GreenTintFilter.
     */
    public GreenTintFilter(String name)
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
                int g = Math.min(255, pix.getRed() + 60);
                int b = pix.getBlue() / 2;
                
                image.setPixel(x, y, new Color(r, g, b));
            }
        }
    }
}
