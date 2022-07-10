package za.co.wethinkcode.robotworlds.serverconsole.commands;

import za.co.wethinkcode.robotworlds.ApiServer;

public abstract class ServerCommand {
    public abstract boolean execute(ApiServer server);
}

