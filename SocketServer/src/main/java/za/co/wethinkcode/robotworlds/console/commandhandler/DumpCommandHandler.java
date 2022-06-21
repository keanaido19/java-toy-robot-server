package za.co.wethinkcode.robotworlds.console.commandhandler;

import za.co.wethinkcode.robotworlds.console.commands.DumpCommand;
import za.co.wethinkcode.robotworlds.console.commands.ServerCommand;

public class DumpCommandHandler extends ServerCommandHandlerStrategy {
    @Override
    public boolean checkCommand(String command) {
        return "dump".equalsIgnoreCase(command.trim());
    }

    @Override
    public ServerCommand getCommand() {
        return new DumpCommand();
    }
}
