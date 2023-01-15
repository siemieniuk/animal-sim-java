package com.siemieniuk.animals;

import com.siemieniuk.animals.hobhw_parser.HobhwParser;
import com.siemieniuk.animals.hobhw_parser.WorldParameters;

import java.io.FileNotFoundException;
import java.util.*;

public class WorldBuilder {
    private static final int MIN_DIST = 4;
    private WorldBuilder() {}
    public static World create(int howManySources, int howManyHideouts, int xSize, int ySize){
        Coordinates.setMaxDim(xSize, ySize);
        List<Coordinates> coordinates = generateCoords(xSize, ySize, howManySources+howManyHideouts);
        Hashtable<Coordinates, Location> locations = new Hashtable<> ();
        generateWaterSources(locations, coordinates.subList(0, howManySources / 2));
        generatePlantSources(locations, coordinates.subList(howManySources/2, howManySources));
        generateHideouts(locations, coordinates.subList(howManySources, coordinates.size()));
        return World.init(locations, xSize, ySize);
    }

    public static World create(String path) throws FileNotFoundException {
        HobhwParser parser = new HobhwParser(path);
        WorldParameters params = parser.parse();
        Coordinates.setMaxDim(params.getxSize(), params.getySize());
        Hashtable<Coordinates, Location> locations = new Hashtable<>();
        generateWaterSources(locations, params.getWaterSources());
        generatePlantSources(locations, params.getPlantSources());
        generateHideouts(locations, params.getHideouts());
        generateIntersections(locations, params.getIntersections());
        generatePaths(locations, params.getPaths());
        return World.init(locations, params.getxSize(), params.getySize());
    }

    private static void generatePlantSources(Hashtable<Coordinates, Location> locations, List<Coordinates> coordinates) {
        for (Coordinates pos : coordinates) {
            /* TODO: Random replenishing speed */
            /* TODO: Random capacity */
            PlantSource ps = new PlantSource(pos, "a", 2, 3);
            locations.put(pos, ps);
        }
    }

    private static void generateWaterSources(Hashtable<Coordinates, Location> locations, List<Coordinates> coordinates) {
        for (Coordinates pos : coordinates) {
            /* TODO: Random replenishing speed */
            /* TODO: Random capacity */
            WaterSource ws = new WaterSource(pos, "a", 2, 3);
            locations.put(pos, ws);
        }
    }

    private static void generateHideouts(Hashtable<Coordinates, Location> locations, List<Coordinates> coordinates) {
        for (Coordinates pos : coordinates) {
            /* TODO: Random capacity */
            Hideout hideout = new Hideout(pos, 3);
            locations.put(pos, hideout);
        }
    }

    private static void generatePaths(Hashtable<Coordinates, Location> locations, List<Coordinates> coordinates) {
        for (Coordinates pos : coordinates) {
            Path path = new Path(pos);
            locations.put(pos, path);
        }
    }

    private static void generateIntersections(Hashtable<Coordinates, Location> locations, List<Coordinates> coordinates) {
        for (Coordinates pos : coordinates) {
            Intersection intersection = new Intersection(pos);
            locations.put(pos, intersection);
        }
    }

    private static List<Coordinates> generateCoords(int xSize, int ySize, int targetSize) {
        List<Coordinates> coordinates = new ArrayList<>();
        for (int x = 0; x < xSize; x++) {
            for (int y=0; y<ySize; y++) {
                coordinates.add(new Coordinates(x, y));
            }
        }
        Collections.shuffle(coordinates);
        int currentSize = 2;
        while (currentSize < targetSize) {
            boolean flag_to_rm = false;
            for (int i=0; i<currentSize; i++) {
                if (coordinates.get(i).getManhattanDistanceTo(coordinates.get(currentSize)) < MIN_DIST) {
                    flag_to_rm = true;
                    break;
                }
            }
            if (flag_to_rm) {
                coordinates.remove(currentSize);
            } else {
                currentSize++;
            }
        }
        while (coordinates.size() > targetSize) {
            coordinates.remove(targetSize);
        }
        return coordinates;
    }
}
