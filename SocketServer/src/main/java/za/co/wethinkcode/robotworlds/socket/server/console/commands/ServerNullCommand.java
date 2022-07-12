package za.co.wethinkcode.robotworlds.socket.server.console.commands;

import za.co.wethinkcode.robotworlds.socket.server.RobotServer;
import za.co.wethinkcode.robotworlds.domain.console.commands.NullCommand;

public class ServerNullCommand extends ServerCommand{
    @Override
    public boolean execute(RobotServer server) {
        return new NullCommand().execute();
    }
}
