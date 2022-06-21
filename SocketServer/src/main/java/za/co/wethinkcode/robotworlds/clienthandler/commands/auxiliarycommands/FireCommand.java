package za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.enums.Direction;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.LinkedHashMap;
import java.util.Map;

import static za.co.wethinkcode.robotworlds.world.enums.Direction.*;

public class FireCommand extends AuxiliaryCommand {
    private Robot clientRobot;
    private Robot target;
    private World world;

    private int bulletCoordinate;
    private int distance;
    private int robotX;
    private int robotY;

    public FireCommand(String robotName) {
        super(robotName, "fire");
    }

    private boolean hasBulletHitRobot(Position p) {
        for (Robot robot : world.getRobots()) {
            if (!robot.equals(clientRobot) && p.equals(robot.getPosition())) {
                target = robot;
                return true;
            }
        }
        return false;
    }

    private boolean hasBulletHitObstacle(Position p) {
        for (Obstacle obstacle : world.getObstacles()) {
            if (obstacle.blocksPosition(p))
                return true;
        }
        return false;
    }

    private boolean hasBulletHitEdge(Position p) {
        return world.isPositionAtWorldEdge(p);
    }

    private boolean hasBulletHitObject(Position p) {
        return
                hasBulletHitEdge(p)
                || hasBulletHitRobot(p)
                || hasBulletHitObstacle(p);
    }

    private boolean loopCondition(
            int range,
            int coordinate,
            Direction direction
    ) {
        if (direction.equals(NORTH) || direction.equals(EAST))
            return bulletCoordinate <= coordinate + range;
        return bulletCoordinate >= range - coordinate;
    }

    private void incrementBulletCoordinate(Direction direction) {
        if (direction.equals(NORTH) || direction.equals(EAST)) {
            bulletCoordinate++;
        } else {
            bulletCoordinate--;
        }
    }

    private void fire(int range, int coordinate, Direction direction) {
        Position bulletPosition;
        for (
                bulletCoordinate = coordinate;
                loopCondition(range, coordinate, direction);
                incrementBulletCoordinate(direction)
        ) {
            if (direction.equals(NORTH) || direction.equals(SOUTH)) {
                bulletPosition = new Position(robotX, bulletCoordinate);
            } else {
                bulletPosition = new Position(bulletCoordinate, robotY);
            }
            if (hasBulletHitObject(bulletPosition)) {
                distance = Math.abs(coordinate - bulletCoordinate);
                break;
            }
        }
    }

    private void shootXAxis(int range, Direction direction) {
        fire(range, robotX, direction);
    }

    private void shootYAxis(int range, Direction direction) {
        fire(range, robotY, direction);
    }

    private void shootEast(int range) {
        shootXAxis(range, EAST);
    }

    private void shootWest(int range) {
        shootXAxis(range, WEST);
    }

    private void shootNorth(int range) {
        shootYAxis(range, NORTH);
    }

    private void shootSouth(int range) {
        shootYAxis(range, SOUTH);
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
        robotX = clientRobot.getPosition().getX();
        robotY = clientRobot.getPosition().getY();

        int maximumShots = clientRobot.getMaximumShots();

        if (0 == maximumShots) return ServerResponse.gunErrorResponse();

        if (clientRobot.getShots() <= 0)
            return ServerResponse.getSuccessResponse(
                    "message",
                    "Gun has no ammo",
                    clientRobot.getRobotData()
            );

        world = clientHandler.getWorld();

        shoot(clientRobot.getRange());

        if (null == target)
            return ServerResponse.getSuccessResponse(
                    "message",
                    "Miss",
                    clientRobot.getRobotData()
            );

        target.damageRobot();

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
