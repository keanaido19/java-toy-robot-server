package za.co.wethinkcode.robotworlds.socket.server.console.commandhandler;

import za.co.wethinkcode.robotworlds.socket.server.console.commands.ServerQuitCommand;
import za.co.wethinkcode.robotworlds.socket.server.console.commands.ServerCommand;

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
