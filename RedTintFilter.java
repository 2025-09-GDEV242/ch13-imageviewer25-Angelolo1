import java.awt.Color;
/**
 * A filter that applies a red tint to an image by boosting the
 * red channel and reducing the green and blue channels
 *
 * @author Angelo Martino
 * @version 1.0
 */
public class RedTintFilter extends Filter
{
    /**
     * Constructor for RedTintFilter.
     */
    public RedTintFilter(String name)
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
                
                int r = Math.min(255, pix.getRed() + 60);
                int g = pix.getGreen() / 2;
                int b = pix.getBlue() / 2;
                
                image.setPixel(x, y, new Color(r, g, b));
            }
        }
    }
}
