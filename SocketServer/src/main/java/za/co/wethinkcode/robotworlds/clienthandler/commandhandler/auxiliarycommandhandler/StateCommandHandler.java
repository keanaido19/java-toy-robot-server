package za.co.wethinkcode.robotworlds.clienthandler.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.AuxiliaryCommand;
import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.StateCommand;

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
