package siemieniuk.animals.core.world_creation;

import siemieniuk.animals.core.locations.*;
import siemieniuk.animals.hobhw_parser.HobhwParser;
import siemieniuk.animals.hobhw_parser.WorldParameters;
import siemieniuk.animals.math.Coordinates;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class works similar to builder pattern. Its purpose is to construct the world.
 */
public class WorldBuilder {
    private static final int MIN_DIST = 4;
    private WorldBuilder() {}

    public static LocationRepository create(int howManySources, int howManyHideouts, int xSize, int ySize){
        Coordinates.setMaxDimensions(xSize, ySize);
        List<Coordinates> coordinates = generateCoordinates(xSize, ySize, howManySources+howManyHideouts);

        ConcurrentHashMap<Coordinates, Location> locations = new ConcurrentHashMap<>();
        generateWaterSources(locations, coordinates.subList(0, howManySources / 2));
        generatePlantSources(locations, coordinates.subList(howManySources/2, howManySources));
        generateHideouts(locations, coordinates.subList(howManySources, coordinates.size()));

        return new LocationRepository(locations, xSize, ySize);
    }

    /**
     * Creates world from a specific file (must be with .hobhw extension)
     * @param src The source to HOBHW file
     * @return the new world
     * @throws FileNotFoundException File was not found
     */
    public static LocationRepository create(URL src) throws FileNotFoundException {
        HobhwParser parser = new HobhwParser(src);
        WorldParameters params = parser.parse();
        Coordinates.setMaxDimensions(params.getxSize(), params.getySize());

        ConcurrentHashMap<Coordinates, Location> locations = new ConcurrentHashMap<>();
        generateWaterSources(locations, params.getWaterSources());
        generatePlantSources(locations, params.getPlantSources());
        generateHideouts(locations, params.getHideouts());
        generateIntersections(locations, params.getIntersections());
        generatePaths(locations, params.getPaths());

        return new LocationRepository(locations, params.getxSize(), params.getySize());
    }

    private static void generatePlantSources(ConcurrentHashMap<Coordinates, Location> locations, List<Coordinates> coordinates) {
        for (Coordinates pos : coordinates) {
            PlantSource ps = new PlantSource(pos, "Baobab", 10, 4);
            locations.put(pos, ps);
        }
    }

    private static void generateWaterSources(ConcurrentHashMap<Coordinates, Location> locations, List<Coordinates> coordinates) {
        for (Coordinates pos : coordinates) {
            WaterSource ws = new WaterSource(pos, "Lake", 10, 4);
            locations.put(pos, ws);
        }
    }

    private static void generateHideouts(ConcurrentHashMap<Coordinates, Location> locations, List<Coordinates> coordinates) {
        for (Coordinates pos : coordinates) {
            Hideout hideout = new Hideout(pos, 3);
            locations.put(pos, hideout);
        }
    }

    private static void generatePaths(ConcurrentHashMap<Coordinates, Location> locations, List<Coordinates> coordinates) {
        for (Coordinates pos : coordinates) {
            Path path = new Path(pos);
            locations.put(pos, path);
        }
    }

    private static void generateIntersections(ConcurrentHashMap<Coordinates, Location> locations, List<Coordinates> coordinates) {
        for (Coordinates pos : coordinates) {
            Intersection intersection = new Intersection(pos);
            locations.put(pos, intersection);
        }
    }

    private static List<Coordinates> generateCoordinates(int xSize, int ySize, int targetSize) {
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
