package za.co.wethinkcode.robotworlds.api.server.console.commandhandler;

import za.co.wethinkcode.robotworlds.api.server.console.commands.ServerRobotsCommand;
import za.co.wethinkcode.robotworlds.api.server.console.commands.ServerCommand;

public class RobotsCommandHandler extends ServerCommandHandlerStrategy{
    @Override
    public boolean checkCommand(String command) {
        return "robots".equalsIgnoreCase(command.trim());
    }

    @Override
    public ServerCommand getCommand() {
        return new ServerRobotsCommand();
    }
}
