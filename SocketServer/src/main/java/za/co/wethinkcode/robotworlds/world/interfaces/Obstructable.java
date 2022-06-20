package za.co.wethinkcode.robotworlds.world.interfaces;

import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.object.WorldObject;

public interface Obstructable {

    boolean isObstructable();

    void setObstructable(boolean isObstructable);

    boolean obstructsPosition(Position position);

    boolean obstructsWorldObject(WorldObject worldObject);

    boolean obstructsPath(WorldObject worldObject, Position endPosition);
}
