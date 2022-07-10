package za.co.wethinkcode.robotworlds.serverconsole.commands;

import za.co.wethinkcode.robotworlds.RobotServer;
import za.co.wethinkcode.robotworlds.console.commands.DumpCommand;

public class ServerDumpCommand extends ServerCommand{
    @Override
    public boolean execute(RobotServer server) {
        new DumpCommand().execute();
        return true;
    }
}
