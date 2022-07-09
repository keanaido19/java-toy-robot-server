package za.co.wethinkcode.robotworlds.commands.auxilliarycommands;

import za.co.wethinkcode.robotworlds.commands.Command;

import java.util.ArrayList;

public abstract class AuxiliaryCommand extends Command {
    public AuxiliaryCommand(String robotName, String command) {
        super(robotName, command, new ArrayList<>());
    }
}
