package za.co.wethinkcode.robotworlds.api.server.console.commands;

import za.co.wethinkcode.robotworlds.api.server.ApiServer;
import za.co.wethinkcode.robotworlds.domain.console.commands.SaveCommand;

public class ServerSaveCommand extends ServerCommand {
    private final String worldName;

    public ServerSaveCommand(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public boolean execute(ApiServer server) {
        return new SaveCommand(worldName).execute();
    }
}
