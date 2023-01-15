package com.siemieniuk.animals.hobhw_parser;

import com.siemieniuk.animals.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class WorldParameters {
    private int xSize;
    private int ySize;
    private List<Coordinates> hideouts;
    private List<Coordinates> waterSources;
    private List<Coordinates> plantSources;
    private List<Coordinates> paths;
    private List<Coordinates> intersections;

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
    public void addWaterSource(Coordinates c) {
        waterSources.add(c);
    }

    public void addHideout(Coordinates c) {
        hideouts.add(c);
    }

    public void addPlantSource(Coordinates c) {
        plantSources.add(c);
    }

    public void addPath(Coordinates c) {
        paths.add(c);
    }

    public void addIntersection(Coordinates c) {
        intersections.add(c);
    }

    public int getxSize() {
        return xSize;
    }

    public void setxSize(int xSize) {
        this.xSize = xSize;
    }

    public int getySize() {
        return ySize;
    }

    public void setySize(int ySize) {
        this.ySize = ySize;
    }

    public List<Coordinates> getHideouts() {
        return hideouts;
    }

    public void setHideouts(List<Coordinates> hideouts) {
        this.hideouts = hideouts;
    }


    public List<Coordinates> getWaterSources() {
        return waterSources;
    }

    public void setWaterSources(List<Coordinates> waterSources) {
        this.waterSources = waterSources;
    }

    public List<Coordinates> getPlantSources() {
        return plantSources;
    }

    public void setPlantSources(List<Coordinates> plantSources) {
        this.plantSources = plantSources;
    }

    public List<Coordinates> getPaths() {
        return paths;
    }

    public void setPaths(List<Coordinates> paths) {
        this.paths = paths;
    }

    public List<Coordinates> getIntersections() {
        return intersections;
    }

    public void setIntersections(List<Coordinates> intersections) {
        this.intersections = intersections;
    }
}
