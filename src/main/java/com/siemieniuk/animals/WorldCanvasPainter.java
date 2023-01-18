package com.siemieniuk.animals;

import com.siemieniuk.animals.math.Coordinates;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class WorldCanvasPainter {
    private final World world;
    private final Canvas canvas;
    private static int cellWidth = 0, cellHeight = 0;

    public WorldCanvasPainter(Canvas c) {
        this.world = World.getInstance();
        this.canvas = c;
        canvas.widthProperty().addListener((event) -> updateCanvas());
        canvas.heightProperty().addListener((event) -> updateCanvas());
        setCellDimensions();
    }

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
            a.prepareToDrawOn(gc);
            Coordinates pos = a.getPos();
            int v = (int)(cellWidth*(pos.getX()+(1-scalingVal)/2));
            int v1 = (int)(cellHeight*(pos.getY()+(1-scalingVal)/2));
            gc.fillOval(v, v1, v2, v3);
            gc.strokeOval(v, v1, v2, v3);
        }
    }

    protected void drawBackground() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    protected void drawLocations() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (Location l: world.getLocations().values()) {
            l.prepareToDrawOn(gc);
            Coordinates pos = l.getPos();
            gc.fillRect(pos.getX()*cellWidth, pos.getY()*cellHeight, cellWidth, cellHeight);
        }
    }

    public void drawMesh() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
        for (int x=1; x<world.getxSize(); x++) {
            gc.strokeLine(x*cellWidth, 0, x*cellWidth, canvas.getWidth());
        }
        for (int y=1; y<world.getySize(); y++) {
            gc.strokeLine(0, y*cellHeight, canvas.getHeight(), y*cellHeight);
        }
    }

    public void setCellDimensions() {
        cellWidth = (int) canvas.getWidth() / world.getxSize();
        cellHeight = (int) canvas.getHeight() / world.getxSize();
    }

    public static int[] convertToWorldPos(double x, double y) {
        int newX = (int)x/cellWidth;
        int newY = (int)y/cellHeight;
        return new int[] {newX, newY};
    }
}
