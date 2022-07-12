package za.co.wethinkcode.robotworlds.api.server.console.commandhandler;

import za.co.wethinkcode.robotworlds.api.server.console.commands.ServerCommand;
import za.co.wethinkcode.robotworlds.api.server.console.commands.ServerDumpCommand;

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
