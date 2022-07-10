package za.co.wethinkcode.robotworlds.serverconsole.commands;

import za.co.wethinkcode.robotworlds.RobotServer;
import za.co.wethinkcode.robotworlds.console.commands.QuitCommand;

public class ServerQuitCommand extends ServerCommand{
    @Override
    public boolean execute(RobotServer server) {
        new QuitCommand().execute();
        server.stopServer();
        return false;
    }
}
