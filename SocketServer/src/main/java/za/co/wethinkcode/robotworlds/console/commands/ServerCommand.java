package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.RobotServer;

public abstract class ServerCommand {
    public abstract boolean execute(RobotServer server);
}

