package za.co.wethinkcode.robotworlds.socket.server.console.commandhandler;

import za.co.wethinkcode.robotworlds.socket.server.console.commands.ServerDumpCommand;
import za.co.wethinkcode.robotworlds.socket.server.console.commands.ServerCommand;

public class DumpCommandHandler extends ServerCommandHandlerStrategy {
    @Override
    public boolean checkCommand(String command) {
        return "dump".equalsIgnoreCase(command.trim());
    }

    @Override
    public ServerCommand getCommand() {
        return new ServerDumpCommand();
    }
}
