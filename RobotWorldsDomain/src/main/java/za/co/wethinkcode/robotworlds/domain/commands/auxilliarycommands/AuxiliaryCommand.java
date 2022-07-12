package za.co.wethinkcode.robotworlds.domain.commands.auxilliarycommands;

import za.co.wethinkcode.robotworlds.domain.commands.Command;

import java.util.ArrayList;

public abstract class AuxiliaryCommand extends Command {
    public AuxiliaryCommand(String robotName, String command) {
        super(robotName, command, new ArrayList<>());
    }
}
