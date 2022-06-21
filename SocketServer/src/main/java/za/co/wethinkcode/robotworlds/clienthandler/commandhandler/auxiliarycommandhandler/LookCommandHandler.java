package za.co.wethinkcode.robotworlds.clienthandler.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.AuxiliaryCommand;
import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.LookCommand;

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
