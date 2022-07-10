package za.co.wethinkcode.robotworlds.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.AuxiliaryCommand;
import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.ReloadCommand;

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
