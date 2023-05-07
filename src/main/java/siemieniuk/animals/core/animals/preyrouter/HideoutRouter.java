package siemieniuk.animals.core.animals.preyrouter;

import siemieniuk.animals.math.Coordinates;
import siemieniuk.animals.core.typing.WorldObjectType;

public final class HideoutRouter extends PreyRouter {
    public HideoutRouter(Coordinates pos) {
        super(pos);
        super.findPlanToNearest(WorldObjectType.HIDEOUT);
    }
}
