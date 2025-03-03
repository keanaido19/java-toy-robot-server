package za.co.wethinkcode.robotworlds.domain.world.objects.robots;

import za.co.wethinkcode.robotworlds.domain.world.Position;
import za.co.wethinkcode.robotworlds.domain.world.World;
import za.co.wethinkcode.robotworlds.domain.world.data.RobotData;
import za.co.wethinkcode.robotworlds.domain.world.enums.Direction;
import za.co.wethinkcode.robotworlds.domain.world.enums.ObjectType;
import za.co.wethinkcode.robotworlds.domain.world.enums.Status;

import java.util.Objects;

public class Robot{

    private final String name;
    private final int maximumShields;
    private final int maximumShots;
    private final int range;

    private final ObjectType objectType = ObjectType.ROBOT;

    private Direction direction = Direction.NORTH;
    private Status currentStatus = Status.NORMAL;

    private int currentShields;
    private Position position;
    private int currentShots;
    private World world;

    public Robot(
            String name,
            Position position,
            int maximumShields,
            int maximumShots,
            int range
    ) {
        this.name = name;
        this.position = position;
        this.maximumShields = maximumShields;
        this.currentShields = maximumShields;
        this.maximumShots = maximumShots;
        this.currentShots = maximumShots;
        this.range = range;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {return position;}

    public ObjectType getObjectType() {
        return objectType;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getMaximumShields() {
        return maximumShields;
    }

    public int getMaximumShots() {
        return maximumShots;
    }

    public int getRange() {
        return range;
    }

    public int getShields() {
        return currentShields;
    }

    public int getShots() {
        return currentShots;
    }

    public Status getRobotStatus() {
        return currentStatus;
    }

    public RobotData getRobotData() {
        return new RobotData(
                position.getPositionAsList(),
                getDirection(),
                currentShields,
                currentShots,
                currentStatus
        );
    }

    public void turnLeft() {
        direction = direction.getLeftDirection();
    }

    public void turnRight() {
        direction = direction.getRightDirection();
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setRobotStatus(Status status) {
        this.currentStatus = status;
    }

    public void joinWorld(World world) {
        this.world = world;
    }

    public void repair() {
        currentShields = maximumShields;
    }

    public void reload() {
        currentShots = maximumShots;
    }

    public void fire() {
        if (currentShots > 0) {
            currentShots --;
        }
    }

    public void damageRobot() {
        if (world != null) {
            if (currentShields <= 0) {
                currentStatus = Status.DEAD;
                world.getRobots().remove(this);
                world = null;
            } else {
                currentShields --;
            }
        }
    }

    public boolean blocksPath(Position a, Position b){
        int startX = a.getX();
        int startY = a.getY();

        int endX = b.getX();
        int endY = b.getY();

        for(int x = Math.min(startX, endX); x <= Math.max(startX, endX); x++) {
            for (
                    int y = Math.min(startY, endY);
                    y <= Math.max(startY, endY);
                    y++
            ) {
                if (position.equals(new Position(x, y))) return true;
            }
        }
        return false;
    }

    private static void resetRobotStatus(Robot robot, Status status) {
        if (Status.DEAD.equals(robot.getRobotStatus())) return;
        if (status.equals(Status.RELOAD)) robot.reload();
        if (status.equals(Status.REPAIR)) robot.repair();
        if (status.equals(robot.getRobotStatus()))
            robot.setRobotStatus(Status.NORMAL);
    }

    private static synchronized void statusTimer(
            Robot robot,
            Status status,
            int milliSeconds
    ) {
        if (Status.DEAD.equals(robot.getRobotStatus())) return;

        robot.setRobotStatus(status);

        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        resetRobotStatus(robot, status);
    }

    private static class StatusTimer implements Runnable {
        private final Robot robot;
        private final Status status;
        private final int milliSeconds;

        public StatusTimer(Robot robot, Status status, int milliSeconds) {
            this.robot = robot;
            this.status = status;
            this.milliSeconds = milliSeconds;
        }

        @Override
        public void run() {
            Robot.statusTimer(robot, status, milliSeconds);
        }
    }

    public void timer(Status status, int milliSeconds) {
        new Thread(new StatusTimer(this, status, milliSeconds)).start();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Robot)) return false;
        Robot robot = (Robot) o;
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                name,
                maximumShields,
                maximumShots,
                range, objectType,
                direction,
                currentStatus,
                currentShields,
                position,
                currentShots,
                world
        );
    }

    @Override
    public String toString() {
        return " * " + name + " At " + position + ", Facing " +
                getDirection() + ", Current Status is " + currentStatus +
                ", (Shields=" + currentShields + ", Shots=" + currentShots +
                ") * ";
    }
}
