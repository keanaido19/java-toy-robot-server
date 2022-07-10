package za.co.wethinkcode.robotworlds.serverconsole.commands;

import za.co.wethinkcode.robotworlds.ApiServer;
import za.co.wethinkcode.robotworlds.console.commands.QuitCommand;

public class ServerQuitCommand extends ServerCommand{
    @Override
    public boolean execute(ApiServer server) {
        new QuitCommand().execute();
        server.stop();
        return false;
    }
}
