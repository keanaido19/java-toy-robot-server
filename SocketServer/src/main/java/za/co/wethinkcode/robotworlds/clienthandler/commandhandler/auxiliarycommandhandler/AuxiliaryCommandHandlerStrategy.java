package za.co.wethinkcode.robotworlds.clienthandler.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.AuxiliaryCommand;

public abstract class AuxiliaryCommandHandlerStrategy {
    public abstract boolean isCommandValid(String command);

    public abstract AuxiliaryCommand getCommand(String robotName);
}
