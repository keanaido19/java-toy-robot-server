package za.co.wethinkcode.robotworlds.socket.server.console.commands;

import za.co.wethinkcode.robotworlds.socket.server.RobotServer;
import za.co.wethinkcode.robotworlds.domain.console.commands.QuitCommand;

public class ServerQuitCommand extends ServerCommand{
    @Override
    public boolean execute(RobotServer server) {
        new QuitCommand().execute();
        server.stopServer();
        return false;
    }
}
