package za.co.wethinkcode.robotworlds.robot;

import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.world.SquareObstacle;
import za.co.wethinkcode.robotworlds.world.World;

public abstract class Robot  {

    public static final Direction STARTDIRECTION = Direction.NORTH;
    private Direction currentDirection;
    private final String robotName;
    private final String robotType;
    private final World world;
    private Position currentPosition;
    protected int shields;
    protected int shots;
    protected int shotDistance;
    protected int maxShields;
    protected int maxShots;
    private String status;

    public Robot(World world, String robotName, String robotType){
        this.currentPosition= new Position(0,0);
        this.currentDirection = STARTDIRECTION;
        this.robotName = robotName;
        this.robotType = robotType;
        this.world = world;
        this.status = "normal";
    }
    public void setRobotPosition(int x , int y){
        currentPosition = new Position(x , y);
    }

    public enum UpdateResponse {
        SUCCESS,
        FAILED_OUTSIDE_WORLD,
        FAILED_OBSTRUCTED
    }

    public String getRobotName(){
        return robotName;
    }

    public Direction getCurrentDirection(){
        return currentDirection;
    }

    public String getRobotState(){
        return "Position [" + currentPosition.getX() + "," + currentPosition.getY() + "] \n" +
                "Direction [" + currentDirection + "]";
    }

    public int getShields() {
        return shields;
    }

    public int getShots() {
        return shots;
    }

    public String getStatus() {
        return status;
    }

    public int getShotDistance(){
        return shotDistance;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public UpdateResponse updatePosition(int steps) {

        int newX = this.currentPosition.getX();
        int newY = this.currentPosition.getY();

        switch (currentDirection) {
            case NORTH:
                if(blockCheckNorth(steps)){
                    return UpdateResponse.FAILED_OBSTRUCTED;
                }
                newY = newY + steps;
                break;
            case SOUTH:
                steps *= -1;
                if(blockCheckSouth(steps)){
                    return UpdateResponse.FAILED_OBSTRUCTED;
                }
                newY = newY - steps;
                break;
            case EAST:
                if(blockCheckEast(steps)){
                    return UpdateResponse.FAILED_OBSTRUCTED;
                }
                newX = newX + steps;
                break;
            case WEST:
                steps *= -1;
                if(blockCheckWest(steps)){
                    return UpdateResponse.FAILED_OBSTRUCTED;
                }
                newX = newX - steps;
                break;
        }
        Position newPosition = new Position(newX, newY);

        if (isNewPositionAllowed(newPosition)) {
            this.currentPosition = newPosition;
            return UpdateResponse.SUCCESS;
        }
        return UpdateResponse.FAILED_OUTSIDE_WORLD;
    }

    public void updateDirection(boolean right){
        if(right){
            switch (currentDirection){
                case NORTH:
                    currentDirection = Direction.EAST;
                    break;
                case EAST:
                    currentDirection = Direction.SOUTH;
                    break;
                case SOUTH:
                    currentDirection = Direction.WEST;
                    break;
                case WEST:
                    currentDirection = Direction.NORTH;
                    break;
            }
        }else {
            switch (currentDirection){
                case NORTH:
                    currentDirection = Direction.WEST;
                    break;
                case WEST:
                    currentDirection = Direction.SOUTH;
                    break;
                case SOUTH:
                    currentDirection = Direction.EAST;
                    break;
                case EAST:
                    currentDirection = Direction.NORTH;
                    break;
            }
        }
    }

    public boolean blockCheckNorth(int steps){
        Position position;
        for(int i = 1 ; i < Math.abs(steps) + 1 ; i++){
            if(steps > 0){
                position = new Position(this.currentPosition.getX(), this.currentPosition.getY()+ i);
            }
            else{
                position = new Position(this.currentPosition.getX(), this.currentPosition.getY()+ (-1*i));
            }
            for (SquareObstacle obstacle : world.getObstacles()) {
                if (obstacle.blocksPosition(position)) {
                    return true;
                }
            }
            for(Robot robot : world.getRobots()){
                if(!robot.getRobotName().equals(this.robotName)){
                    if(robot.robotBlocksPosition(position, robot)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean blockCheckWest(int steps){
        Position position;
        for(int i = 1 ; i < Math.abs(steps) + 1 ; i++){
            if(steps > 0){
                position = new Position(this.currentPosition.getX()+ i, this.currentPosition.getY());
            }
            else{
                position = new Position(this.currentPosition.getX()+ (-1*i), this.currentPosition.getY());
            }
            for (SquareObstacle obstacle : world.getObstacles()) {
                if (obstacle.blocksPosition(position)) {
                    return true;
                }
            }
            for(Robot robot : world.getRobots()){
                if(!robot.getRobotName().equals(this.robotName)){
                    if(robot.robotBlocksPosition(position, robot)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean blockCheckEast(int steps){
        Position position;
        for(int i = 1 ; i < Math.abs(steps) + 1 ; i++){
            if(steps > 0){
                position = new Position(this.currentPosition.getX()+ i, this.currentPosition.getY());
            }
            else{
                position = new Position(this.currentPosition.getX()+ (-1*i), this.currentPosition.getY());
            }
            for (SquareObstacle obstacle : world.getObstacles()) {
                if (obstacle.blocksPosition(position)) {
                    return true;
                }
            }
            for(Robot robot : world.getRobots()){
                if(!robot.getRobotName().equals(this.robotName)){
                    if(robot.robotBlocksPosition(position, robot)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean blockCheckSouth(int steps){
        Position position;
        for(int i = 1 ; i < Math.abs(steps) + 1 ; i++){
            if(steps > 0){
                position = new Position(this.currentPosition.getX(), this.currentPosition.getY()+ i);
            }
            else{
                position = new Position(this.currentPosition.getX(), this.currentPosition.getY()+ (-1*i));
            }
            for (SquareObstacle obstacle : world.getObstacles()) {
                if (obstacle.blocksPosition(position)) {
                    return true;
                }
            }
            for(Robot robot : world.getRobots()){
                if(!robot.getRobotName().equals(this.robotName)){
                    if(robot.robotBlocksPosition(position, robot)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean robotBlocksPosition(Position position, Robot robot) {
        if(robot.getCurrentPosition().getX() == position.getX() && robot.getCurrentPosition().getY() == position.getY()){
                return true;
        }
        return false;
    }

    public boolean isNewPositionAllowed(Position position) {
        if (position.isIn(world.getTopLeft(), world.getBottomRight())) {
            return true;
        }
        return false;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void loseShield(){
        this.shields -= 1;
    }

    public void loseShot(){
        this.shots -= 1;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void reloadShots(){
        this.shots = maxShots;
    }

    public void repairShields(){
        this.shields = maxShields;
    }

    public void setShotDistance(int shotDistance) {
        this.shotDistance = shotDistance;
    }
}
