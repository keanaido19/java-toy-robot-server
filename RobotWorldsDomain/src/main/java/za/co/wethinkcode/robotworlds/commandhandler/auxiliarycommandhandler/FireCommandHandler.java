package za.co.wethinkcode.robotworlds.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.AuxiliaryCommand;
import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.FireCommand;

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
