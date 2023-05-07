package siemieniuk.animals.core.locations;

import siemieniuk.animals.core.typing.LocationVisitor;
import siemieniuk.animals.core.typing.WorldObjectType;
import siemieniuk.animals.math.Coordinates;

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

	public void accept(LocationVisitor visitor) {
		visitor.visitPath(this);
	}

	@Override
	public WorldObjectType getMetadataCode() {
		return WorldObjectType.PATH;
	}

	@Override
	public String toString() {
		return "Path";
	}
}
