package siemieniuk.animals.core.animals.preyrouter;

import siemieniuk.animals.core.locations.PlantSource;
import siemieniuk.animals.math.Coordinates;

public final class PlantSourceRouter extends PreyRouter {
    public PlantSourceRouter(Coordinates currentPos) {
        super(currentPos);
        super.findPlanToNearest(PlantSource.class);
    }
}
