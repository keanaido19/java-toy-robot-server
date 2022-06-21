package za.co.wethinkcode.robotworlds.clienthandler.commandhandler;

import za.co.wethinkcode.robotworlds.clienthandler.commands.Command;

import java.util.List;

public abstract class CommandHandlerStrategy {
    protected String robotName;
    protected String command;
    protected List<Object> commandArguments;

    public CommandHandlerStrategy(
            String robotName,
            String command,
            List<Object> commandArguments
    ) {
        this.robotName = robotName;
        this.command = command;
        this.commandArguments = commandArguments;
    }

    public abstract boolean isCommandValid();

    public abstract boolean isCommandArgumentsValid();

    public abstract Command getCommand();
}
