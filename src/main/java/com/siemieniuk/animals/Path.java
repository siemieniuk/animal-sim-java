package com.siemieniuk.animals;

import com.siemieniuk.animals.math.Coordinates;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public class Path extends Location {
	/**
	 * @param pos Object of class Coordinates
	 */
	public Path(Coordinates pos) {
		super(pos);
	}

	@Override
	public void prepareToDrawOn(GraphicsContext gc) {
		gc.setFill(Color.YELLOW);
	}

	@Override
	public String getDetails() {
		return null;
	}
}
