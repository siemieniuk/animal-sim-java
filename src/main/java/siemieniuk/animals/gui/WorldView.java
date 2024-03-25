package siemieniuk.animals.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import siemieniuk.animals.core.animals.Animal;
import siemieniuk.animals.core.animals.AnimalRepository;
import siemieniuk.animals.core.locations.Location;
import siemieniuk.animals.core.locations.LocationRepository;
import siemieniuk.animals.core.typing.WorldObjectType;
import siemieniuk.animals.images.ImageLoader;
import siemieniuk.animals.math.Coordinates;

public final class WorldView extends Canvas {
    private AnimalRepository animalRepository;
    private LocationRepository locationRepository;

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
        drawGrid();
    }

    private void drawAnimals() {
        GraphicsContext gc = getGraphicsContext2D();
        for (Animal a : animalRepository.getAnimals()) {
            Image img = ImageLoader.getImage(a.getMetadataCode());
            Coordinates pos = a.getPos();
            gc.drawImage(img, pos.getX()*cellWidth, pos.getY()*cellHeight, cellWidth, cellHeight);
        }
    }

    private void drawBackground() {
        GraphicsContext gc = getGraphicsContext2D();
        Image img = ImageLoader.getImage(WorldObjectType.GRASS);

        for (int x=0; x<locationRepository.getX_SIZE(); x++) {
            for (int y=0; y<locationRepository.getY_SIZE(); y++) {
                gc.drawImage(img, x*cellWidth, y*cellHeight, cellWidth, cellHeight);
            }
        }
    }

    private void drawLocations() {
        GraphicsContext gc = getGraphicsContext2D();
        for (Location l: locationRepository.getLocations().values()) {
            Image img = ImageLoader.getImage(l.getMetadataCode());
            Coordinates pos = l.getPos();
            gc.drawImage(img, pos.getX()*cellWidth, pos.getY()*cellHeight, cellWidth, cellHeight);
        }
    }

    private void drawGrid() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setLineWidth(1);
        gc.setStroke(Color.GRAY);

        double width = cellWidth * locationRepository.getX_SIZE()-1;
        double height = cellHeight * locationRepository.getY_SIZE()-1;

        for (int x=1; x<locationRepository.getX_SIZE(); x++) {
            gc.strokeLine(x*cellWidth, 0, x*cellWidth, width);
        }
        for (int y=1; y<locationRepository.getY_SIZE(); y++) {
            gc.strokeLine(0, y*cellHeight, height, y*cellHeight);
        }
    }

    private void setCellDimensions() {
        cellWidth = (int) getWidth() / locationRepository.getX_SIZE();
        cellHeight = (int) getHeight() / locationRepository.getX_SIZE();
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

    public void setRepositories(AnimalRepository animalRepository, LocationRepository locationRepository) {
        this.animalRepository = animalRepository;
        this.locationRepository = locationRepository;
    }
}
