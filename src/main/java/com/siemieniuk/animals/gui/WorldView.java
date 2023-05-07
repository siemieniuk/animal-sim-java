package com.siemieniuk.animals.gui;

import com.siemieniuk.animals.core.World;
import com.siemieniuk.animals.core.animals.Animal;
import com.siemieniuk.animals.core.locations.Location;
import com.siemieniuk.animals.core.typing.WorldObjectType;
import com.siemieniuk.animals.images.ImageLoader;
import com.siemieniuk.animals.math.Coordinates;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public final class WorldView extends Canvas {
    private final World world;

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double maxHeight(double width) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double maxWidth(double height) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double minWidth(double height) {
        return 1D;
    }

    @Override
    public double minHeight(double width) {
        return 1D;
    }

    @Override
    public void resize(double width, double height) {
        this.setWidth(Math.min(width, height));
        this.setHeight(Math.min(width, height));
    }

    public WorldView() {
        super();
        world = World.getInstance();
    }

    public WorldView(double size) {
        super(size, size);
        world = World.getInstance();
    }

    private static int cellWidth = 0, cellHeight = 0;

    /**
     * Forces canvas to redraw
     */
    public void redraw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        setCellDimensions();
        drawBackground();
        drawLocations();
        drawAnimals();
        drawMesh();
    }

    private void drawAnimals() {
        GraphicsContext gc = getGraphicsContext2D();
        for (Animal a : world.getAnimals()) {
            Image img = ImageLoader.getImage(a.getMetadataCode());
            Coordinates pos = a.getPos();
            gc.drawImage(img, pos.getX()*cellWidth, pos.getY()*cellHeight, cellWidth, cellHeight);
        }
    }

    private void drawBackground() {
        GraphicsContext gc = getGraphicsContext2D();
        Image img = ImageLoader.getImage(WorldObjectType.GRASS);

        for (int x=0; x<world.getX_SIZE(); x++) {
            for (int y=0; y<world.getY_SIZE(); y++) {
                gc.drawImage(img, x*cellWidth, y*cellHeight, cellWidth, cellHeight);
            }
        }
    }

    private void drawLocations() {
        GraphicsContext gc = getGraphicsContext2D();
        for (Location l: world.getLocations().values()) {
            Image img = ImageLoader.getImage(l.getMetadataCode());
            Coordinates pos = l.getPos();
            gc.drawImage(img, pos.getX()*cellWidth, pos.getY()*cellHeight, cellWidth, cellHeight);
        }
    }

    private void drawMesh() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setLineWidth(1);
        gc.setStroke(Color.GRAY);

        double width = cellWidth * world.getX_SIZE()-1;
        double height = cellHeight * world.getY_SIZE()-1;

        for (int x=1; x<world.getX_SIZE(); x++) {
            gc.strokeLine(x*cellWidth, 0, x*cellWidth, width);
        }
        for (int y=1; y<world.getY_SIZE(); y++) {
            gc.strokeLine(0, y*cellHeight, height, y*cellHeight);
        }
    }

    private void setCellDimensions() {
        cellWidth = (int) getWidth() / world.getX_SIZE();
        cellHeight = (int) getHeight() / world.getX_SIZE();
    }

    /**
     * Converts canvas position to the world's coordinate system
     * @param x Canvas' horizontal position
     * @param y Canvas' vertical position
     * @return The position in Coordinate system
     */
    public static int[] convertToWorldPos(double x, double y) {
        int newX = (int)x/cellWidth;
        int newY = (int)y/cellHeight;
        return new int[] {newX, newY};
    }
}
