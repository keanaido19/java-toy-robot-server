package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.RobotServer;

public class ServerNullCommand extends ServerCommand{
    @Override
    public boolean execute(RobotServer server) {
        new NullCommand().execute();
        return true;
    }
}
