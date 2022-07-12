package za.co.wethinkcode.robotworlds.socket.server.console.commands;

import za.co.wethinkcode.robotworlds.socket.server.RobotServer;

public abstract class ServerCommand {
    public abstract boolean execute(RobotServer server);
}

