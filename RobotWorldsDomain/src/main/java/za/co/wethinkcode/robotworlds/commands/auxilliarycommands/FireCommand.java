package za.co.wethinkcode.robotworlds.commands.auxilliarycommands;

import za.co.wethinkcode.robotworlds.Play;
import za.co.wethinkcode.robotworlds.response.JsonResponse;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.world.enums.Direction;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.LinkedHashMap;
import java.util.Map;

import static za.co.wethinkcode.robotworlds.world.enums.Direction.*;

public class FireCommand extends AuxiliaryCommand {

    private Robot robot;
    private Robot target;

    private int bulletCoordinate;
    private int distance;
    private int robotX;
    private int robotY;

    public FireCommand(String robotName) {
        super(robotName, "fire");
    }

    private boolean hasBulletHitRobot(Position p) {
        for (Robot robot : world.getRobots()) {
            if (!robot.equals(this.robot) && p.equals(robot.getPosition())) {
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
        robot.fire();

        switch (robot.getDirection()) {
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
    public JsonResponse execute(Play play) {
        robot = world.getRobot(robotName);
        robotX = robot.getPosition().getX();
        robotY = robot.getPosition().getY();

        int maximumShots = robot.getMaximumShots();

        if (0 == maximumShots) return JsonResponse.gunErrorResponse();

        if (robot.getShots() <= 0)
            return JsonResponse.getSuccessResponse(
                    "message",
                    "Gun has no ammo",
                    robot.getRobotData()
            );

        shoot(robot.getRange());

        if (null == target)
            return JsonResponse.getSuccessResponse(
                    "message",
                    "Miss",
                    robot.getRobotData()
            );

        target.damageRobot();

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("message", "Hit");
        data.put("robot", target.getName());
        data.put("distance", distance);
        data.put("state", target.getRobotData());

        return
                JsonResponse.getSuccessResponse(
                        data,
                        robot.getRobotData()
                );
    }
}
