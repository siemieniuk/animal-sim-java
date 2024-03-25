package siemieniuk.animals.hobhw_parser;

import lombok.Getter;
import siemieniuk.animals.math.Coordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores parameters necessary to build a new world.
 * @author Szymon Siemieniuk
 */
@Getter
public class WorldParameters {
    private int xSize;
    private int ySize;
    private final List<Coordinates> hideouts;
    private final List<Coordinates> waterSources;
    private final List<Coordinates> plantSources;
    private final List<Coordinates> paths;
    private final List<Coordinates> intersections;

    public WorldParameters() {
        this.xSize = 0;
        this.ySize = 0;
        this.hideouts = new ArrayList<>();
        this.waterSources = new ArrayList<>();
        this.plantSources = new ArrayList<>();
        this.paths = new ArrayList<>();
        this.intersections = new ArrayList<>();
    }

    public WorldParameters(int xSize, int ySize, List<Coordinates> hideouts, List<Coordinates> waterSources, List<Coordinates> plantSources, List<Coordinates> paths, List<Coordinates> intersections) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.hideouts = hideouts;
        this.waterSources = waterSources;
        this.plantSources = plantSources;
        this.paths = paths;
        this.intersections = intersections;
    }

    /**
     * Adds water source location
     * @param c Coordinates to the new object
     */
    public void addWaterSource(Coordinates c) {
        waterSources.add(c);
    }

    /**
     * Adds hideout location
     * @param c Coordinates to the new object
     */
    public void addHideout(Coordinates c) {
        hideouts.add(c);
    }

    /**
     * Adds plant source location
     * @param c Coordinates to the new object
     */
    public void addPlantSource(Coordinates c) {
        plantSources.add(c);
    }

    /**
     * Adds path location
     * @param c Coordinates to the new object
     */
    public void addPath(Coordinates c) {
        paths.add(c);
    }

    /**
     * Adds intersection location
     * @param c Coordinates to the new object
     */
    public void addIntersection(Coordinates c) {
        intersections.add(c);
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public void setySize(int ySize) {
        this.ySize = ySize;
    }

}
