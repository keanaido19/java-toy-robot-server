package za.co.wethinkcode.robotworlds.api.server.world;

public class WorldData {
    private int width, height, visibility, repairTime, reloadTime, mineTime,
            maxShield, maxShots;

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
