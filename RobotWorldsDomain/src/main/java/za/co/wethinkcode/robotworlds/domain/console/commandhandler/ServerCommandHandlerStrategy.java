package za.co.wethinkcode.robotworlds.domain.console.commandhandler;

import za.co.wethinkcode.robotworlds.domain.console.commands.ConsoleCommand;

public abstract class ServerCommandHandlerStrategy {
    public abstract boolean checkCommand(String command);

    public abstract ConsoleCommand getCommand();
}
