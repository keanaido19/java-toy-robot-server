package za.co.wethinkcode.robotworlds.commands;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class MovementCommand extends Command {
    public MovementCommand(
            String robotName,
            String command,
            List<String> commandArguments
    ) {
        super(robotName, command, commandArguments);
    }

    @Override
    public JsonNode execute() {
        return null;
    }
}
