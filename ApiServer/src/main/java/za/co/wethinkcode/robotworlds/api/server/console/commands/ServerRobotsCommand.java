package za.co.wethinkcode.robotworlds.api.server.console.commands;

import za.co.wethinkcode.robotworlds.api.server.ApiServer;
import za.co.wethinkcode.robotworlds.domain.console.commands.RobotsCommand;

public class ServerRobotsCommand extends ServerCommand{
    @Override
    public boolean execute(ApiServer server) {
        return new RobotsCommand().execute();
    }
}
