package za.co.wethinkcode.robotworlds.clienthandler.commands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.StateCommand;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.builders.robotbuilder.RobotBuilder;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.List;

import static za.co.wethinkcode.robotworlds.Helpers.getInteger;

public class LaunchCommand extends Command{
    private String robotMake;
    private int maximumShields;
    private int maximumShots;
    private int robotShields;
    private int robotShots;

    public LaunchCommand(
            String robotName,
            String command,
            List<Object> commandArguments
    ) {
        super(robotName, command, commandArguments);
    }

    private void processCommandArguments() {
        robotShields = maximumShields;
        robotShots = maximumShots;
        if (commandArguments.size() > 0) {
            robotMake = (String) commandArguments.get(0);
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
    public ServerResponse execute(ClientHandler clientHandler) {
        World world = clientHandler.getWorld();
        Position position = world.getUnoccupiedPosition();

        if (position == null) return ServerResponse.spaceErrorResponse();

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
            return ServerResponse.argumentErrorResponse();

        for (Robot worldRobot : world.getRobots()) {
            if (worldRobot.getName().equals(robot.getName()))
                return ServerResponse.nameErrorResponse();
        }

        if (null != clientHandler.getRobot())
            world.getRobots().remove(clientHandler.getRobot());

        clientHandler.setRobot(robot);
        world.addRobotToWorld(robot);
        robot.joinWorld(world);

        return new StateCommand(robotName).execute(clientHandler);
    }
}
