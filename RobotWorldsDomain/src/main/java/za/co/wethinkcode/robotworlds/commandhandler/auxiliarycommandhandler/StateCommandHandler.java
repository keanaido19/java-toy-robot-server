package za.co.wethinkcode.robotworlds.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.AuxiliaryCommand;
import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.StateCommand;

public class StateCommandHandler extends AuxiliaryCommandHandlerStrategy {
    @Override
    public boolean isCommandValid(String command) {
        return "state".equals(command);
    }

    @Override
    public AuxiliaryCommand getCommand(String robotName) {
        return new StateCommand(robotName);
    }
}
