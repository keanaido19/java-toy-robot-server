package za.co.wethinkcode.robotworlds.serverconsole.commandhandler;

import za.co.wethinkcode.robotworlds.serverconsole.commands.ServerCommand;
import za.co.wethinkcode.robotworlds.serverconsole.commands.ServerRestoreCommand;

public class RestoreCommandHandler extends ServerCommandHandlerStrategy {

    private String worldName = null;

    @Override
    public boolean checkCommand(String command) {
        String[] commandAndArgument = command.trim().split(" ");
        if (2 != commandAndArgument.length) return false;
        worldName = commandAndArgument[1];
        return "restore".equalsIgnoreCase(commandAndArgument[0]);
    }

    @Override
    public ServerCommand getCommand() {
        return new ServerRestoreCommand(worldName);
    }
}
