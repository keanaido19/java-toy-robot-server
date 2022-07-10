package za.co.wethinkcode.robotworlds.serverconsole.commands;

import za.co.wethinkcode.robotworlds.RobotServer;
import za.co.wethinkcode.robotworlds.console.commands.RobotsCommand;

public class ServerRobotsCommand extends ServerCommand{
    @Override
    public boolean execute(RobotServer server) {
        new RobotsCommand().execute();
        return true;
    }
}
