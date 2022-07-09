package za.co.wethinkcode.robotworlds.commands;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public abstract class Command {
    protected final String robotName;
    protected final String command;
    protected final List<String> commandArguments;

    public Command(
            String robotName,
            String command,
            List<String> commandArguments
    ) {
        this.robotName = robotName;
        this.command = command;
        this.commandArguments = commandArguments;
    }

    public abstract JsonNode execute();
}
