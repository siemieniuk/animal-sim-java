package com.siemieniuk.animals.core.animals.router;

import com.siemieniuk.animals.core.typing.WorldObjectType;
import com.siemieniuk.animals.math.Coordinates;

public final class PlantSourceRouter extends PreyRouter {
    public PlantSourceRouter(Coordinates currentPos) {
        super(currentPos);
        super.findPlanToNearest(WorldObjectType.PLANT_SRC);
    }
}
