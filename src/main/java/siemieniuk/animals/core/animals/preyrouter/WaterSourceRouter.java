package siemieniuk.animals.core.animals.preyrouter;

import siemieniuk.animals.core.typing.WorldObjectType;
import siemieniuk.animals.math.Coordinates;

public final class WaterSourceRouter extends PreyRouter {
    public WaterSourceRouter(Coordinates pos) {
        super(pos);
        super.findPlanToNearest(WorldObjectType.WATER_SRC);
    }
}
