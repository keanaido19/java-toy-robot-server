package za.co.wethinkcode.robotworlds.domain.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.domain.commands.auxilliarycommands.AuxiliaryCommand;
import za.co.wethinkcode.robotworlds.domain.commands.auxilliarycommands.ReloadCommand;

public class ReloadCommandHandler extends AuxiliaryCommandHandlerStrategy {
    @Override
    public boolean isCommandValid(String command) {
        return "reload".equals(command);
    }

    @Override
    public AuxiliaryCommand getCommand(String robotName) {
        return new ReloadCommand(robotName);
    }
}
