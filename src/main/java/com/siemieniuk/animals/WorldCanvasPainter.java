package com.siemieniuk.animals;

import com.siemieniuk.animals.math.Coordinates;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * This class is an intermediate between the world and the main window's canvas
 * @author Szymon Siemieniuk
 */
public class WorldCanvasPainter {
    private final World world;
    private final Canvas canvas;
    private static int cellWidth = 0, cellHeight = 0;

    /**
     * Simple constructor
     * @param canvas Canvas
     */
    public WorldCanvasPainter(Canvas canvas) {
        this.world = World.getInstance();
        this.canvas = canvas;
        canvas.widthProperty().addListener((event) -> updateCanvas());
        canvas.heightProperty().addListener((event) -> updateCanvas());
        setCellDimensions();
    }

    /**
     * Forces canvas to redraw
     */
    public void updateCanvas() {
        setCellDimensions();
        drawBackground();
        drawLocations();
        drawAnimals();
        drawMesh();
    }

    protected void drawAnimals() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.PINK);
        gc.setStroke(Color.BLACK);
        final double scalingVal = 0.5;
        int v2 = (int)(scalingVal*cellWidth);
        int v3 = (int)(scalingVal*cellHeight);
        for (Animal a : world.getAnimals()) {
            switch(a.getMetadataCode()) {
                case PREDATOR -> gc.setFill(Color.RED);
                case PREY -> gc.setFill(Color.YELLOW);
            }
            Coordinates pos = a.getPos();
            int v = (int)(cellWidth*(pos.getX()+(1-scalingVal)/2));
            int v1 = (int)(cellHeight*(pos.getY()+(1-scalingVal)/2));
            gc.fillOval(v, v1, v2, v3);
            gc.strokeOval(v, v1, v2, v3);
        }
    }

    protected void drawBackground() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image img = ImageLoader.getImage(WorldObjectType.GRASS);

        for (int x=0; x<world.getX_SIZE(); x++) {
            for (int y=0; y<world.getY_SIZE(); y++) {
                gc.drawImage(img, x*cellWidth, y*cellHeight, cellWidth, cellHeight);
            }
        }
    }

    protected void drawLocations() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (Location l: world.getLocations().values()) {
            Image img = ImageLoader.getImage(l.getMetadataCode());
            Coordinates pos = l.getPos();
            gc.drawImage(img, pos.getX()*cellWidth, pos.getY()*cellHeight, cellWidth, cellHeight);
        }
    }

    protected void drawMesh() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
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

    protected void setCellDimensions() {
        cellWidth = (int) canvas.getWidth() / world.getX_SIZE();
        cellHeight = (int) canvas.getHeight() / world.getX_SIZE();
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
