package za.co.wethinkcode.robotworlds.dbobjects;

import java.util.Objects;

public class WorldObjectDbData {
    private final int width, height, x, y;

    public WorldObjectDbData(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorldObjectDbData)) return false;
        WorldObjectDbData that = (WorldObjectDbData) o;
        return width == that.width
                && height == that.height
                && x == that.x
                && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, x, y);
    }
}
