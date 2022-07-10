package za.co.wethinkcode.robotworlds.commandhandler.auxiliarycommandhandler;

import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.AuxiliaryCommand;

public abstract class AuxiliaryCommandHandlerStrategy {
    public abstract boolean isCommandValid(String command);

    public abstract AuxiliaryCommand getCommand(String robotName);
}
