import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ImageCache {

	private Map<MyURL, Image> imageCache = new HashMap<MyURL, Image>();

	public static interface ImageLoader {
		Image loadImage(MyURL url);
	}

	public void addImage(MyURL key, BufferedImage image) {
		imageCache.put(key, image);
	}

	public BufferedImage getImage(MyURL key) {
		if (imageCache.containsKey(key))
			return (BufferedImage) imageCache.get(key);

		return null;
	}
}