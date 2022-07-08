package za.co.wethinkcode.robotworlds.dbobjects.doobjects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "worldData")
public class WorldDataDO {
    @DatabaseField(generatedId = true)
    private int worldDataID;

    @DatabaseField(canBeNull = false)
    private int width;

    @DatabaseField(canBeNull = false)
    private int height;

    @DatabaseField(canBeNull = false)
    private int visibility;

    @DatabaseField(canBeNull = false)
    private int repairTime;

    @DatabaseField(canBeNull = false)
    private int reloadTime;

    @DatabaseField(canBeNull = false)
    private int mineTime;

    @DatabaseField(canBeNull = false)
    private int maxShield;

    @DatabaseField(canBeNull = false)
    private int maxShots;

    public WorldDataDO() {}

    public int getWorldDataID() {
        return worldDataID;
    }

    public void setWorldDataID(int worldDataID) {
        this.worldDataID = worldDataID;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public int getRepairTime() {
        return repairTime;
    }

    public void setRepairTime(int repairTime) {
        this.repairTime = repairTime;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public int getMineTime() {
        return mineTime;
    }

    public void setMineTime(int mineTime) {
        this.mineTime = mineTime;
    }

    public int getMaxShield() {
        return maxShield;
    }

    public void setMaxShield(int maxShield) {
        this.maxShield = maxShield;
    }

    public int getMaxShots() {
        return maxShots;
    }

    public void setMaxShots(int maxShots) {
        this.maxShots = maxShots;
    }
}

