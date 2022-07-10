package za.co.wethinkcode.robotworlds.serverconsole.commandhandler;

import za.co.wethinkcode.robotworlds.serverconsole.commands.ServerNullCommand;
import za.co.wethinkcode.robotworlds.serverconsole.commands.ServerCommand;

public class ServerCommandHandler {
    private static final za.co.wethinkcode.robotworlds.serverconsole.commandhandler.ServerCommandHandlerStrategy[]
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
