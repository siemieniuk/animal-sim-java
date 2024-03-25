package siemieniuk.animals.core.animals.preyrouter;

import siemieniuk.animals.core.locations.Hideout;
import siemieniuk.animals.core.locations.LocationRepository;
import siemieniuk.animals.math.Coordinates;

public final class HideoutRouter extends PreyRouter {
    public HideoutRouter(LocationRepository locationRepository, Coordinates currentPos) {
        super(locationRepository, currentPos);
        super.findPlanToNearest(Hideout.class);
    }
}
