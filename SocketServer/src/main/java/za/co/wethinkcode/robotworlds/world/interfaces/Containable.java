package za.co.wethinkcode.robotworlds.world.interfaces;

public interface Containable {
    boolean isContainable();

    void setContainable(boolean isContainable);

    boolean isContained(Object o);
}
