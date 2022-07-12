package za.co.wethinkcode.robotworlds.api.server.console.commands;

import za.co.wethinkcode.robotworlds.api.server.ApiServer;
import za.co.wethinkcode.robotworlds.domain.console.commands.DumpCommand;

public class ServerDumpCommand extends ServerCommand{
    @Override
    public boolean execute(ApiServer server) {
        return new DumpCommand().execute();
    }
}
