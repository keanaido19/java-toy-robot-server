package za.co.wethinkcode.robotworlds.api.server.console.commandhandler;

import za.co.wethinkcode.robotworlds.api.server.console.commands.ServerCommand;
import za.co.wethinkcode.robotworlds.api.server.console.commands.ServerNullCommand;

public class ServerCommandHandler {
    private static final ServerCommandHandlerStrategy[]
            ServerCommandHandlerStrategies =
            {new QuitCommandHandler(),
            new RobotsCommandHandler(),
            new DumpCommandHandler(),
            new SaveCommandHandler(),
            new RestoreCommandHandler()};

    public static ServerCommand getServerCommand(String command) {
        for (ServerCommandHandlerStrategy serverCommandHandlerStrategy :
                ServerCommandHandlerStrategies) {
            if (serverCommandHandlerStrategy.checkCommand(command)) return
            serverCommandHandlerStrategy.getCommand();
        }
        return new ServerNullCommand();
    }
}
