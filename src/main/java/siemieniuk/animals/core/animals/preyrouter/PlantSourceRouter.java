package siemieniuk.animals.core.animals.preyrouter;

import siemieniuk.animals.core.typing.WorldObjectType;
import siemieniuk.animals.math.Coordinates;

public final class PlantSourceRouter extends PreyRouter {
    public PlantSourceRouter(Coordinates currentPos) {
        super(currentPos);
        super.findPlanToNearest(WorldObjectType.PLANT_SRC);
    }
}
