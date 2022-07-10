package za.co.wethinkcode.robotworlds.serverconsole.commands;

import za.co.wethinkcode.robotworlds.RobotServer;
import za.co.wethinkcode.robotworlds.console.commands.NullCommand;

public class ServerNullCommand extends ServerCommand{
    @Override
    public boolean execute(RobotServer server) {
        new NullCommand().execute();
        return true;
    }
}
