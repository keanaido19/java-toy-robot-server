package za.co.wethinkcode.robotworlds.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import za.co.wethinkcode.robotworlds.ClientHandler;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

public class Turn extends ClientCommands{
    String turnDirection;
    public Turn(String robotName, String turnDirection){
        super("turn",robotName);
        this.turnDirection = turnDirection;
    }

    @Override
    public String execute(World world, String[] arguments, ClientHandler clientHandler) {
        StateResponseJson stateResponseJson = null;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        for (Robot robot : world.getRobots()) {
            if (robot.getRobotName().equals(getArgument())){
                switch(turnDirection){
                    case "left":
                        robot.updateDirection(false);
                        break;
                    case "right":
                        robot.updateDirection(true);
                        break;
                }
                stateResponseJson = new StateResponseJson(robot.getCurrentDirection().toString());
            }
        }
        DataResponseJson dataResponseJson = new DataResponseJson("Done");
        TurnResponseJson turnResponseJson = new TurnResponseJson("OK",dataResponseJson,stateResponseJson);
        return gson.toJson(turnResponseJson);
    }
    public class TurnResponseJson{
        String result;
        DataResponseJson data;
        StateResponseJson state;
        public TurnResponseJson(String result, DataResponseJson data, StateResponseJson state){
            this.result = result;
            this.data = data;
            this.state = state;
        }
    }

    public static class DataResponseJson{
        String message;
        public DataResponseJson(String message){
            this.message = message;
        }
    }
    public static class StateResponseJson{
        String direction;
        public StateResponseJson(String direction){
            this.direction = direction;
        }
    }
}
