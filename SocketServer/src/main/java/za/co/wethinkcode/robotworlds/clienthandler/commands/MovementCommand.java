package za.co.wethinkcode.robotworlds.clienthandler.commands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.enums.Status;
import za.co.wethinkcode.robotworlds.world.enums.UpdateResponse;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.List;

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

        int commandArgument = (int) ((double) commandArguments.get(0));
        int nrSteps =
                "back".equals(command) ? -1 * commandArgument: commandArgument;

        int newX = clientRobot.getCenterXCoordinate();
        int newY = clientRobot.getCenterYCoordinate();

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
                world.moveWorldObject(clientRobot, newPosition);

        clientRobot.setRobotStatus(Status.MOVING);
        clientRobot.timer(Status.MOVING, 66);

        return ServerResponse.getSuccessResponse(
                "message",
                updateResponse.getMessage(),
                clientRobot.getRobotData()
        );
    }
}
