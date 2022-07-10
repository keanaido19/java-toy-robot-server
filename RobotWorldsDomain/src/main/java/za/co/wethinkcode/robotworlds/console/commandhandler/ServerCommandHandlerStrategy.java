package za.co.wethinkcode.robotworlds.console.commandhandler;

import za.co.wethinkcode.robotworlds.console.commands.ConsoleCommand;

public abstract class ServerCommandHandlerStrategy {
    public abstract boolean checkCommand(String command);

    public abstract ConsoleCommand getCommand();
}
