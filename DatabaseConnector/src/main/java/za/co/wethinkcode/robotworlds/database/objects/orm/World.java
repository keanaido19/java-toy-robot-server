package za.co.wethinkcode.robotworlds.database.objects.orm;


import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import za.co.wethinkcode.robotworlds.database.dao.world.WorldDaoImpl;
import za.co.wethinkcode.robotworlds.database.objects.orm.worldobject.Mine;
import za.co.wethinkcode.robotworlds.database.objects.orm.worldobject.Obstacle;
import za.co.wethinkcode.robotworlds.database.objects.orm.worldobject.Pit;

@DatabaseTable(tableName = "world", daoClass = WorldDaoImpl.class)
public class World {
    @DatabaseField(generatedId = true)
    private int worldID;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    @DatabaseField(
            foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true,
            canBeNull = false, unique = true
    )
    private WorldData worldData;

    @ForeignCollectionField()
    private ForeignCollection<Obstacle> obstacles;

    @ForeignCollectionField()
    private ForeignCollection<Pit> pits;

    @ForeignCollectionField()
    private ForeignCollection<Mine> mines;

    public World() {}

    public int getWorldID() {
        return worldID;
    }

    public void setWorldID(int worldID) {
        this.worldID = worldID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WorldData getWorldData() {
        return worldData;
    }

    public void setWorldData(WorldData worldData) {
        this.worldData = worldData;
    }

    public ForeignCollection<Obstacle> getObstacles() {
        return obstacles;
    }

    public void setObstacles(ForeignCollection<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public ForeignCollection<Pit> getPits() {
        return pits;
    }

    public void setPits(ForeignCollection<Pit> pits) {
        this.pits = pits;
    }

    public ForeignCollection<Mine> getMines() {
        return mines;
    }

    public void setMines(ForeignCollection<Mine> mines) {
        this.mines = mines;
    }
}

