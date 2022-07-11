package za.co.wethinkcode.robotworlds.serverconsole.commands;

import za.co.wethinkcode.robotworlds.ApiServer;
import za.co.wethinkcode.robotworlds.console.commands.DumpCommand;

public class ServerDumpCommand extends ServerCommand{
    @Override
    public boolean execute(ApiServer server) {
        new DumpCommand().execute();
        return true;
    }
}
