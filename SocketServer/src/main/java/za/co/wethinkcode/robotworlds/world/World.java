package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.world.data.WorldData;
import za.co.wethinkcode.robotworlds.world.enums.UpdateResponse;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public int getVisibility() {
        return worldData.getWorldConfigData().getVisibility();
    }

    public int getReload() {
        return worldData.getWorldConfigData().getReload();
    }

    public int getRepair() {
        return worldData.getWorldConfigData().getRepair();
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
                random.nextInt(bottomRight.getX() - topLeft.getX())
                        + topLeft.getX();
    }

    private int getRandomYCoordinate() {
        if ((topLeft.getY() - bottomRight.getY()) < 1) return 0;
        return
                random.nextInt(topLeft.getY() - bottomRight.getY())
                        + bottomRight.getY();
    }

    public boolean isPositionAtWorldEdge(Position p) {
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

        boolean atTopEdge = y == maxWorldY && inXRange;
        boolean atBottomEdge = y == minWorldY && inXRange;
        boolean atLeftEdge = x == minWorldX && inYRange;
        boolean atRightEdge = x == maxWorldX && inYRange;

        return atTopEdge || atBottomEdge || atLeftEdge || atRightEdge;
    }

    private boolean isPositionInsideWorld(Position p) {
        return p.isIn(topLeft, bottomRight);
    }

    public boolean isSpaceAvailableForPosition(Position p) {
        if (!isPositionInsideWorld(p)) return false;

        for (Obstacle obstacle : obstacles) {
            if (obstacle.blocksPosition(p)) return false;
        }

        for (Robot robot : robots) {
            if (robot.getPosition().equals(p)) return false;
        }

        return true;
    }

    public Position getUnoccupiedPosition() {
        Position p;
        int counter = 0;
        while (true) {
            if (counter == 10000) return null;
            p = new Position(getRandomXCoordinate(), getRandomYCoordinate());
            if (isSpaceAvailableForPosition(p)) return p;
            counter++;
        }
    }

    public UpdateResponse moveRobot(Robot robot, Position newPosition) {
        if (!isPositionInsideWorld(newPosition)) return FAILED_OUTSIDE_WORLD;

        Position robotPosition = robot.getPosition();

        for (Obstacle obstacle : obstacles) {
            if (obstacle.blocksPath(robotPosition, newPosition))
                return FAILED_OBSTRUCTED;
        }

        for (Robot worldRobot : robots) {
            if (
                    !robot.equals(worldRobot)
                    && worldRobot.blocksPath(
                            robotPosition,
                            newPosition
                    )
            ) return FAILED_OBSTRUCTED;
        }

        robot.setPosition(newPosition);
        return SUCCESS;
    }

    public void setObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public void addRobotToWorld(Robot robot) {robots.add(robot);}
}
