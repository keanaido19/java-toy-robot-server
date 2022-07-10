package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.RobotServer;

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
