package za.co.wethinkcode.robotworlds.clienthandler.commands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.response.ServerResponse;

import java.util.List;

public abstract class Command {
    protected final String robotName;
    protected final String command;
    protected final List<Object> commandArguments;

    public Command(
            String robotName,
            String command,
            List<Object> commandArguments
    ) {
        this.robotName = robotName;
        this.command = command;
        this.commandArguments = commandArguments;
    }

    public abstract ServerResponse execute(ClientHandler clientHandler);
}
