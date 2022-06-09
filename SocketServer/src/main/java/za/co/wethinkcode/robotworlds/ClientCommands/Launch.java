package za.co.wethinkcode.robotworlds.ClientCommands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import za.co.wethinkcode.robotworlds.ClientHandler;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.Robot.*;
import za.co.wethinkcode.robotworlds.World.Obstacle;
import za.co.wethinkcode.robotworlds.World.World;
import java.util.Random;

public class Launch extends ClientCommands {

    public Launch(String make, String name) {
        super("launch",make,name);
    }

    @Override
    public String execute(
            World world,
            String[] arguments,
            ClientHandler clientHandler
    ) {
        Robot robot;
        Position freePosition = findFreeSpace(world);

        switch (getArgument()){
            case "normal":
                robot = new Normal(world, getArgument2(), getArgument());
                break;
            case "machinegun":
                robot = new MachineGun(world, getArgument2(), getArgument());
                break;
            case "sniper":
                robot = new Sniper(world, getArgument2(), getArgument());
                break;
            case "tank":
                robot = new Tank(world, getArgument2(), getArgument());
                break;
            default:
                return "Invalid tank type selected";
        }

        robot.setRobotPosition(freePosition.getX(),freePosition.getY());
        ClientHandler.robots.add(robot);

        if (null != clientHandler) clientHandler.robot = robot;

        return responseFormulator(robot);
    }

    private String responseFormulator(Robot robot){
        Gson gson = new GsonBuilder().create();
        int[] position = {robot.getCurrentPosition().getX(), robot.getCurrentPosition().getY()};
        StateResponse stateResponse = new StateResponse(position, robot.getCurrentDirection().toString(),
                robot.getShields(), robot.getShots(), robot.getStatus());
        DataResponse dataResponse = new DataResponse(position, 0, 0, 0, 0);
        LaunchResponse launchResponse = new LaunchResponse("OK", dataResponse, stateResponse);
        return gson.toJson(launchResponse);
    }

    private Position findFreeSpace(World world){
        Random random = new Random();
        while(true){
            boolean free = true;
//            Position freePosition = new Position((random.nextInt(world.getBOTTOM_RIGHT().getX() -
//                    (world.getTOP_LEFT().getX())) + (world.getTOP_LEFT().getX())),
//                    (random.nextInt(world.getTOP_LEFT().getY() -(world.getBOTTOM_RIGHT().getY()))) + (world.getBOTTOM_RIGHT().getY()));
            Position freePosition = new Position(0, 0);
            for(Obstacle obstacles: world.getOBSTACLES()){
                if(obstacles.blocksPosition(freePosition)){
                    free = false;
                }
            for(Robot robots: world.getRobots())    {
                if(robots.getCurrentPosition().getX() == freePosition.getX()
                        && robots.getCurrentPosition().getY() == freePosition.getY()){
                    free = false;
                }
            }
            }
            if(free){
                return freePosition;
            }
        }
    }

    public class LaunchResponse{
        String result;
        DataResponse data;
        StateResponse state;
        public LaunchResponse(String result, DataResponse data, StateResponse state){
            this.result = result;
            this.data = data;
            this.state = state;
        }
    }

    public static class DataResponse{
        int[] position;
        int visibility;
        int reload;
        int repair;
        int shields;

        public DataResponse(int[] position, int visibility, int reload, int repair, int shields){
            this.position = position;
            this.visibility = visibility;
            this.reload = reload;
            this.repair = repair;
            this.shields = shields;
        }
    }

    public static class StateResponse{
        int[] position;
        String direction;
        int shields;
        int shots;
        String status;

        public StateResponse(int[] position, String direction, int shields, int shots, String status){
            this.position = position;
            this.direction = direction;
            this.shields = shields;
            this.shots = shots;
            this.status = status;
        }
    }
}
