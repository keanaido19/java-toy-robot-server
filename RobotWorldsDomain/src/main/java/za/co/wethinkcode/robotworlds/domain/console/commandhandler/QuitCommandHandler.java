package za.co.wethinkcode.robotworlds.domain.console.commandhandler;

import za.co.wethinkcode.robotworlds.domain.console.commands.QuitCommand;
import za.co.wethinkcode.robotworlds.domain.console.commands.ConsoleCommand;

public class QuitCommandHandler extends ServerCommandHandlerStrategy {
    @Override
    public boolean checkCommand(String command) {
        return "quit".equalsIgnoreCase(command.trim());
    }

    @Override
    public ConsoleCommand getCommand() {
        return new QuitCommand();
    }
}
