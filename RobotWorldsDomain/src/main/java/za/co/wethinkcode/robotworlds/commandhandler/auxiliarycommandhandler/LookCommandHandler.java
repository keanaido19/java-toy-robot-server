package za.co.wethinkcode.robotworlds.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.AuxiliaryCommand;
import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.LookCommand;

public class LookCommandHandler extends AuxiliaryCommandHandlerStrategy {
    @Override
    public boolean isCommandValid(String command) {
        return "look".equals(command);
    }

    @Override
    public AuxiliaryCommand getCommand(String robotName) {
        return new LookCommand(robotName);
    }
}
