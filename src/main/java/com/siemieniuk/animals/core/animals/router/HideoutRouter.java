package com.siemieniuk.animals.core.animals.router;

import com.siemieniuk.animals.core.typing.WorldObjectType;
import com.siemieniuk.animals.math.Coordinates;

public final class HideoutRouter extends PreyRouter {
    public HideoutRouter(Coordinates pos) {
        super(pos);
        super.findPlanToNearest(WorldObjectType.HIDEOUT);
    }
}
