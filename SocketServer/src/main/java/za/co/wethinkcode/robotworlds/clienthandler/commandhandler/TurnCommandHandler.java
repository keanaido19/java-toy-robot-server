package za.co.wethinkcode.robotworlds.clienthandler.commandhandler;

import za.co.wethinkcode.robotworlds.clienthandler.commands.Command;
import za.co.wethinkcode.robotworlds.clienthandler.commands.TurnCommand;

import java.util.List;

public class TurnCommandHandler extends CommandHandlerStrategy {
    public TurnCommandHandler(
            String robotName,
            String command,
            List<Object> commandArguments
    ) {
        super(robotName, command, commandArguments);
    }

    @Override
    public boolean isCommandValid() {
        return "turn".equals(command);
    }

    @Override
    public boolean isCommandArgumentsValid() {
        if (commandArguments.size() == 1) {
            Object turnArgument = commandArguments.get(0);
            return "left".equals(turnArgument) || "right".equals(turnArgument);
        }
        return false;
    }

    @Override
    public Command getCommand() {
        return new TurnCommand(robotName, command, commandArguments);
    }
}
