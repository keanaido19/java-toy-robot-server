package za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.clienthandler.commands.CommandResult;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.data.WorldObjectData;
import za.co.wethinkcode.robotworlds.world.enums.Direction;
import za.co.wethinkcode.robotworlds.world.enums.ObjectType;
import za.co.wethinkcode.robotworlds.world.objects.WorldObject;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.Boundary;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LookWorldCommand extends AuxiliaryCommand {
    private final List<WorldObject> observedObjects = new ArrayList<>();
    private final List<WorldObject> worldObjects = new ArrayList<>();
    private final List<WorldObjectData> objects = new ArrayList<>();

    private Robot clientRobot;

    public LookWorldCommand(String robotName) {
        super(robotName, "look");
    }

    private void updateObjects(WorldObject o) {
        objects.add(new WorldObjectData(o));
    }

    private void addEdge(Position p) {
        WorldObjectData edge = new WorldObjectData(
                p,
                Direction.NORTH,
                ObjectType.EDGE,
                3,
                3
        );
        objects.add(edge);
    }

    private void addRobot(WorldObject o) {
        Robot r = (Robot) o;
        WorldObjectData robot = new WorldObjectData(r, r.getRobotStatus());
        objects.add(robot);
    }

    private void lookAtPosition(Position p) {
        for (WorldObject o : worldObjects) {
            if (!o.equals(clientRobot)
                    && !observedObjects.contains(o)
                    && o.containsPosition(p)
            ) {
                if (o instanceof Boundary && o.containsPosition(p)) {
                    addEdge(p);
                    continue;
                } else if(o instanceof Robot && o.containsPosition(p)) {
                    addRobot(o);
                } else {
                    updateObjects(o);
                }
                observedObjects.add(o);
            }
        }
    }

    private void lookForObjects(int visibility) {
        int startX = clientRobot.getCenterXCoordinate();
        int startY = clientRobot.getCenterYCoordinate();

        for (int i = startX - visibility; i <= startX + visibility; i++) {
            for (int j = startY - visibility; j <= startY + visibility; j++) {
                lookAtPosition(new Position(i, j));
            }
        }
    }

    @Override
    public synchronized ServerResponse execute(ClientHandler clientHandler) {
        clientRobot = clientHandler.getRobot();
        World world = clientHandler.getWorld();

        worldObjects.add(world.getWorldBoundary());
        worldObjects.addAll(world.getWorldObjects());

        int visibility = world.getWorldData().getVisibility();

        lookForObjects(visibility);

        Map<String, Object> data = new LinkedHashMap<>();

        WorldObject worldMap = new WorldObject(
                new Position(0, 0),
                Direction.NORTH,
                world.getWorldData().getWidth(),
                world.getWorldData().getHeight()
        );

        data.put("world", List.of(new WorldObjectData(worldMap)));

        if (!objects.isEmpty()) {
            data.put("objects", objects);
        }

        data.put(
                "robot",
                List.of(
                        new WorldObjectData(
                                clientRobot,
                                clientRobot.getRobotStatus()
                        )
                )
        );

        return
                new ServerResponse(
                        CommandResult.DRAW,
                        data,
                        clientRobot.getRobotData()
                );
    }
}
