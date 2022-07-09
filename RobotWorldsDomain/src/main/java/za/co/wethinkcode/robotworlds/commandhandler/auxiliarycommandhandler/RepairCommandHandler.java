package za.co.wethinkcode.robotworlds.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.AuxiliaryCommand;
import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.RepairCommand;

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
