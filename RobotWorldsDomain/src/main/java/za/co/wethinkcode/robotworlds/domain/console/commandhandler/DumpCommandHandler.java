package za.co.wethinkcode.robotworlds.domain.console.commandhandler;

import za.co.wethinkcode.robotworlds.domain.console.commands.DumpCommand;
import za.co.wethinkcode.robotworlds.domain.console.commands.ConsoleCommand;

public class DumpCommandHandler extends ServerCommandHandlerStrategy {
    @Override
    public boolean checkCommand(String command) {
        return "dump".equalsIgnoreCase(command.trim());
    }

    @Override
    public ConsoleCommand getCommand() {
        return new DumpCommand();
    }
}
