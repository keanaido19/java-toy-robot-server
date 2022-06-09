package za.co.wethinkcode.robotworlds.ClientCommands;

import com.google.gson.Gson;

public class State extends ClientCommands {
    public State() {
        super("state");
    }

    public String execute(String robotName) {
        Gson gson = new Gson();
        String[] arguments = {};
        RequestJson stateCommandJson = new RequestJson(robotName, "state", arguments);
        return gson.toJson(stateCommandJson);
    }

    static class StateCommandJson {
        String robot;
        String command;
        String[] arguments;

        private StateCommandJson(String robotName, String robotCommand, String[] stateArguments) {
            this.robot = robotName;
            this.command = robotCommand;
            this.arguments = stateArguments;
        }
    }
}

