package com.siemieniuk.animals.core.world_creation;

import com.siemieniuk.animals.core.WorldObjectType;
import com.siemieniuk.animals.math.Pair;
import javafx.scene.image.Image;

import java.util.Hashtable;
import java.util.List;

public class ImageLoader {
    private static Hashtable<WorldObjectType, Image> images = null;

    public static void init(List<Pair<WorldObjectType, String>> entries) {
        images = new Hashtable<>();
        for (Pair<WorldObjectType, String> entry : entries) {
            Image img = new Image(entry.getRight());
            WorldObjectType key = entry.getLeft();
            images.put(key, img);
        }
    }

    public static Image getImage(WorldObjectType key) {
        if (images != null) {
            return images.get(key);
        }
        return null;
    }
}
