package za.co.wethinkcode.robotworlds.world.data;

import java.util.Objects;

public class WorldData {
    int width;
    int height;
    WorldConfigData worldConfigData;

    public WorldData(int width, int height, WorldConfigData worldConfigData) {
        this.width = width;
        this.height = height;
        this.worldConfigData = worldConfigData;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public WorldConfigData getWorldConfigData() {return worldConfigData;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorldData)) return false;
        WorldData worldData = (WorldData) o;
        return width == worldData.width
                && height == worldData.height
                && worldConfigData == worldData.worldConfigData;
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, worldConfigData);
    }
}
