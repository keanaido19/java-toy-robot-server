package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.world.data.WorldData;
import za.co.wethinkcode.robotworlds.world.enums.Direction;
import za.co.wethinkcode.robotworlds.world.enums.UpdateResponse;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static za.co.wethinkcode.robotworlds.world.enums.Direction.*;
import static za.co.wethinkcode.robotworlds.world.enums.UpdateResponse.*;

public class World {
    private final List<Robot> robots = new ArrayList<>();
    private final Random random = new Random();
    private final WorldData worldData;

    private List<Obstacle> obstacles = new ArrayList<>();
    private Position bottomRight;
    private Position topLeft;

    public World(WorldData worldData) {
        this.worldData = worldData;
        this.setBounds(worldData.getWidth(), worldData.getHeight());
    }

    private void setBounds(int width, int height) {
        int absWidth = Math.abs(width);
        int absHeight = Math.abs(height);

        topLeft = new Position(-absWidth/2, absHeight/2);
        bottomRight = new Position(absWidth/2, -absHeight/2);
    }

    public WorldData getWorldData() {
        return worldData;
    }

    public int getWidth() { return worldData.getWidth();}

    public int getHeight() { return worldData.getHeight();}

    public int getVisibility() {
        return worldData.getWorldConfigData().getVisibility();
    }

    public int getReload() {
        return worldData.getWorldConfigData().getReload();
    }

    public int getRepair() {
        return worldData.getWorldConfigData().getRepair();
    }

    public int getMine() {
        return worldData.getWorldConfigData().getMine();
    }

    public int getShields() {
        return worldData.getWorldConfigData().getShields();
    }

    public int getShots() {
        return worldData.getWorldConfigData().getShots();
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    public Position getTopLeft() {
        return topLeft;
    }

    public Position getBottomRight() {
        return bottomRight;
    }

    public List<Robot> getRobots() {
        return robots;
    }

    private int getRandomXCoordinate() {
        if ((bottomRight.getX() - topLeft.getX()) < 1) return 0;
        return
                random.nextInt(bottomRight.getX() - topLeft.getX() + 1)
                        + topLeft.getX();
    }

    private int getRandomYCoordinate() {
        if ((topLeft.getY() - bottomRight.getY()) < 1) return 0;
        return
                random.nextInt(topLeft.getY() - bottomRight.getY() + 1)
                        + bottomRight.getY();
    }

    private boolean isPositionAtEdge(
            int coordinate,
            int edgeCoordinate,
            boolean inWorldRange
    ) {
        return coordinate == edgeCoordinate && inWorldRange;
    }

    private boolean isPositionAtEdge(
            int[] values,
            boolean inWorldRange,
            Direction direction
    ) {
        int edgeCoordinate;

        if (NORTH.equals(direction) || EAST.equals(direction)) {
            edgeCoordinate = values[2];
        } else if (SOUTH.equals(direction) || WEST.equals(direction)) {
            edgeCoordinate = values[1];
        } else {
            return false;
        }

        return isPositionAtEdge(values[0], edgeCoordinate, inWorldRange);
    }

    private boolean isPositionAtWorldEdge(Position p, Direction direction) {
        int x = p.getX();
        int y = p.getY();

        int minWorldX = topLeft.getX();
        int maxWorldX = bottomRight.getX();
        int minWorldY = bottomRight.getY();
        int maxWorldY = topLeft.getY();

        boolean inXRange =
                x >= minWorldX && x <= maxWorldX;
        boolean inYRange =
                y >= minWorldY && y <= maxWorldY;

        if (NORTH.equals(direction) || SOUTH.equals(direction)) {
            return
                    isPositionAtEdge(
                            new int[] {y, minWorldY, maxWorldY},
                            inXRange,
                            direction
                    );
        } else if (EAST.equals(direction) || WEST.equals(direction)) {
            return
                    isPositionAtEdge(
                            new int[] {x, minWorldX, maxWorldX},
                            inYRange,
                            direction
                    );
        }

        return false;
    }

    public boolean isPositionAtWorldEdge(Position p) {
        for (Direction direction : Direction.values()) {
            if (isPositionAtWorldEdge(p, direction)) return true;
        }
        return false;
    }

    public Direction getEdge(Position p, Direction direction) {

        if (isPositionAtWorldEdge(p, direction)) return direction;

        for (Direction d : Direction.values()) {
            if (!d.equals(direction) && isPositionAtWorldEdge(p, d)) return d;
        }
        return null;
    }

    private boolean isPositionInsideWorld(Position p) {
        return p.isIn(topLeft, bottomRight);
    }

    public boolean doesObstacleBlockPosition(Position p) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.blocksPosition(p)) return true;
        }
        return false;
    }

    public boolean doesRobotBlockPosition(Position p) {
        for (Robot robot : robots) {
            if (robot.getPosition().equals(p)) return true;
        }
        return false;
    }

    public boolean isSpaceAvailableForPosition(Position p) {
        if (!isPositionInsideWorld(p)) return false;
        if (doesObstacleBlockPosition(p)) return false;
        return !doesRobotBlockPosition(p);
    }

    public Position getUnoccupiedPosition() {
        Position p = new Position(0, 0);
        int counter = 0;
        while (true) {
            if (counter == 10000) return null;
            if (isSpaceAvailableForPosition(p)) return p;
            p = new Position(getRandomXCoordinate(), getRandomYCoordinate());
            counter++;
        }
    }

    public boolean doesObstacleBlockPath(Position start, Position end) {
        for (Obstacle obstacle : obstacles) {
            if (obstacle.blocksPath(start, end))
                return true;
        }
        return false;
    }

    public boolean doesRobotBlockPath(Robot robot, Position end) {
        for (Robot worldRobot : robots) {
            if (
                    !robot.equals(worldRobot)
                            && worldRobot.blocksPath(
                            robot.getPosition(),
                            end
                    )
            ) return true;
        }
        return false;
    }

    public UpdateResponse moveRobot(Robot robot, Position newPosition) {
        if (!isPositionInsideWorld(newPosition)) return FAILED_OUTSIDE_WORLD;

        Position robotPosition = robot.getPosition();

        if (doesObstacleBlockPath(robotPosition, newPosition))
            return FAILED_OBSTRUCTED;

        if (doesRobotBlockPath(robot, newPosition))
            return FAILED_OBSTRUCTED;

        robot.setPosition(newPosition);
        return SUCCESS;
    }

    public void setObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public void addRobotToWorld(Robot robot) {robots.add(robot);}
}
