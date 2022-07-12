package za.co.wethinkcode.robotworlds.domain.commandhandler;

import za.co.wethinkcode.robotworlds.domain.commands.Command;
import za.co.wethinkcode.robotworlds.domain.commands.MovementCommand;

import java.util.List;

import static za.co.wethinkcode.robotworlds.domain.Helpers.isObjectPositiveInteger;

public class MovementCommandHandler extends CommandHandlerStrategy {
    public MovementCommandHandler(
            String robotName,
            String command,
            List<String> commandArguments
    ) {
        super(robotName, command, commandArguments);
    }

    @Override
    public boolean isCommandValid() {
        return "forward".equals(command) || "back".equals(command);
    }

    @Override
    public boolean isCommandArgumentsValid() {
        if (commandArguments.size() == 1) {
            return isObjectPositiveInteger(commandArguments.get(0));
        }
        return false;
    }

    @Override
    public Command getCommand() {
        return new MovementCommand(robotName, command, commandArguments);
    }
}
