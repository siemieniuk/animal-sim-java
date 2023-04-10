package com.siemieniuk.animals;

import com.siemieniuk.animals.math.Coordinates;

/**
 * This class represents a path
 * @author Szymon Siemieniuk
 */
public class Path extends Location {
	/**
	 * Simple constructor
	 * @param pos Object of class Coordinates
	 */
	public Path(Coordinates pos) {
		super(pos);
	}

	@Override
	public WorldObjectType getMetadataCode() {
		return WorldObjectType.PATH;
	}
}
