package za.co.wethinkcode.robotworlds.database.objects;

import java.util.Objects;

public class WorldDataDO {
    private final int
            width, height, visibility, repairTime, reloadTime, mineTime,
            maxShield, maxShots;

    public WorldDataDO(int[] worldData) {
        this.width = worldData[0];
        this.height = worldData[1];
        this.visibility = worldData[2];
        this.repairTime = worldData[3];
        this.reloadTime = worldData[4];
        this.mineTime = worldData[5];
        this.maxShield = worldData[6];
        this.maxShots = worldData[7];
    }

    public WorldDataDO() {
        this(new int[8]);
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

    public int getMaxShots() {
        return maxShots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorldDataDO)) return false;
        WorldDataDO that = (WorldDataDO) o;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                width,
                height,
                visibility,
                repairTime,
                reloadTime,
                mineTime,
                maxShield,
                maxShots
        );
    }
}
