package siemieniuk.animals.core.locations;

import lombok.Getter;
import siemieniuk.animals.math.Coordinates;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class LocationRepository {
    private final int X_SIZE;
    private final int Y_SIZE;
    private final Random random;

    private final ConcurrentHashMap<Coordinates, Location> locations;

    public LocationRepository(ConcurrentHashMap<Coordinates, Location> locations, int xSize, int ySize) {
        this.locations = locations;
        this.X_SIZE = xSize;
        this.Y_SIZE = ySize;
        this.random = new Random();
    }

    public Location getLocation(Coordinates pos) {
        return locations.get(pos);
    }

    public Coordinates getRandomPathLocation() {
        ArrayList<Location> locCopy = new ArrayList<>(locations.values());
        int idx;
        do {
            idx = random.nextInt(locCopy.size());
        } while (!(locCopy.get(idx) instanceof Path));
        return locCopy.get(idx).getPos();
    }
    
    public ArrayList<? extends Location> getHideouts() {
        return getGroupOfLocations(Hideout.class);
    }
    
    public ArrayList<Location> getWaterSources() {
        return getGroupOfLocations(WaterSource.class);
    }
    
    public ArrayList<Location> getPlantSources() {
        return getGroupOfLocations(PlantSource.class);
    }
    
    private ArrayList<Location> getGroupOfLocations(Class<?> c) {
        ArrayList<Location> result = new ArrayList<>();
        for (Location location : locations.values()) {
            if (location.getClass().isAssignableFrom(c)) {
                result.add(location);
            }
        }
        return result;
    }

    public Coordinates getRandomValidPosition() {
        int x = random.nextInt(X_SIZE);
        int y = random.nextInt(Y_SIZE);
        return new Coordinates(x, y);
    }
}
