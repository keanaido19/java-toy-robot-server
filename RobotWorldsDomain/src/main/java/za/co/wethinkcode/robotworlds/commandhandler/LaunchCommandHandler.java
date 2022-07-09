package za.co.wethinkcode.robotworlds.commandhandler;

import za.co.wethinkcode.robotworlds.commands.Command;
import za.co.wethinkcode.robotworlds.commands.LaunchCommand;

import java.util.List;

import static za.co.wethinkcode.robotworlds.Helpers.isObjectAlphabetString;
import static za.co.wethinkcode.robotworlds.Helpers.isObjectPositiveInteger;

public class LaunchCommandHandler extends CommandHandlerStrategy {
    public LaunchCommandHandler(
            String robotName,
            String command,
            List<String> commandArguments
    ) {
        super(robotName, command, commandArguments);
    }

    @Override
    public boolean isCommandValid() {
        return "launch".equals(command);
    }

    @Override
    public boolean isCommandArgumentsValid() {
        if (commandArguments.size() == 3) {
            String robotMake = commandArguments.get(0);
            String shields = commandArguments.get(1);
            String shots = commandArguments.get(2);
            return isObjectAlphabetString(robotMake) &&
                    isObjectPositiveInteger(shields) &&
                    isObjectPositiveInteger(shots);
        }
        return false;
    }

    @Override
    public Command getCommand() {
        return new LaunchCommand(robotName, commandArguments);
    }
}
