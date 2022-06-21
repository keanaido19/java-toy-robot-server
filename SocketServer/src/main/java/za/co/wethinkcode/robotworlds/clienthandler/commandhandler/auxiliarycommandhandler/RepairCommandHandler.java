package za.co.wethinkcode.robotworlds.clienthandler.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.AuxiliaryCommand;
import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.RepairCommand;

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
