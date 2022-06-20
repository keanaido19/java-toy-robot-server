package za.co.wethinkcode.robotworlds.clienthandler.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.AuxiliaryCommand;
import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.FireCommand;

public class FireCommandHandler extends AuxiliaryCommandHandlerStrategy {
    @Override
    public boolean isCommandValid(String command) {
        return "fire".equals(command);
    }

    @Override
    public AuxiliaryCommand getCommand(String robotName) {
        return new FireCommand(robotName);
    }
}
