package za.co.wethinkcode.robotworlds.clienthandler.commands;

import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.data.WorldData;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;
import za.co.wethinkcode.robotworlds.world.builders.robotbuilder.RobotBuilder;
import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
                robotShields = (int) ((double) commandArguments.get(1));
                if (robotShields > maximumShields) {
                    robotShields = maximumShields;
                }
                robotShots = (int) ((double) commandArguments.get(2));
                if (robotShots > maximumShots) {
                    robotShots = maximumShots;
                }
            }
        }
    }

    private ServerResponse createServerResponse(
            Robot robot,
            WorldData worldData
    ) {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("position", robot.getCenterPosition().getPositionAsList());
        data.put("visibility", worldData.getVisibility());
        data.put("reload", worldData.getReloadTime());
        data.put("repair", worldData.getRepairTime());
        data.put("shields", worldData.getShields());

        return new ServerResponse(CommandResult.OK, data, robot.getRobotData());
    }

    @Override
    public ServerResponse execute(ClientHandler clientHandler) {
        World world = clientHandler.getWorld();
        WorldData worldData = world.getWorldData();
        Position position = world.getUnoccupiedPosition(4);

        if (position == null) return ServerResponse.spaceErrorResponse();

        maximumShields = worldData.getShields();
        maximumShots = worldData.getShots();

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

        if (world.isObjectInWorld(robot))
            return ServerResponse.nameErrorResponse();

        if (null != clientHandler.getRobot()) {
            Robot clientRobot = clientHandler.getRobot();
            world.removeObjectFromWorld(clientRobot);
        }

        clientHandler.setRobot(robot);
        world.addObjectToWorld(robot);
        robot.joinWorld(world);

        return createServerResponse(robot, worldData);
    }
}
