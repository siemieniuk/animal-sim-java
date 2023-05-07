package siemieniuk.animals.images;

import siemieniuk.animals.core.typing.WorldObjectType;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

public class ImageLoader {
    private static Hashtable<WorldObjectType, Image> images = null;

    private static boolean wasInstantiated = false;

    public static void init() {
        if (wasInstantiated) return;

        images = new Hashtable<>();

        String[] paths = new String[] {
                "grass.png",
                "hideout.png",
                "intersection.png",
                "path.png",
                "water.png",
                "plant.png",
                "predator.png",
                "prey.png"
        };

        WorldObjectType[] types = new WorldObjectType[] {
                WorldObjectType.GRASS,
                WorldObjectType.HIDEOUT,
                WorldObjectType.INTERSECTION,
                WorldObjectType.PATH,
                WorldObjectType.WATER_SRC,
                WorldObjectType.PLANT_SRC,
                WorldObjectType.PREDATOR,
                WorldObjectType.PREY
        };

        for (int i=0; i<paths.length; i++) {
            try (InputStream is = ImageLoader.class.getResourceAsStream(paths[i])) {
                assert is != null;
                Image img = new Image(is);
                WorldObjectType key = types[i];
                images.put(key, img);
            } catch (IOException e) {
                System.err.println("Archive corrupted!");
            }
        }

        wasInstantiated = true;
    }

    public static Image getImage(WorldObjectType key) {
        if (!wasInstantiated) {
            init();
        }
        return (images != null) ? images.get(key) : null;
    }
}
