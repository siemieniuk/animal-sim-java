package com.siemieniuk.animals.core.animals.preyrouter;

import com.siemieniuk.animals.core.typing.WorldObjectType;
import com.siemieniuk.animals.math.Coordinates;

public final class WaterSourceRouter extends PreyRouter {
    public WaterSourceRouter(Coordinates pos) {
        super(pos);
        super.findPlanToNearest(WorldObjectType.WATER_SRC);
    }
}
