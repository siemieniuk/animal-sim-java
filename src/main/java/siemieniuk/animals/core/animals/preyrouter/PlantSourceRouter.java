package siemieniuk.animals.core.animals.preyrouter;

import siemieniuk.animals.core.locations.LocationRepository;
import siemieniuk.animals.core.locations.PlantSource;
import siemieniuk.animals.math.Coordinates;

public final class PlantSourceRouter extends PreyRouter {
    public PlantSourceRouter(LocationRepository locationRepository, Coordinates currentPos) {
        super(locationRepository, currentPos);
        super.findPlanToNearest(PlantSource.class);
    }
}
