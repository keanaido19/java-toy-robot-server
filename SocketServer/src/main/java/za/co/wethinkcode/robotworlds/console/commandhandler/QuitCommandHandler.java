package za.co.wethinkcode.robotworlds.console.commandhandler;

import za.co.wethinkcode.robotworlds.console.commands.QuitCommand;
import za.co.wethinkcode.robotworlds.console.commands.ServerCommand;

public class QuitCommandHandler extends ServerCommandHandlerStrategy {
    @Override
    public boolean checkCommand(String command) {
        return "quit".equalsIgnoreCase(command.trim());
    }

    @Override
    public ServerCommand getCommand() {
        return new QuitCommand();
    }
}
