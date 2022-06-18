package za.co.wethinkcode.robotworlds.world.interfaces;

import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.object.WorldObject;

public interface Obstructable {

    boolean isObstructable();

    boolean obstructsPosition(WorldObject worldObject);

    boolean obstructsPath(
            WorldObject worldObject,
            Position startPosition,
            Position endPosition
    );
}
