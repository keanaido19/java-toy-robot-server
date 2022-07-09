package za.co.wethinkcode.robotworlds.dbobjects.doobjects;


import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import za.co.wethinkcode.robotworlds.dao.world.WorldDaoImpl;
import za.co.wethinkcode.robotworlds.dbobjects.doobjects.worldobject.MineDO;
import za.co.wethinkcode.robotworlds.dbobjects.doobjects.worldobject.ObstacleDO;
import za.co.wethinkcode.robotworlds.dbobjects.doobjects.worldobject.PitDO;

@DatabaseTable(tableName = "world", daoClass = WorldDaoImpl.class)
public class WorldDO {
    @DatabaseField(generatedId = true)
    private int worldID;

    @DatabaseField(canBeNull = false, unique = true)
    private String name;

    @DatabaseField(
            foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true,
            canBeNull = false, unique = true
    )
    private WorldDataDO worldDataDO;

    @ForeignCollectionField()
    private ForeignCollection<ObstacleDO> obstacleDOS;

    @ForeignCollectionField()
    private ForeignCollection<PitDO> pitDOS;

    @ForeignCollectionField()
    private ForeignCollection<MineDO> mineDOS;

    public WorldDO() {}

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

    public WorldDataDO getWorldData() {
        return worldDataDO;
    }

    public void setWorldData(WorldDataDO worldDataDO) {
        this.worldDataDO = worldDataDO;
    }

    public ForeignCollection<ObstacleDO> getObstacles() {
        return obstacleDOS;
    }

    public void setObstacles(ForeignCollection<ObstacleDO> obstacleDOS) {
        this.obstacleDOS = obstacleDOS;
    }

    public ForeignCollection<PitDO> getPits() {
        return pitDOS;
    }

    public void setPits(ForeignCollection<PitDO> pitDOS) {
        this.pitDOS = pitDOS;
    }

    public ForeignCollection<MineDO> getMines() {
        return mineDOS;
    }

    public void setMines(ForeignCollection<MineDO> mineDOS) {
        this.mineDOS = mineDOS;
    }
}

