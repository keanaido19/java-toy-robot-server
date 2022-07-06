package za.co.wethinkcode.robotworlds.dbobjects;

public class WorldDataDbObject {
    private final int
            width, height, visibility, repairTime, reloadTime, mineTime,
            maxShield;

    public WorldDataDbObject() {
        this(0, 0, 0, 0, 0, 0, 0);
    }

    public WorldDataDbObject(
            int width,
            int height,
            int visibility,
            int repairTime,
            int reloadTime,
            int mineTime,
            int maxShield
    ) {
        this.width = width;
        this.height = height;
        this.visibility = visibility;
        this.repairTime = repairTime;
        this.reloadTime = reloadTime;
        this.mineTime = mineTime;
        this.maxShield = maxShield;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getVisibility() {
        return visibility;
    }

    public int getRepairTime() {
        return repairTime;
    }

    public int getReloadTime() {
        return reloadTime;
    }

    public int getMineTime() {
        return mineTime;
    }

    public int getMaxShield() {
        return maxShield;
    }
}
