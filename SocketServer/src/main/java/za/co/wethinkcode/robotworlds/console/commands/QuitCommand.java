package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.RobotServer;

public class QuitCommand extends ServerCommand{
    @Override
    public boolean execute(RobotServer server) {
        System.out.println("Shutting down...");
        server.stopServer();
        return false;
    }
}
