package za.co.wethinkcode.robotworlds.ClientCommands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import za.co.wethinkcode.robotworlds.ClientHandler;
import za.co.wethinkcode.robotworlds.Robot.Robot;
import za.co.wethinkcode.robotworlds.World.World;

public class State extends ClientCommands{

    public State(String robotName) {
        super("state",robotName);
    }

    @Override
    public String execute(World world, String[] arguments, ClientHandler clientHandler) {
        StateResponseJSon stateResponseJSon;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        for(Robot robot : world.getRobots()){
            if(robot.getRobotName().equals(getArgument())){
                int[] position = {robot.getCurrentPosition().getX(), robot.getCurrentPosition().getY()};
                stateResponseJSon = new StateResponseJSon(position, robot.getCurrentDirection().toString(),
                        robot.getShields(),robot.getShots(),robot.getStatus());
                return gson.toJson(stateResponseJSon);
            }
        }
        return "Robot not found!!";
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
