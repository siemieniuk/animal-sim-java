package com.siemieniuk.animals.core;

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
    }
}
