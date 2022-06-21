package za.co.wethinkcode.robotworlds.console.commandhandler;

import za.co.wethinkcode.robotworlds.console.commands.NullCommand;
import za.co.wethinkcode.robotworlds.console.commands.ServerCommand;

public class ServerCommandHandler {
    private static final ServerCommandHandlerStrategy[]
            ServerCommandHandlerStrategies =
            {new QuitCommandHandler(),
            new RobotsCommandHandler(),
            new DumpCommandHandler()};

    public static ServerCommand getServerCommand(String command) {
        for (ServerCommandHandlerStrategy serverCommandHandlerStrategy :
                ServerCommandHandlerStrategies) {
            if (serverCommandHandlerStrategy.checkCommand(command)) return
            serverCommandHandlerStrategy.getCommand();
        }
        return new NullCommand();
    }
}
