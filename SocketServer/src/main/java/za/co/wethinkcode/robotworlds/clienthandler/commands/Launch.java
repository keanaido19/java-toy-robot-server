package za.co.wethinkcode.robotworlds.clienthandler.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.Position;
import za.co.wethinkcode.robotworlds.robot.*;
import za.co.wethinkcode.robotworlds.world.Obstacle;
import za.co.wethinkcode.robotworlds.world.World;
import java.util.Random;

public class Launch extends ClientCommand {

    public Launch(String make, String name) {
        super("launch",make,name);
    }

    @Override
    public String execute(
            World world,
            String[] arguments,
            ClientHandler clientHandler
    ) {
        Gson gson = new Gson();
        Forward.DataJson dataJson;
        ErrorResponseJson errorResponseJson;

        for (Robot r : world.getRobots()) {
            if (getArgument2().equals(r.getRobotName())) {
                dataJson = new Forward.DataJson("Too many of you in this world");
                errorResponseJson =
                        new ErrorResponseJson("ERROR", dataJson);
                return gson.toJson(errorResponseJson);
            }
        }

        Position freePosition = findFreeSpace(world);

        if (null == freePosition) {
            dataJson = new Forward.DataJson("No more space in this world");
            errorResponseJson = new ErrorResponseJson("ERROR", dataJson);
            return gson.toJson(errorResponseJson);
        }

        Robot robot;

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
        int counter = 0;
        while(true){
            boolean free = true;
//            Position freePosition = new Position((random.nextInt(world.getBOTTOM_RIGHT().getX() -
//                    (world.getTOP_LEFT().getX())) + (world.getTOP_LEFT().getX())),
//                    (random.nextInt(world.getTOP_LEFT().getY() -(world.getBOTTOM_RIGHT().getY()))) + (world.getBOTTOM_RIGHT().getY()));
            Position freePosition = new Position(0, 0);
            for(Obstacle obstacles: world.getOBSTACLES()){
                if(obstacles.blocksPosition(freePosition)){
                    free = false;
                    break;
                }
            }
            for(Robot robots: world.getRobots()) {
                if (robots.getCurrentPosition().getX() == freePosition.getX()
                        && robots.getCurrentPosition().getY() == freePosition.getY()) {
                    free = false;
                    break;
                }
            }
            if(free)return freePosition;
            if (1000 <= counter) return null;
            counter ++;
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
