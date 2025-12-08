// --- Pet Haven ---
// Game created by Trinity Johnson for CS 3250 Portfolio Project

package petgame;

import javafx.scene.image.Image;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AssetCache {

    private static final Map<String, Image> imageCache = new HashMap<>();

    // Loads an image if not already cached
    public static Image getImage(String path) {
        if (imageCache.containsKey(path)) {
            return imageCache.get(path);
        }

        InputStream is = AssetCache.class.getResourceAsStream(path);
        Image img = new Image(is);
        imageCache.put(path, img);
        return img;
    }

    // Clears all cached images
    public static void clear() {
        imageCache.clear();
    }
}