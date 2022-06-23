package za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.clienthandler.commands.CommandResult;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.data.LookData;
import za.co.wethinkcode.robotworlds.world.enums.Direction;
import za.co.wethinkcode.robotworlds.world.enums.ObjectType;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static za.co.wethinkcode.robotworlds.world.enums.Direction.*;
import static za.co.wethinkcode.robotworlds.world.enums.ObjectType.*;

public class LookCommand extends AuxiliaryCommand {
    private final List<LookData> objects = new ArrayList<>();
    private final List<Object> observedObjects = new ArrayList<>();

    private Robot clientRobot;
    private World world;
    private int robotX;
    private int robotY;
    private int visibility;

    public LookCommand(String robotName) {
        super(robotName, "look");
    }

    private void updateObjects(
            Direction direction,
            ObjectType objectType,
            int distance
    ) {
        if (null != objectType)
            objects.add(new LookData(direction, objectType, distance));
    }

    private ObjectType lookForEdges(Position p) {
        if (world.isPositionAtWorldEdge(p)) return EDGE;
        return null;
    }

    private Obstacle lookForObstacles(Position p) {
        for (Obstacle obstacle : world.getObstacles()) {
            if (obstacle.blocksPosition(p)) return obstacle;
        }
        return null;
    }

    private Robot lookForRobots(Position p) {
        for (Robot robot : world.getRobots()) {
            if (
                    !robot.equals(clientRobot)
                    && robot.getPosition().equals(p)
            ) return robot;
        }
        return null;
    }

    private boolean hasObjectBeenObserved(Object o) {
        if (o != null && !observedObjects.contains(o)) {
            observedObjects.add(o);
            return false;
        }
        return true;
    }

    private List<ObjectType> lookAtPosition(Position p) {
        List<ObjectType> objects = new ArrayList<>();

        objects.add(lookForEdges(p));

        Obstacle obstacle = lookForObstacles(p);
        if (!hasObjectBeenObserved(obstacle)) objects.add(OBSTACLE);

        Robot robot = lookForRobots(p);
        if (!hasObjectBeenObserved(robot)) objects.add(ROBOT);

        return objects;
    }

    private void look(int distance, Direction direction, Position position) {
        List<ObjectType> objects = lookAtPosition(position);
        for (ObjectType objectType : objects) {
            updateObjects(direction, objectType, distance);
        }
    }

    private void lookXAxis(int distance, Direction direction) {
        int x = EAST.equals(direction) ? robotX + distance: robotX - distance;
        Position position = new Position(x, robotY);
        look(distance, direction, position);
    }

    private void lookYAxis(int distance, Direction direction) {
        int y = NORTH.equals(direction) ? robotY + distance: robotY - distance;
        Position position = new Position(robotX, y);
        look(distance, direction, position);
    }

    private void lookEast(int distance) {
        lookXAxis(distance, EAST);
    }

    private void lookWest(int distance) {
        lookXAxis(distance, WEST);
    }

    private void lookNorth(int distance) {
        lookYAxis(distance, NORTH);
    }

    private void lookSouth(int distance) {
        lookYAxis(distance, SOUTH);
    }

    private void lookAtSurroundings() {
        for (int i = 0; i <= visibility; i++) {
            lookNorth(i);
            lookEast(i);
            lookSouth(i);
            lookWest(i);
        }
    }

    @Override
    public ServerResponse execute(ClientHandler clientHandler) {
        clientRobot = clientHandler.getRobot(robotName);
        robotX = clientRobot.getPosition().getX();
        robotY = clientRobot.getPosition().getY();

        world = clientHandler.getWorld();

        visibility = world.getVisibility();

        lookAtSurroundings();

        if (objects.isEmpty())
            return ServerResponse.getSuccessResponse(
                    "message",
                    "Nothing to look at",
                    clientRobot.getRobotData());

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("visibility", clientHandler.getWorld().getVisibility());
        data.put(
                "position",
                clientHandler.getRobot(robotName).getPosition().getPositionAsList()
        );
        data.put("objects", objects);

        return
                new ServerResponse(
                        CommandResult.OK,
                        data,
                        clientHandler.getRobot(robotName).getRobotData()
                );
    }
}
