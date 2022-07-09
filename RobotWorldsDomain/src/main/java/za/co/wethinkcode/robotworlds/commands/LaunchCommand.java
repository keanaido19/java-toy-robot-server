package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.response.JsonResponse;

import java.util.List;

public class LaunchCommand extends Command {
    public LaunchCommand(String robotName, List<String> commandArguments) {
        super(robotName, "launch", commandArguments);
    }

    @Override
    public JsonResponse execute() {
        return null;
    }
}
