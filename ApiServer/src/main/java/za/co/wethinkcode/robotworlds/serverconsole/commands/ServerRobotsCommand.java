package za.co.wethinkcode.robotworlds.serverconsole.commands;

import za.co.wethinkcode.robotworlds.ApiServer;
import za.co.wethinkcode.robotworlds.console.commands.RobotsCommand;

public class ServerRobotsCommand extends ServerCommand{
    @Override
    public boolean execute(ApiServer server) {
        new RobotsCommand().execute();
        return true;
    }
}
