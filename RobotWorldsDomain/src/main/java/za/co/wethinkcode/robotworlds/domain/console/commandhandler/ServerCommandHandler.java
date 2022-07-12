package za.co.wethinkcode.robotworlds.domain.console.commandhandler;

import za.co.wethinkcode.robotworlds.domain.console.commands.NullCommand;
import za.co.wethinkcode.robotworlds.domain.console.commands.ConsoleCommand;

public class ServerCommandHandler {
    private static final ServerCommandHandlerStrategy[]
            ServerCommandHandlerStrategies =
            {new QuitCommandHandler(),
            new RobotsCommandHandler(),
            new DumpCommandHandler(),
            new SaveCommandHandler(),
            new RestoreCommandHandler()};

    public static ConsoleCommand getServerCommand(String command) {
        for (ServerCommandHandlerStrategy serverCommandHandlerStrategy :
                ServerCommandHandlerStrategies) {
            if (serverCommandHandlerStrategy.checkCommand(command)) return
            serverCommandHandlerStrategy.getCommand();
        }
        return new NullCommand();
    }
}
