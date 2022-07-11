package za.co.wethinkcode.robotworlds.serverconsole.commands;

import za.co.wethinkcode.robotworlds.ApiServer;
import za.co.wethinkcode.robotworlds.console.commands.RestoreCommand;

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
