package za.co.wethinkcode.robotServer;

import za.co.wethinkcode.robotServer.World.World;

public class Robot {

    protected final Position TOP_LEFT = new Position(-5, 5);
    protected final Position BOTTOM_RIGHT = new Position(5, -5);
    public static final Direction STARTDIRECTION = Direction.NORTH;
    private Direction currentDirection;
    private String robotName;
    private String robotType;
    private World world;
    private Position currentPosition;
    private int shields;
    private int shots;
    private int shotDistance;
    private String status;

    public Robot(World world, String robotName, String robotType){
        this.currentPosition= new Position(0,0);
        this.currentDirection = STARTDIRECTION;
        this.robotName = robotName;
        this.robotType = robotType;
        this.world = world;
        this.shields = 0;
        this.shots = 3;
        this.shotDistance = 3;
        this.status = "normal";
    }
    public void setRobotPosition(int x , int y){
        currentPosition = new Position(x , y);
    }


//    public UpdateResponse updatePosition(int nrSteps) {
//
//        int newX = this.position.getX();
//        int newY = this.position.getY();
//
//        if (za.co.wethinkcode.toyrobot.world.IWorld.Direction.UP.equals(this.currentDirection)) {
//            newY = newY + nrSteps;
//        } else if (za.co.wethinkcode.toyrobot.world.IWorld.Direction.RIGHT.equals(this.currentDirection)) {
//            newX = newX + nrSteps;
//        } else if (za.co.wethinkcode.toyrobot.world.IWorld.Direction.DOWN.equals(this.currentDirection)) {
//            newY = newY - nrSteps;
//        } else if (za.co.wethinkcode.toyrobot.world.IWorld.Direction.LEFT.equals(this.currentDirection)) {
//            newX = newX - nrSteps;
//        }
//
//        Position newPosition = new Position(newX, newY);
//        for(int i=0; i< obstacles.size(); i++){
//            if(obstacles.get(i).blocksPath(this.position, newPosition)){
//                return UpdateResponse.FAILED_OBSTRUCTED;
//            }
//        }
//        if (isNewPositionAllowed(newPosition)) {
//            this.position = newPosition;
//            return UpdateResponse.SUCCESS;
//        }
//        return UpdateResponse.FAILED_OUTSIDE_WORLD;
//    }

    enum UpdateResponse {
        SUCCESS, //position was updated successfully
        FAILED_OUTSIDE_WORLD, //robot will go outside world limits if allowed, so it failed to update the position
        FAILED_OBSTRUCTED_OBSTACLE, //robot obstructed by at least one obstacle, thus cannot proceed.
        FAILED_OBSTRUCTED_ROBOT
    }

    public String getRobotName(){
        return robotName;
    }

    public String getRobotType(){
        return robotType;
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

    public void updatePosition(int steps) {
        switch (currentDirection) {
            case NORTH:
                currentPosition = new Position(currentPosition.getX(), currentPosition.getY() + steps);
                break;
            case SOUTH:
                currentPosition = new Position(currentPosition.getX(), currentPosition.getY() - steps);
                break;
            case EAST:
                currentPosition = new Position(currentPosition.getX() + steps, currentPosition.getY());
                break;
            case WEST:
                currentPosition = new Position(currentPosition.getX() - steps, currentPosition.getY());
                break;
        }

    }

    public void updateDirection(boolean right){
        //This section is to account for if the robot is turning right.
        //If it is facing NORTH and turns to the right is then facing east and so forth
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
            //This section is to account for if the robot is turning left.
            //If it is facing NORTH and turns to the left is then facing west and so forth
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


}
