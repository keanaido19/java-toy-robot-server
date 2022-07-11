package za.co.wethinkcode.robotworlds.serverconsole.commands;

import za.co.wethinkcode.robotworlds.ApiServer;
import za.co.wethinkcode.robotworlds.console.commands.SaveCommand;

public class ServerSaveCommand extends ServerCommand {
    private final String worldName;

    public ServerSaveCommand(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public boolean execute(ApiServer server) {
        new SaveCommand(worldName).execute();
        return true;
    }
}
