package za.co.wethinkcode.robotworlds.socket.server.console.commandhandler;

import za.co.wethinkcode.robotworlds.socket.server.console.commands.ServerCommand;

public abstract class ServerCommandHandlerStrategy {
    public abstract boolean checkCommand(String command);

    public abstract ServerCommand getCommand();
}
