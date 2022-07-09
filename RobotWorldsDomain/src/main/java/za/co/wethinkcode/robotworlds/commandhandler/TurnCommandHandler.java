package za.co.wethinkcode.robotworlds.commandhandler;

import za.co.wethinkcode.robotworlds.commands.Command;
import za.co.wethinkcode.robotworlds.commands.TurnCommand;

import java.util.List;

public class TurnCommandHandler extends CommandHandlerStrategy {

    public TurnCommandHandler(
            String robotName,
            String command,
            List<String> commandArguments
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
            String turnArgument = commandArguments.get(0);
            return "left".equals(turnArgument) || "right".equals(turnArgument);
        }
        return false;
    }

    @Override
    public Command getCommand() {
        return new TurnCommand(robotName, command, commandArguments);
    }
}
