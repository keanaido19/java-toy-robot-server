package za.co.wethinkcode.robotworlds.api.server.console.commands;

import za.co.wethinkcode.robotworlds.api.server.ApiServer;
import za.co.wethinkcode.robotworlds.domain.console.commands.QuitCommand;

public class ServerQuitCommand extends ServerCommand{
    @Override
    public boolean execute(ApiServer server) {
        new QuitCommand().execute();
        server.stop();
        return false;
    }
}
