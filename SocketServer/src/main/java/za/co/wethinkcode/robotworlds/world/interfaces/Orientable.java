package za.co.wethinkcode.robotworlds.world.interfaces;

import za.co.wethinkcode.robotworlds.world.enums.Direction;

public interface Orientable {
    boolean isOrientable();

    public void setOrientable(boolean isOrientable);

    Direction getDirection();

    void setDirection(Direction direction);

    void turnLeft();

    void turnRight();
}
