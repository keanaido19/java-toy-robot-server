package za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.data.LookData;
import za.co.wethinkcode.robotworlds.world.enums.Direction;
import za.co.wethinkcode.robotworlds.world.enums.ObjectType;
import za.co.wethinkcode.robotworlds.world.objects.WorldObject;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.Boundary;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.ArrayList;
import java.util.List;

public class LookCommand extends AuxiliaryCommand {
    private final List<LookData> objects = new ArrayList<>();
    private final List<WorldObject> worldObjects  = new ArrayList<>();
    private final List<WorldObject> observedObjects = new ArrayList<>();

    private Boundary worldBoundary;
    private Robot clientRobot;
    private int visibility;

    public LookCommand(String robotName) {
        super(robotName, "look");
    }

    private void updateObjects(
            Direction direction,
            ObjectType objectType,
            int distance
    ) {
        objects.add(new LookData(direction, objectType, distance));
    }

    private ObjectType lookAtPosition(Position p) {
        for (WorldObject o : worldObjects) {
            if (!observedObjects.contains(o) && o.containsPosition(p)) {
                observedObjects.add(o);
                return o.getObjectType();
            }
        }
        return null;
    }

    private void look(int distance, Direction direction, Position position) {
        ObjectType objectType = lookAtPosition(position);
        if (objectType != null) updateObjects(direction, objectType, distance);
    }

    private void lookEast(int distance) {
        Direction direction = Direction.EAST;
        int yStart = clientRobot.getMinimumYCoordinate();
        int yEnd = clientRobot.getMaximumYCoordinate();

        for (int i = yStart; i <= yEnd ; i++) {
            Position position =
                    new Position(
                            clientRobot.getMaximumXCoordinate() + distance,
                            i
                    );
            look(distance, direction, position);
        }
        observedObjects.remove(worldBoundary);
    }

    private void lookWest(int distance) {
        Direction direction = Direction.WEST;
        int yStart = clientRobot.getMinimumYCoordinate();
        int yEnd = clientRobot.getMaximumYCoordinate();

        for (int i = yStart; i <= yEnd ; i++) {
            Position position =
                    new Position(
                            clientRobot.getMinimumXCoordinate() - distance,
                            i
                    );
            look(distance, direction, position);
        }
        observedObjects.remove(worldBoundary);
    }

    private void lookNorth(int distance) {
        Direction direction = Direction.NORTH;
        int xStart = clientRobot.getMinimumXCoordinate();
        int xEnd = clientRobot.getMaximumXCoordinate();

        for (int i = xStart; i <= xEnd ; i++) {
            Position position =
                    new Position(
                            i,
                            clientRobot.getMaximumYCoordinate() + distance
                    );
            look(distance, direction, position);
        }
        observedObjects.remove(worldBoundary);
    }

    private void lookSouth(int distance) {
        Direction direction = Direction.SOUTH;
        int xStart = clientRobot.getMinimumXCoordinate();
        int xEnd = clientRobot.getMaximumXCoordinate();

        for (int i = xStart; i <= xEnd ; i++) {
            Position position =
                    new Position(
                            i,
                            clientRobot.getMinimumYCoordinate() - distance
                    );
            look(distance, direction, position);
        }
        observedObjects.remove(worldBoundary);
    }

    private void lookForObjects() {
        for (int i = 1; i <= visibility; i++) {
            lookNorth(i);
            lookEast(i);
            lookSouth(i);
            lookWest(i);
        }
    }

    @Override
    public ServerResponse execute(ClientHandler clientHandler) {
        clientRobot = clientHandler.getRobot();
        World world = clientHandler.getWorld();

        worldBoundary = world.getWorldBoundary();
        worldObjects.add(worldBoundary);
        worldObjects.addAll(world.getWorldObjects());

        visibility = world.getWorldData().getVisibility();

        lookForObjects();

        if (objects.isEmpty())
            return ServerResponse.getSuccessResponse(
                    "message",
                    "Nothing to look at",
                    clientRobot.getRobotData());

        return ServerResponse.getSuccessResponse(
                    "objects",
                    objects,
                    clientRobot.getRobotData());
    }
}
