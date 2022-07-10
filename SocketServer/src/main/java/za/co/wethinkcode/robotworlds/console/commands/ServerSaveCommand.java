package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.RobotServer;

public class ServerSaveCommand extends ServerCommand {
    private final String worldName;

    public ServerSaveCommand(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public boolean execute(RobotServer server) {
        new SaveCommand(worldName).execute();
        return true;
    }
}
