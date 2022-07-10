package za.co.wethinkcode.robotworlds.world.builders.robotbuilder;

import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

public abstract class RobotBuilderStrategy {
    protected String robotName;
    protected String make;
    protected Position position;
    protected int robotShields;
    protected int robotShots;

    public RobotBuilderStrategy(
            String robotName,
            String make,
            Position position,
            int robotShields,
            int robotShots
    ) {
        this.robotName = robotName;
        this.make = make;
        this.position = position;
        this.robotShields = robotShields;
        this.robotShots = robotShots;
    }

    public abstract boolean checkRobotMake();

    public abstract Robot getRobot();
}
