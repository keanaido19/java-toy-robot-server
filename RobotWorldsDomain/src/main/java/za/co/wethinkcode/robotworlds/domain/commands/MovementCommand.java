package za.co.wethinkcode.robotworlds.domain.commands;

import za.co.wethinkcode.robotworlds.domain.Play;
import za.co.wethinkcode.robotworlds.domain.response.JsonResponse;
import za.co.wethinkcode.robotworlds.domain.world.Position;
import za.co.wethinkcode.robotworlds.domain.world.builders.DataMapBuilder;
import za.co.wethinkcode.robotworlds.domain.world.enums.Direction;
import za.co.wethinkcode.robotworlds.domain.world.enums.UpdateResponse;
import za.co.wethinkcode.robotworlds.domain.world.objects.robots.Robot;

import java.util.LinkedHashMap;
import java.util.List;

import static za.co.wethinkcode.robotworlds.domain.Helpers.getInteger;
import static za.co.wethinkcode.robotworlds.domain.world.enums.Direction.*;
import static za.co.wethinkcode.robotworlds.domain.world.enums.UpdateResponse.FAILED_OBSTRUCTED;

public class MovementCommand extends Command {
    private Robot robot;
    private Direction directionOfMovement;
    private Position newPosition;

    public MovementCommand(
            String robotName,
            String command,
            List<String> commandArguments
    ) {
        super(robotName, command, commandArguments);
    }

    private int getNumberOfSteps() {
        int commandArgument = getInteger(commandArguments.get(0));
        return "back".equals(command) ? -1 * commandArgument: commandArgument;
    }

    private void setPositionAndDirection() {
        int nrSteps = getNumberOfSteps();

        int newX = robot.getPosition().getX();
        int newY = robot.getPosition().getY();

        directionOfMovement = "back".equals(command) ? SOUTH : NORTH;

        switch (robot.getDirection()) {
            case NORTH:
                newY = newY + nrSteps;
                break;
            case SOUTH:
                newY = newY - nrSteps;
                directionOfMovement = "back".equals(command) ? NORTH : SOUTH;
                break;
            case EAST:
                newX = newX + nrSteps;
                directionOfMovement = "back".equals(command) ? WEST : EAST;
                break;
            case WEST:
                newX = newX - nrSteps;
                directionOfMovement = "back".equals(command) ? EAST : WEST;
        }

        newPosition = new Position(newX, newY);
    }

    private JsonResponse getMovementResponse() {
        LinkedHashMap<String, Object> dataMap =
                DataMapBuilder.getDataMap(world, robot);

        UpdateResponse updateResponse =
                world.moveRobot(robot, newPosition);

        String message = updateResponse.getMessage();

        Direction direction =
                world.getEdge(
                        robot.getPosition(),
                        directionOfMovement
                );

        if (
                null != direction
                        && world.isPositionAtWorldEdge(robot.getPosition())
                        && !updateResponse.equals(FAILED_OBSTRUCTED)
        ) {
            message = "At the " + direction + " edge";
        }

        dataMap.put("message", message);

        return JsonResponse.getSuccessResponse(
                dataMap,
                robot.getRobotData()
        );
    }

    @Override
    public JsonResponse execute(Play play) {
        robot = world.getRobot(robotName);

        setPositionAndDirection();

        return getMovementResponse();
    }
}
