package za.co.wethinkcode.robotworlds.commands;

public class RequestJson {

    String robot;
    String command;
    String[] arguments;

    RequestJson(String robotName, String robotCommand, String[] stateArguments) {
        this.robot = robotName;
        this.command = robotCommand;
        this.arguments = stateArguments;
    }
}
