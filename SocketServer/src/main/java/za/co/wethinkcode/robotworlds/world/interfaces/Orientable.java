package za.co.wethinkcode.robotworlds.world.interfaces;

import za.co.wethinkcode.robotworlds.world.enums.Direction;

public interface Orientable {
    boolean isOrientable();
    Direction getDirection();

    void turnLeft();

    void turnRight();
}
