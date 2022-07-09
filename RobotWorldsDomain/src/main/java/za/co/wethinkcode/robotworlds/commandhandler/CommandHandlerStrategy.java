package za.co.wethinkcode.robotworlds.commandhandler;

import za.co.wethinkcode.robotworlds.commands.Command;

public abstract class CommandHandlerStrategy {

    public abstract boolean isCommandValid();

    public abstract boolean isCommandArgumentsValid();

    public abstract Command getCommand();
}
