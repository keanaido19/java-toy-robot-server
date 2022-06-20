package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.world.data.WorldData;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    private final List<Robot> robots = new ArrayList<>();
    private final Random random = new Random();
    private final WorldData worldData;

    private List<Obstacle> obstacles;

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
        return
                random.nextInt(bottomRight.getX()- topLeft.getX())
                        + topLeft.getX();
    }

    private int getRandomYCoordinate() {
        return
                random.nextInt(topLeft.getY() - bottomRight.getY())
                        + bottomRight.getY();
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
            if (robot.getCurrentPosition().equals(p)) return false;
        }

        return true;
    }

    public Position getUnoccupiedPosition() {
        Position p;
        int counter = 0;
        while (true) {
            if (counter == 10000) return null;
            p = new Position(getRandomXCoordinate(), getRandomYCoordinate());;
            if (isSpaceAvailableForPosition(p)) return p;
            counter++;
        }
    }

    public void addRobotToWorld(Robot robot) {robots.add(robot);}

    public void addObstacleToWorld(Obstacle obstacle) {obstacles.add(obstacle);}

    public void showObstacles() {
        System.out.println("There are some obstacles");
        for (Obstacle obstacle : obstacles) {
            System.out.println(
                    "- At position " + obstacle.getBottomLeftX() + "," +
                            obstacle.getBottomLeftY() + " (to " +
                            (obstacle.getBottomLeftX() +
                                    obstacle.getSize() - 1) + "," +
                            (obstacle.getBottomLeftY() +
                                    obstacle.getSize() - 1) + ")");
        }
    }
}
