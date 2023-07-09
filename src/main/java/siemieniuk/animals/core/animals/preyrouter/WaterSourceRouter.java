package siemieniuk.animals.core.animals.preyrouter;

import siemieniuk.animals.core.locations.WaterSource;
import siemieniuk.animals.math.Coordinates;

public final class WaterSourceRouter extends PreyRouter {
    public WaterSourceRouter(Coordinates pos) {
        super(pos);
        super.findPlanToNearest(WaterSource.class);
    }
}
