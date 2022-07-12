package za.co.wethinkcode.robotworlds.domain.console.commandhandler;

import za.co.wethinkcode.robotworlds.domain.console.commands.RestoreCommand;
import za.co.wethinkcode.robotworlds.domain.console.commands.ConsoleCommand;

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
    public ConsoleCommand getCommand() {
        return new RestoreCommand(worldName);
    }
}
