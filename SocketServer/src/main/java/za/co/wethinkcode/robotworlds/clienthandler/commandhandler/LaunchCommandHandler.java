package za.co.wethinkcode.robotworlds.clienthandler.commandhandler;

import za.co.wethinkcode.robotworlds.clienthandler.commands.Command;
import za.co.wethinkcode.robotworlds.clienthandler.commands.LaunchCommand;

import java.util.List;

import static za.co.wethinkcode.robotworlds.Helpers.isObjectAlphabetString;
import static za.co.wethinkcode.robotworlds.Helpers.isObjectPositiveInteger;

public class LaunchCommandHandler extends CommandHandlerStrategy {
    public LaunchCommandHandler(
            String robotName,
            String command,
            List<Object> commandArguments
    )
    {
        super(robotName, command, commandArguments);
    }

    @Override
    public boolean isCommandValid() {
        return "launch".equals(command);
    }

    @Override
    public boolean isCommandArgumentsValid() {
        Object robotMake;
        Object shields;
        Object shots;
        if (commandArguments.size() == 3) {
            robotMake = commandArguments.get(0);
            shields = commandArguments.get(1);
            shots = commandArguments.get(2);
            return isObjectAlphabetString(robotMake) &&
                    isObjectPositiveInteger(shields) &&
                    isObjectPositiveInteger(shots);
        }
        return false;
    }

    @Override
    public Command getCommand() {
        return new LaunchCommand(robotName, command, commandArguments);
    }
}
