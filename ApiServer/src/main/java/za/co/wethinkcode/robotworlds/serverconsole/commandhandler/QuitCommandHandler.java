package za.co.wethinkcode.robotworlds.serverconsole.commandhandler;

import za.co.wethinkcode.robotworlds.serverconsole.commands.ServerCommand;
import za.co.wethinkcode.robotworlds.serverconsole.commands.ServerQuitCommand;

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
