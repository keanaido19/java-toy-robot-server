package za.co.wethinkcode.robotworlds.world.object;

import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.enums.ObjectType;

public abstract class WorldObject {

    private Position position;
    private final ObjectType objectType;

    public WorldObject(Position position, ObjectType objectType) {
        this.position = position;
        this.objectType = objectType;
    }

    public Position getPosition() {
        return position;
    }

    public ObjectType getObjectType() {
        return objectType;
    }
}
