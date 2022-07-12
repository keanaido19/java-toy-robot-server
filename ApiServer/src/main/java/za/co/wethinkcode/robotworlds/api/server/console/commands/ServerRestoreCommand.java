package za.co.wethinkcode.robotworlds.api.server.console.commands;

import za.co.wethinkcode.robotworlds.api.server.ApiServer;
import za.co.wethinkcode.robotworlds.domain.console.commands.RestoreCommand;

public class ServerRestoreCommand extends ServerCommand {
    private final String worldName;

    public ServerRestoreCommand(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public boolean execute(ApiServer server) {
        new RestoreCommand(worldName).execute();
        return true;
    }
}
