package za.co.wethinkcode.robotworlds.domain.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.domain.commandhandler.CommandHandlerStrategy;
import za.co.wethinkcode.robotworlds.domain.commands.Command;

import java.util.List;

public class AuxiliaryCommandHandler extends CommandHandlerStrategy {
    private final List<AuxiliaryCommandHandlerStrategy>
            auxiliaryCommandHandlerStrategyList =
            List.of(
                    new StateCommandHandler(),
                    new LookCommandHandler(),
                    new RepairCommandHandler(),
                    new ReloadCommandHandler(),
                    new FireCommandHandler()
            );

    public AuxiliaryCommandHandler(
            String robotName,
            String command,
            List<String> commandArguments
    ) {
        super(robotName, command, commandArguments);
    }

    @Override
    public boolean isCommandValid() {
        for (AuxiliaryCommandHandlerStrategy auxiliaryCommandHandlerStrategy :
                auxiliaryCommandHandlerStrategyList
        ) {
            if (auxiliaryCommandHandlerStrategy.isCommandValid(command))
                return true;
        }
        return false;
    }

    @Override
    public boolean isCommandArgumentsValid() {
        return commandArguments.isEmpty();
    }

    @Override
    public Command getCommand() {
        for (AuxiliaryCommandHandlerStrategy auxiliaryCommandHandlerStrategy :
                auxiliaryCommandHandlerStrategyList
        ) {
            if (auxiliaryCommandHandlerStrategy.isCommandValid(command))
                return auxiliaryCommandHandlerStrategy.getCommand(robotName);
        }
        return null;
    }
}
