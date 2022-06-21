package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.RobotServer;

public class NullCommand extends ServerCommand{
    @Override
    public boolean execute(RobotServer server) {
        System.out.println("Invalid Command!");
        return true;
    }
}
