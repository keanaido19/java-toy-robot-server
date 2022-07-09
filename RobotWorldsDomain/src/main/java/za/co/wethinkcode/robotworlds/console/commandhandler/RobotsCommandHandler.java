package za.co.wethinkcode.robotworlds.console.commandhandler;

import za.co.wethinkcode.robotworlds.console.commands.RobotsCommand;
import za.co.wethinkcode.robotworlds.console.commands.ConsoleCommand;

public class RobotsCommandHandler extends ServerCommandHandlerStrategy{
    @Override
    public boolean checkCommand(String command) {
        return "robots".equalsIgnoreCase(command.trim());
    }

    @Override
    public ConsoleCommand getCommand() {
        return new RobotsCommand();
    }
}
