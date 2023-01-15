package com.siemieniuk.animals;

import javafx.scene.canvas.GraphicsContext;

/**
 * Class implementing this interface is able to draw on canva
 * @author Szymon Siemieniuk
 * @version 0.1
 *
 */
public interface Drawable {
	void prepareToDrawOn(GraphicsContext gc);
}
