package za.co.wethinkcode.robotworlds.world.data;

import java.util.Objects;

public class WorldConfigData {
    int visibility;
    int reload;
    int repair;
    int shields;
    int shots;

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
                && shots == that.shots;
    }

    @Override
    public int hashCode() {
        return Objects.hash(visibility, reload, repair, shields, shots);
    }
}
