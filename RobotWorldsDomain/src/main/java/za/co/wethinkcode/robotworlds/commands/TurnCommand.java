package za.co.wethinkcode.robotworlds.commands;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class TurnCommand extends Command {
    public TurnCommand(
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
