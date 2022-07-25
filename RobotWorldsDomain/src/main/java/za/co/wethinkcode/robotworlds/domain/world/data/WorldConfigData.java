package za.co.wethinkcode.robotworlds.domain.world.data;

import java.util.Objects;

public class WorldConfigData {
    int visibility;
    int reload;
    int repair;
    int shields;
    int shots;
    int mine;

    public WorldConfigData(int[] configData) {
        this.visibility = configData[0];
        this.reload = configData[1];
        this.repair = configData[2];
        this.shields = configData[3];
        this.shots = configData[4];
        this.mine = configData[5];
    }

    public int getVisibility() {
        return visibility;
    }

    public int getReload() {
        return reload;
    }

    public int getRepair() {
        return repair;
    }

    public int getShields() {
        return shields;
    }

    public int getShots() {
        return shots;
    }

    public int getMine() {
        return mine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorldConfigData)) return false;
        WorldConfigData that = (WorldConfigData) o;
        return
                visibility == that.visibility
                && reload == that.reload
                && repair == that.repair
                && shields == that.shields
                && shots == that.shots
                && mine == that.mine;
    }

    @Override
    public int hashCode() {
        return Objects.hash(visibility, reload, repair, shields, shots, mine);
    }
}
