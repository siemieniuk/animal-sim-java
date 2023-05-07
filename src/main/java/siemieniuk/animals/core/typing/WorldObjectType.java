package siemieniuk.animals.core.typing;

import java.util.ArrayList;
import java.util.List;

/**
 * This enum can be used for distinguishing between different objects available in the world.
 * @author Szymon Siemieniuk
 */
public enum WorldObjectType {
    PREDATOR {
        @Override public String toString() {
            return "predator";
        }
    },

    PREY {
        @Override public String toString() {
            return "prey";
        }
    },

    GRASS {
        @Override public String toString() {
            return "nothing";
        }
    },

    PATH {
        @Override public String toString() {
            return "path";
        }
    },

    INTERSECTION {
        @Override public String toString() {
            return "intersection";
        }
    },

    WATER_SRC {
        @Override public String toString() {
            return "water source";
        }
    },


    PLANT_SRC {
        @Override public String toString() {
            return "plant source";
        }
    },

    HIDEOUT {
        @Override public String toString() {
            return "hideout";
        }
    };

    public boolean isLocation() {
        List<WorldObjectType> locations = new ArrayList<>();
        locations.add(HIDEOUT);
        locations.add(WATER_SRC);
        locations.add(PLANT_SRC);
        locations.add(INTERSECTION);
        locations.add(PATH);

        return locations.contains(this);
    }

    public boolean isAnimal() {
        List<WorldObjectType> animals = new ArrayList<>();
        animals.add(PREDATOR);
        animals.add(PREY);

        return animals.contains(this);
    }
}
