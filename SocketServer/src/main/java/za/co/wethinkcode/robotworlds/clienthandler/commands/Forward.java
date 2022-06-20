package za.co.wethinkcode.robotworlds.clienthandler.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;


public class Forward extends ClientCommand {

    int steps;

    public Forward( String robotName, int steps) {
        super("forward", robotName);
        this.steps = steps;
    }

    @Override
    public ServerResponse execute(World world, String[] arguments, ClientHandler clientHandler) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        for (Robot robot: world.getRobots()) {
            if (robot.getRobotName().equals(getArgument())){
                switch(robot.updatePosition(steps)){
                    case SUCCESS:
                        int[] position = {robot.getCurrentPosition().getX(), robot.getCurrentPosition().getY()};

                        StateJson stateJson = new StateJson(position, robot.getCurrentDirection().toString(),
                                robot.getShields(), robot.getShots(), robot.getStatus());

                        DataJson dataJson = new DataJson("Done");

                        MovementJson movementJson = new MovementJson("OK", dataJson, stateJson);
                        return gson.toJson(movementJson);


                    case FAILED_OUTSIDE_WORLD:
                        int[] coord = {robot.getCurrentPosition().getX(), robot.getCurrentPosition().getY()};

                        StateJson stateJson1 = new StateJson(coord, robot.getCurrentDirection().toString(),
                                robot.getShields(), robot.getShots(), robot.getStatus());

                        DataJson dataJson1 = new DataJson("Edge");

                        MovementJson movementJson1 = new MovementJson("OK", dataJson1, stateJson1);

                        return gson.toJson(movementJson1);

                    case FAILED_OBSTRUCTED:
                        int[] place = {robot.getCurrentPosition().getX(), robot.getCurrentPosition().getY()};

                        StateJson stateJson2 = new StateJson(place, robot.getCurrentDirection().toString(),
                                robot.getShields(), robot.getShots(), robot.getStatus());

                        DataJson dataJson2 = new DataJson("Obstructed");

                        MovementJson movementJson2 = new MovementJson("OK", dataJson2, stateJson2);
                        return gson.toJson(movementJson2);
                }
            }
        }
        return "Forward movement error";
    }


    public class MovementJson{
        String result;
        DataJson data;
        StateJson state;

        public MovementJson(String result, DataJson data, StateJson state){
            this.result = result;
            this.data = data;
            this.state = state;
        }
    }
    public static class DataJson{
        String message;

        public DataJson(String message){
            this.message = message;
        }
    }

    public static class StateJson{
        int[] position;
        String direction;
        int shields;
        int shots;
        String status;

        public StateJson(int[] position, String direction, int shields, int shots, String status){
            this.position = position;
            this.direction = direction;
            this.shields = shields;
            this.shots = shots;
            this.status = status;
        }
    }
}
