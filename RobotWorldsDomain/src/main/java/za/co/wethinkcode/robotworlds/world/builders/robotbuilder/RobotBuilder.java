package za.co.wethinkcode.robotworlds.world.builders.robotbuilder;

import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class RobotBuilder {
    private final String robotName;
    private final String robotMake;
    private final Position position;
    private final int robotShields;
    private final int robotShots;

    private final List<Class<? extends RobotBuilderStrategy>>
            robotBuilderStrategies =
            List.of(
                    SniperRobotBuilder.class,
                    DefaultRobotBuilder.class
            );

    private final Class<?>[] classArguments =
            new Class[]{
                    String.class,
                    String.class,
                    Position.class,
                    int.class,
                    int.class
    };

    public RobotBuilder(
            String robotName,
            String robotMake,
            Position position,
            int robotShields,
            int robotShots
    ) {
        this.robotName = robotName;
        this.robotMake = robotMake;
        this.position = position;
        this.robotShields = robotShields;
        this.robotShots = robotShots;
    }

    public Robot getRobot() {
        for (Class<? extends RobotBuilderStrategy> robotBuilderStrategy :
                robotBuilderStrategies
        ) {
            try {
                RobotBuilderStrategy x =
                        robotBuilderStrategy
                                .getDeclaredConstructor(classArguments)
                                .newInstance(
                                        robotName,
                                        robotMake,
                                        position,
                                        robotShields,
                                        robotShots
                                );
                if (x.checkRobotMake()) return x.getRobot();
            } catch (
                    NoSuchMethodException
                    | IllegalAccessException
                    | InstantiationException
                    | InvocationTargetException
                            e
            ) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
