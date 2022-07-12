package za.co.wethinkcode.robotworlds.api.server;

import java.util.List;

public class JsonRequest {
    private String robot;
    private String command;
    private List<Object> arguments;

    public JsonRequest() {}

    public String getRobot() {
        return robot;
    }

    public void setRobot(String robot) {
        this.robot = robot;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public void setArguments(List<Object> arguments) {
        this.arguments = arguments;
    }
}
