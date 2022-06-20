package za.co.wethinkcode.robotworlds.clienthandler.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.DataObject;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;

public class State extends ClientCommand {

    public State(String robotName) {
        super("state",robotName);
    }

    @Override
    public ServerResponse execute(World world, String[] arguments, ClientHandler clientHandler) {
        Look.StateResponseJSon stateResponseJSon;
        Gson gson = new GsonBuilder().create();
        for(Robot robot : world.getRobots()){
            if(robot.getRobotName().equals(getArgument())){
                int[] position = {robot.getCurrentPosition().getX(), robot.getCurrentPosition().getY()};
                stateResponseJSon = new Look.StateResponseJSon(position,
                        robot.getCurrentDirection().toString(),
                        robot.getShields(),robot.getShots(),robot.getStatus());

                DataObject data =
                        new DataObject(
                                world.visibility,
                                position,
                                new ArrayList<Look.ObjectJson>()
                                        .toArray(new Look.ObjectJson[0])
                        );
                Look.LookResponseJson lookResponseJson =
                        new Look.LookResponseJson(
                                "OK",
                                data,
                                stateResponseJSon
                        );
                return gson.toJson(lookResponseJson);
            }
        }

        Forward.DataJson dataJson =
                new Forward.DataJson("Robot does not exist");
        ErrorResponseJson errorResponseJson =
                new ErrorResponseJson("ERROR", dataJson);

        return gson.toJson(errorResponseJson);
    }

    public static class StateResponseJSon{
        int[] position;
        String direction;
        int shields;
        int shots;
        String status;

        public StateResponseJSon(int[] position, String direction, int shields, int shots, String status){
            this.position = position;
            this.direction = direction;
            this.shields = shields;
            this.shots = shots;
            this.status = status;
        }
    }
}
