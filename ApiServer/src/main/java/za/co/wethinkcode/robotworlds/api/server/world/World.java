package za.co.wethinkcode.robotworlds.api.server.world;

import java.util.List;

public class World {
    private String worldName;
    private WorldData worldData;
    private List<WorldObject> obstacles, pits, mines;

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public WorldData getWorldData() {
        return worldData;
    }

    public void setWorldData(WorldData worldData) {
        this.worldData = worldData;
    }

    public List<WorldObject> getObstacles() {
        return obstacles;
    }

    public void setObstacles(List<WorldObject> obstacles) {
        this.obstacles = obstacles;
    }

    public List<WorldObject> getPits() {
        return pits;
    }

    public void setPits(List<WorldObject> pits) {
        this.pits = pits;
    }

    public List<WorldObject> getMines() {
        return mines;
    }

    public void setMines(List<WorldObject> mines) {
        this.mines = mines;
    }
}
