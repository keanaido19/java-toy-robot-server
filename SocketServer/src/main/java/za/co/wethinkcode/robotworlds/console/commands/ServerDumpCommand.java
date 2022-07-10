package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.RobotServer;

public class ServerDumpCommand extends ServerCommand{
    @Override
    public boolean execute(RobotServer server) {
        new DumpCommand().execute();
        return true;
    }
}
