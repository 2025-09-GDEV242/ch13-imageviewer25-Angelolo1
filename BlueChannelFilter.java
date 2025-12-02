import java.awt.Color;
/**
 * A filter that converts the image to a grayscale version
 * based on the Blue channel.
 *
 * @author Angelo Martino
 * @version 1.0
 */
public class BlueChannelFilter extends Filter
{
    /**
     * Constructor BlueChannelFilter.
     */
    public BlueChannelFilter(String name)
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
                int gray = pix.getRed();
                image.setPixel(x, y, new Color(gray, gray, gray));
            }
        }
    }
}