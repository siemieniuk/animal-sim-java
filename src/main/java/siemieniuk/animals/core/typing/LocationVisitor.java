package siemieniuk.animals.core.typing;

import siemieniuk.animals.core.locations.*;

public interface LocationVisitor {

    void visitHideout(Hideout hideout);
    void visitIntersection(Intersection intersection);
    void visitPath(Path path);
    void visitPlantSource(PlantSource plantSource);
    void visitWaterSource(WaterSource source);
}
