package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.RobotServer;

public class SaveCommand extends ServerCommand {
    private final String worldName;

    public SaveCommand(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public boolean execute(RobotServer server) {
        return true;
    }
}
