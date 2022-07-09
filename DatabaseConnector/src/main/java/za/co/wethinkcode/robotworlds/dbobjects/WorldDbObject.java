package za.co.wethinkcode.robotworlds.dbobjects;

import java.util.List;
import java.util.Objects;

public class WorldDbObject {
    private final WorldDataDbObject worldData;
    private final List<WorldObjectDbData> obstacles, pits, mines;

    public WorldDbObject(
            WorldDataDbObject worldData,
            List<WorldObjectDbData> obstacles,
            List<WorldObjectDbData> pits,
            List<WorldObjectDbData> mines
    ) {
        this.worldData = worldData;
        this.obstacles = obstacles;
        this.pits = pits;
        this.mines = mines;
    }

    public WorldDataDbObject getWorldData() {
        return worldData;
    }

    public List<WorldObjectDbData> getObstacles() {
        return obstacles;
    }

    public List<WorldObjectDbData> getPits() {
        return pits;
    }

    public List<WorldObjectDbData> getMines() {
        return mines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorldDbObject)) return false;
        WorldDbObject that = (WorldDbObject) o;
        return worldData.equals(that.worldData)
                && obstacles.equals(that.obstacles)
                && pits.equals(that.pits)
                && mines.equals(that.mines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(worldData, obstacles, pits, mines);
    }
}
