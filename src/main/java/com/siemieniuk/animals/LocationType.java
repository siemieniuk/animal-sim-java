package com.siemieniuk.animals;

public enum LocationType {
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
    }
}
