package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.RobotServer;

public class ServerQuitCommand extends ServerCommand{
    @Override
    public boolean execute(RobotServer server) {
        new QuitCommand().execute();
        server.stopServer();
        return false;
    }
}
