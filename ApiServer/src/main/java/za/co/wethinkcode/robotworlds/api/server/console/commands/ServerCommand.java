package za.co.wethinkcode.robotworlds.api.server.console.commands;

import za.co.wethinkcode.robotworlds.api.server.ApiServer;

public abstract class ServerCommand {
    public abstract boolean execute(ApiServer server);
}

