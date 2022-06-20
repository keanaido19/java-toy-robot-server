package za.co.wethinkcode.robotworlds.world.data;

import za.co.wethinkcode.robotworlds.world.enums.Direction;
import za.co.wethinkcode.robotworlds.world.enums.ObjectType;

import java.util.Objects;

public class LookData {
    Direction direction;
    ObjectType type;
    int distance;

    public LookData(
            Direction direction,
            ObjectType type,
            int distance
    ) {
        this.direction = direction;
        this.type = type;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LookData)) return false;
        LookData lookData = (LookData) o;
        return distance == lookData.distance
                && direction == lookData.direction
                && type == lookData.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, type, distance);
    }
}
