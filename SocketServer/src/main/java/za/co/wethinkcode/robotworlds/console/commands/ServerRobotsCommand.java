package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.RobotServer;

public class ServerRobotsCommand extends ServerCommand{
    @Override
    public boolean execute(RobotServer server) {
        new RobotsCommand().execute();
        return true;
    }
}
