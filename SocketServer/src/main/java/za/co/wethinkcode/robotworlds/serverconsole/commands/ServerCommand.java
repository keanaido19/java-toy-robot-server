package za.co.wethinkcode.robotworlds.serverconsole.commands;

import za.co.wethinkcode.robotworlds.RobotServer;

public abstract class ServerCommand {
    public abstract boolean execute(RobotServer server);
}

