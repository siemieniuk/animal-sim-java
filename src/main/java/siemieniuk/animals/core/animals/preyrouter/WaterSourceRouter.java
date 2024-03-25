package siemieniuk.animals.core.animals.preyrouter;

import siemieniuk.animals.core.locations.LocationRepository;
import siemieniuk.animals.core.locations.WaterSource;
import siemieniuk.animals.math.Coordinates;

public final class WaterSourceRouter extends PreyRouter {
    public WaterSourceRouter(LocationRepository locationRepository, Coordinates currentPos) {
        super(locationRepository, currentPos);
        super.findPlanToNearest(WaterSource.class);
    }
}
