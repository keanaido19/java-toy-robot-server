package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.Play;
import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.StateCommand;
import za.co.wethinkcode.robotworlds.response.JsonResponse;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.builders.robotbuilder.RobotBuilder;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.List;

import static za.co.wethinkcode.robotworlds.Helpers.getInteger;

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
    public JsonResponse execute() {
        World world = Play.getWorld();
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

        world.addRobotToWorld(robot);
        robot.joinWorld(world);

        return new StateCommand(robotName).execute();
    }
}
