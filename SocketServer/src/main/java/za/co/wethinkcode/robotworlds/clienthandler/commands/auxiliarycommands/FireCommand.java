package za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.enums.Status;
import za.co.wethinkcode.robotworlds.world.objects.WorldObject;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.Boundary;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FireCommand extends AuxiliaryCommand {
    private List<WorldObject> worldObjects;

    private WorldObject target;
    private Boundary worldBoundary;
    private Robot clientRobot;
    private int distance;


    public FireCommand(String robotName) {
        super(robotName, "fire");
    }

    private boolean isBulletTouchingWorldObject(Position p) {

        if (worldBoundary.containsPosition(p)) return true;

        for (WorldObject o : worldObjects) {
            if (o.containsPosition(p)) {
                target = o;
                return true;
            }
        }
        return false;
    }

    private void shootEast(int range) {
        Position bulletPosition;
        int x = clientRobot.getMaximumXCoordinate() + 1;
        int y = clientRobot.getCenterYCoordinate();

        for (int i = x; i <= x + range; i++) {
            bulletPosition = new Position(i, y);
            if (isBulletTouchingWorldObject(bulletPosition)) {
                distance = i - clientRobot.getMaximumXCoordinate();
                break;
            }
        }
    }

    private void shootWest(int range) {
        Position bulletPosition;
        int x = clientRobot.getMinimumXCoordinate() - 1;
        int y = clientRobot.getCenterYCoordinate();

        for (int i = x; i >= x - range; i--) {
            bulletPosition = new Position(i, y);
            if (isBulletTouchingWorldObject(bulletPosition)) {
                distance = clientRobot.getMinimumXCoordinate() - i;
                break;
            }
        }
    }

    private void shootNorth(int range) {
        Position bulletPosition;
        int x = clientRobot.getCenterXCoordinate();
        int y = clientRobot.getMaximumYCoordinate() + 1;

        for (int i = y; i <= y + range; i++) {
            bulletPosition = new Position(x, i);
            if (isBulletTouchingWorldObject(bulletPosition)) {
                distance = i - clientRobot.getMaximumYCoordinate();
                break;
            }
        }
    }

    private void shootSouth(int range) {
        Position bulletPosition;
        int x = clientRobot.getCenterXCoordinate();
        int y = clientRobot.getMinimumYCoordinate() - 1;

        for (int i = y; i >= y - range; i--) {
            bulletPosition = new Position(x, i);
            if (isBulletTouchingWorldObject(bulletPosition)) {
                distance = clientRobot.getMinimumYCoordinate() - i;
                break;
            }
        }
    }

    private void shoot(int range) {
        clientRobot.fire();

        switch (clientRobot.getDirection()) {
            case NORTH:
                shootNorth(range);
                break;
            case EAST:
                shootEast(range);
                break;
            case SOUTH:
                shootSouth(range);
                break;
            case WEST:
                shootWest(range);
                break;
        }
    }

    @Override
    public ServerResponse execute(ClientHandler clientHandler) {
        clientRobot = clientHandler.getRobot();

        int maximumShots = clientRobot.getMaximumShots();

        if (0 == maximumShots) return ServerResponse.gunErrorResponse();

        if (clientRobot.getShots() <= 0)
            return ServerResponse.getSuccessResponse(
                    "message",
                    "Gun has no ammo",
                    clientRobot.getRobotData()
            );

        World world = clientHandler.getWorld();

        worldObjects = world.getWorldObjects();
        worldBoundary = world.getWorldBoundary();

        shoot(clientRobot.getRange());

        clientRobot.setRobotStatus(Status.FIRE);
        clientRobot.timer(Status.FIRE, 66);

        if (!(target instanceof Robot))
            return ServerResponse.getSuccessResponse(
                    "message",
                    "Miss",
                    clientRobot.getRobotData()
            );

        Robot target = (Robot) this.target;
        target.damageRobot();

        target.timer(Status.DAMAGED, 66);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("message", "Hit");
        data.put("robot", target.getName());
        data.put("distance", distance);
        data.put("state", target.getRobotData());

        return
                ServerResponse.getSuccessResponse(
                        data,
                        clientRobot.getRobotData()
                );
    }
}
