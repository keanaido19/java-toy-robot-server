package za.co.wethinkcode.robotworlds.serverconsole.commandhandler;

import za.co.wethinkcode.robotworlds.serverconsole.commands.ServerDumpCommand;
import za.co.wethinkcode.robotworlds.serverconsole.commands.ServerCommand;

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
