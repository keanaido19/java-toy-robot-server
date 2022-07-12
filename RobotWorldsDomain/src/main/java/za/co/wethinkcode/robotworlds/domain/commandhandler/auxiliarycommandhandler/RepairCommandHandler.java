package za.co.wethinkcode.robotworlds.domain.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.domain.commands.auxilliarycommands.AuxiliaryCommand;
import za.co.wethinkcode.robotworlds.domain.commands.auxilliarycommands.RepairCommand;

public class RepairCommandHandler extends AuxiliaryCommandHandlerStrategy {
    @Override
    public boolean isCommandValid(String command) {
        return "repair".equals(command);
    }

    @Override
    public AuxiliaryCommand getCommand(String robotName) {
        return new RepairCommand(robotName);
    }
}
