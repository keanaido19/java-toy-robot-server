package za.co.wethinkcode.robotworlds.database.objects;

import java.util.List;
import java.util.Objects;

public class WorldDO {
    private final String worldName;
    private final WorldDataDO worldData;
    private final List<WorldObjectDO> obstacles, pits, mines;

    public WorldDO(
            String worldName,
            WorldDataDO worldData,
            List<WorldObjectDO> obstacles,
            List<WorldObjectDO> pits,
            List<WorldObjectDO> mines
    ) {
        this.worldName = worldName;
        this.worldData = worldData;
        this.obstacles = obstacles;
        this.pits = pits;
        this.mines = mines;
    }

    public WorldDO(
            WorldDataDO worldData,
            List<WorldObjectDO> obstacles,
            List<WorldObjectDO> pits,
            List<WorldObjectDO> mines
    ) {
        this("", worldData, obstacles, pits, mines);
    }

    public String getWorldName() {
        return worldName;
    }

    public WorldDataDO getWorldData() {
        return worldData;
    }

    public List<WorldObjectDO> getObstacles() {
        return obstacles;
    }

    public List<WorldObjectDO> getPits() {
        return pits;
    }

    public List<WorldObjectDO> getMines() {
        return mines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorldDO)) return false;
        WorldDO that = (WorldDO) o;
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
