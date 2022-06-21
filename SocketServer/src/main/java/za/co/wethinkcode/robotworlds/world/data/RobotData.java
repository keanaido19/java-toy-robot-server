package za.co.wethinkcode.robotworlds.world.data;

import za.co.wethinkcode.robotworlds.world.enums.Direction;
import za.co.wethinkcode.robotworlds.world.enums.Status;

import java.util.List;
import java.util.Objects;

public class RobotData {
    List<Integer> position;
    Direction direction;
    int shields;
    int shots;
    Status status;

    public RobotData(
            List<Integer> position,
            Direction direction,
            int shields,
            int shots, Status status
    ) {
        this.position = position;
        this.direction = direction;
        this.shields = shields;
        this.shots = shots;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RobotData)) return false;
        RobotData robotData = (RobotData) o;
        return shields == robotData.shields
                && shots == robotData.shots
                && position.equals(robotData.position)
                && direction == robotData.direction
                && status == robotData.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, direction, shields, shots, status);
    }
}
