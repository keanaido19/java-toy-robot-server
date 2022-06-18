package za.co.wethinkcode.robotworlds.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import za.co.wethinkcode.robotworlds.ClientHandler;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.world.Obstacle;
import za.co.wethinkcode.robotworlds.world.World;

public class Fire extends ClientCommands {

    public int shotDistance;
    public int obstacleDistance = 100000;
    public Fire(String name) {
        super("fire", name);
        this.shotDistance = 0;
    }

    @Override
    public String execute(World world, String[] arguments, ClientHandler clientHandler) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        Robot robot = null;
        for (int i = 0; i < world.getRobots().size(); i++) {
            if (getArgument().equals(world.getRobots().get(i).getRobotName())) {
                robot = world.getRobots().get(i);
                if(robot.getShots() == 0){
                    return "You have no shots left please reload.";
                }
                world.getRobots().get(i).loseShot();
            }
        }
        for (int i = 0; i < world.getRobots().size(); i++) {
            if (!getArgument().equals(world.getRobots().get(i).getRobotName())) {
                assert robot != null;
                if (hitCheck(robot.getShotDistance(), robot, world,
                        world.getRobots().get(i))) {
                    world.getRobots().get(i).loseShield();
                    if(world.getRobots().get(i).getShields() == -1){
                        world.getRobots().get(i).setStatus("Destroyed");
                    }
                    StateJson stateJson = new StateJson(robot.getShots());
                    int[] position = {world.getRobots().get(i).getCurrentPosition().getX(), world.getRobots().get(i).getCurrentPosition().getY()};
                    EnemyRobotStateJson enemyRobotStateJson = new EnemyRobotStateJson(position, world.getRobots().get(i).getCurrentDirection().toString(),
                            world.getRobots().get(i).getShields(), world.getRobots().get(i).getShots(), world.getRobots().get(i).getStatus());
                    DataJson dataJson = new DataJson("Hit", shotDistance, world.getRobots().get(i).getRobotName(), enemyRobotStateJson);
                    HitJson hitJson = new HitJson("OK", dataJson, stateJson);
                    robotDestroyed(world.getRobots().get(i));

                    return gson.toJson(hitJson);
                }
        }
    }
        for (int i = 0; i < world.getRobots().size(); i++) {
            if (getArgument().equals(world.getRobots().get(i).getRobotName())) {
                StateJson missStateJson = new StateJson(world.getRobots().get(i).getShots());
                MissDataJson missDataJson = new MissDataJson("Miss");
                MissJson missJson = new MissJson("OK", missDataJson, missStateJson);
                return gson.toJson(missJson);
            }
        }
        return "Nothing";
    }

    public boolean hitCheck(int shotLength, Robot myRobot, World world, Robot enemyRobot) {
        int newX = myRobot.getCurrentPosition().getX();
        int newY = myRobot.getCurrentPosition().getY();

        switch (myRobot.getCurrentDirection()) {
            case NORTH:
                newY = newY + shotLength;
                break;
            case SOUTH:
                newY = newY - shotLength;
                break;
            case EAST:
                newX = newX + shotLength;
                break;
            case WEST:
                newX = newX - shotLength;
                break;
        }
        Position newPosition = new Position(newX, newY);
        for(Obstacle obstacle : world.getOBSTACLES()){
            this.obstacleBlocksPath(myRobot.getCurrentPosition(), newPosition, obstacle);
        }
        if (robotBlocksPath(myRobot.getCurrentPosition(), newPosition, enemyRobot)) {
            return true;

        }

        return false;
    }

    private boolean robotBlocksSight(Position position, Robot robot) {
        if (robot.getCurrentPosition().getX() == position.getX() && robot.getCurrentPosition().getY() == position.getY()) {
            return true;
        }
        return false;
    }

    private boolean robotBlocksPath(Position a, Position b, Robot robot) {
        if (a.getX() > b.getX()) {
            int path = a.getX() - b.getX();
            for (int i = 0; i < path + 1; i++) {
                if (robotBlocksSight(new Position(b.getX() + i, b.getY()), robot)) {
                    setShotDistance(i);
                    if(shotDistance < obstacleDistance){
                        obstacleDistance = 1000;
                        return true;
                    }

                }
            }
        } else if (a.getX() < b.getX()) {
            int path = b.getX() - a.getX();
            for (int i = 0; i < path + 1; i++) {
                if (robotBlocksSight(new Position(a.getX() + i, a.getY()), robot)) {
                    setShotDistance(i);
                    if(shotDistance < obstacleDistance){
                        obstacleDistance = 1000;
                        return true;
                    }
                }
            }
        } else if (a.getY() > b.getY()) {
            int path = a.getY() - b.getY();
            for (int i = 0; i < path + 1; i++) {
                if (robotBlocksSight(new Position(b.getX(), b.getY() + i), robot)) {
                    setShotDistance(i);
                    if(shotDistance < obstacleDistance){
                        obstacleDistance = 1000;
                        return true;
                    }
                }
            }
        } else if (a.getY() < b.getY()) {
            int path = b.getY() - a.getY();
            for (int i = 0; i < path + 1; i++) {
                if (robotBlocksSight(new Position(a.getX(), a.getY() + i), robot)) {
                    setShotDistance(i);
                    if(shotDistance < obstacleDistance){
                        obstacleDistance = 1000;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void obstacleBlocksPath(Position a, Position b, Obstacle obstacle){
        if(a.getX() > b.getX()){
            int path = a.getX() - b.getX();
            for(int i = 0; i < path; i++){
                if(obstacle.blocksPosition(new Position(b.getX()+i, b.getY()))){
                    obstacleDistance = i;
                }
            }
        }
        else if(a.getX() < b.getX()){
            int path = b.getX() - a.getX();
            for(int i = 0; i < path; i++){
                if(obstacle.blocksPosition(new Position(a.getX()+i, a.getY()))){
                    obstacleDistance = i;
                }
            }
        }
        else if(a.getY() > b.getY()){
            int path = a.getY() - b.getY();
            for(int i = 0; i < path; i++){
                if(obstacle.blocksPosition(new Position(b.getX(), b.getY()+ i))){
                    obstacleDistance = i;
                }
            }
        }
        else if(a.getY() < b.getY()){
            int path = b.getY() - a.getY();
            for(int i = 0; i < path; i++){
                if(obstacle.blocksPosition(new Position(a.getX(), a.getY()+ i))){
                    obstacleDistance = i;
                }
            }
        }
    }

    public static void robotDestroyed(Robot robot){
        if(robot.getShields() == -1){
            ClientHandler.robots.remove(robot);
            ClientHandler.broadcastMessage("[" +robot.getRobotName().toUpperCase() + "]: has been destroyed.");
        }
    }

    public void setShotDistance(int shotDistance1) {
        shotDistance = shotDistance1;
    }

    public static class HitJson {

        String result;
        DataJson data;
        StateJson state;

        public HitJson(String result, DataJson data, StateJson state) {
            this.result = result;
            this.data = data;
            this.state = state;
        }
    }

    public static class DataJson {
        String message;
        int distance;
        String robot;
        EnemyRobotStateJson enemyState;

        public DataJson(String message, int distance, String robot, EnemyRobotStateJson enemyState) {
            this.message = message;
            this.distance = distance;
            this.robot = robot;
            this.enemyState = enemyState;
        }
    }

    public static class EnemyRobotStateJson {
        int[] position;
        String direction;
        int shields;
        int shots;
        String status;

        public EnemyRobotStateJson(int[] position, String direction, int shields, int shots, String status) {
            this.position = position;
            this.direction = direction;
            this.shields = shields;
            this.shots = shots;
            this.status = status;
        }
    }

    public static class StateJson {
        int shots;

        public StateJson(int shots) {
            this.shots = shots;
        }
    }

    public static class MissJson{
        String result;
        MissDataJson miss;
        StateJson state;

        public MissJson(String result, MissDataJson miss, StateJson state){
            this.result = result;
            this.miss = miss;
            this.state = state;
        }
    }

    public static class MissDataJson{
        String message;

        public MissDataJson(String message){
            this.message = message;
        }
    }

}

