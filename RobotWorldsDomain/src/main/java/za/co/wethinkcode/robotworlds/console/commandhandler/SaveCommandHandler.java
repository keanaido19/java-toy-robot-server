package za.co.wethinkcode.robotworlds.console.commandhandler;

import za.co.wethinkcode.robotworlds.console.commands.SaveCommand;
import za.co.wethinkcode.robotworlds.console.commands.ConsoleCommand;

public class SaveCommandHandler extends ServerCommandHandlerStrategy {

    private String worldName = null;

    @Override
    public boolean checkCommand(String command) {
        String[] commandAndArgument = command.trim().split(" ");
        if (2 != commandAndArgument.length) return false;
        worldName = commandAndArgument[1];
        return "save".equalsIgnoreCase(commandAndArgument[0]);
    }

    @Override
    public ConsoleCommand getCommand() {
        return new SaveCommand(worldName);
    }
}
