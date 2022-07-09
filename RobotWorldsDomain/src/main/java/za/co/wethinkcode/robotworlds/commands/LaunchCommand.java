package za.co.wethinkcode.robotworlds.commands;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class LaunchCommand extends Command {
    public LaunchCommand(String robotName, List<String> commandArguments) {
        super(robotName, "launch", commandArguments);
    }

    @Override
    public JsonNode execute() {
        return null;
    }
}
