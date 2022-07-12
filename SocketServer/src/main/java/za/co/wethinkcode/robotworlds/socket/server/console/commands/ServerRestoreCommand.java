package za.co.wethinkcode.robotworlds.socket.server.console.commands;

import za.co.wethinkcode.robotworlds.socket.server.RobotServer;
import za.co.wethinkcode.robotworlds.domain.console.commands.RestoreCommand;

public class ServerRestoreCommand extends ServerCommand {
    private final String worldName;

    public ServerRestoreCommand(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public boolean execute(RobotServer server) {
        return new RestoreCommand(worldName).execute();
    }
}
