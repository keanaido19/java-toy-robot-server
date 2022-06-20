package za.co.wethinkcode.robotworlds.clienthandler.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.AuxiliaryCommand;
import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.ReloadCommand;

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
