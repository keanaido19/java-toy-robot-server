package za.co.wethinkcode.robotworlds.serverconsole.commands;

import za.co.wethinkcode.robotworlds.RobotServer;
import za.co.wethinkcode.robotworlds.console.commands.RestoreCommand;

public class ServerRestoreCommand extends ServerCommand {
    private final String worldName;

    public ServerRestoreCommand(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public boolean execute(RobotServer server) {
        new RestoreCommand(worldName).execute();
        return true;
    }
}
