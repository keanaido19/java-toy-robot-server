package za.co.wethinkcode.robotworlds.domain.commands;

import za.co.wethinkcode.robotworlds.domain.Play;
import za.co.wethinkcode.robotworlds.domain.commands.auxilliarycommands.StateCommand;
import za.co.wethinkcode.robotworlds.domain.response.JsonResponse;
import za.co.wethinkcode.robotworlds.domain.world.Position;
import za.co.wethinkcode.robotworlds.domain.world.builders.robotbuilder.RobotBuilder;
import za.co.wethinkcode.robotworlds.domain.world.objects.robots.Robot;

import java.util.List;

import static za.co.wethinkcode.robotworlds.domain.Helpers.getInteger;

public class LaunchCommand extends Command {

    private String robotMake;
    private int maximumShields;
    private int maximumShots;
    private int robotShields;
    private int robotShots;

    public LaunchCommand(String robotName, List<String> commandArguments) {
        super(robotName, "launch", commandArguments);
    }

    private void processCommandArguments() {
        robotShields = maximumShields;
        robotShots = maximumShots;
        if (commandArguments.size() > 0) {
            robotMake = commandArguments.get(0);
            if (commandArguments.size() == 3) {
                robotShields = getInteger(commandArguments.get(1));
                if (robotShields > maximumShields) {
                    robotShields = maximumShields;
                }
                robotShots = getInteger(commandArguments.get(2));
                if (robotShots > maximumShots) {
                    robotShots = maximumShots;
                }
            }
        }
    }

    @Override
    public JsonResponse execute(Play play) {
        Position position = world.getUnoccupiedPosition();

        for (Robot worldRobot : world.getRobots()) {
            if (worldRobot.getName().equals(robotName))
                return JsonResponse.nameErrorResponse();
        }

        if (position == null) return JsonResponse.spaceErrorResponse();

        maximumShields = world.getShields();
        maximumShots = world.getShots();

        processCommandArguments();

        RobotBuilder robotBuilder =
                new RobotBuilder(
                        robotName,
                        robotMake,
                        position,
                        robotShields,
                        robotShots
                );

        Robot robot = robotBuilder.getRobot();

        if (null == robot)
            return JsonResponse.argumentErrorResponse();

        for (Robot worldRobot : world.getRobots()) {
            if (worldRobot.getName().equals(robot.getName()))
                return JsonResponse.nameErrorResponse();
        }

        play.addRobot(robot);
        world.addRobotToWorld(robot);
        robot.joinWorld(world);

        return new StateCommand(robotName).execute(play);
    }
}
