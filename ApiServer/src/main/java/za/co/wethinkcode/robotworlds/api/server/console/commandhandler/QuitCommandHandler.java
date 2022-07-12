package za.co.wethinkcode.robotworlds.api.server.console.commandhandler;

import za.co.wethinkcode.robotworlds.api.server.console.commands.ServerCommand;
import za.co.wethinkcode.robotworlds.api.server.console.commands.ServerQuitCommand;

public class QuitCommandHandler extends ServerCommandHandlerStrategy {
    @Override
    public boolean checkCommand(String command) {
        return "quit".equalsIgnoreCase(command.trim());
    }

    @Override
    public ServerCommand getCommand() {
        return new ServerQuitCommand();
    }
}
