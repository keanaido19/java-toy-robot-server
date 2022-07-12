package za.co.wethinkcode.robotworlds.domain.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.domain.commands.auxilliarycommands.AuxiliaryCommand;

public abstract class AuxiliaryCommandHandlerStrategy {
    public abstract boolean isCommandValid(String command);

    public abstract AuxiliaryCommand getCommand(String robotName);
}
