package za.co.wethinkcode.robotworlds.clienthandler.commands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.builders.DataMapBuilder;
import za.co.wethinkcode.robotworlds.world.enums.Direction;
import za.co.wethinkcode.robotworlds.world.enums.UpdateResponse;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.HashMap;
import java.util.List;

import static za.co.wethinkcode.robotworlds.Helpers.getInteger;

public class MovementCommand extends Command{
    public MovementCommand(
            String robotName,
            String command,
            List<Object> commandArguments
    ) {
        super(robotName, command, commandArguments);
    }

    @Override
    public ServerResponse execute(ClientHandler clientHandler) {
        Robot clientRobot = clientHandler.getRobot();
        World world = clientHandler.getWorld();

        int commandArgument = getInteger(commandArguments.get(0));
        int nrSteps =
                "back".equals(command) ? -1 * commandArgument: commandArgument;

        int newX = clientRobot.getPosition().getX();
        int newY = clientRobot.getPosition().getY();

        switch (clientRobot.getDirection()) {
            case NORTH:
                newY = newY + nrSteps;
                break;
            case SOUTH:
                newY = newY - nrSteps;
                break;
            case EAST:
                newX = newX + nrSteps;
                break;
            case WEST:
                newX = newX - nrSteps;
        }

        Position newPosition = new Position(newX, newY);
        UpdateResponse updateResponse =
                world.moveRobot(clientRobot, newPosition);

        HashMap<String, Object> dataMap =
                DataMapBuilder.getDataMap(clientHandler);

        Direction direction = world.getEdge(clientRobot.getPosition());

        if (null != direction) {
            dataMap.put(
                    "message",
                    "At the " + direction + " edge"
            );
        } else {
            dataMap.put("message", updateResponse.getMessage());
        }

        return ServerResponse.getSuccessResponse(
                dataMap,
                clientRobot.getRobotData()
        );
    }
}
