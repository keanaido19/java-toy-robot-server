package za.co.wethinkcode.robotworlds.domain.world.builders.robotbuilder;

import za.co.wethinkcode.robotworlds.domain.world.Position;
import za.co.wethinkcode.robotworlds.domain.world.objects.robots.Robot;

public class DefaultRobotBuilder extends RobotBuilderStrategy {
    public DefaultRobotBuilder(
            String robotName,
            String make,
            Position position,
            int robotShields,
            int robotShots
    ) {
        super(robotName, make, position, robotShields, robotShots);
    }

    @Override
    public boolean checkRobotMake() {
        return "default".equals(make);
    }

    @Override
    public Robot getRobot() {
        return new Robot(
                robotName,
                position,
                robotShields,
                robotShots,
                5
        );
    }
}
