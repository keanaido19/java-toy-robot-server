package za.co.wethinkcode.robotworlds.domain.commands;

import za.co.wethinkcode.robotworlds.domain.Play;
import za.co.wethinkcode.robotworlds.domain.response.JsonResponse;
import za.co.wethinkcode.robotworlds.domain.world.World;

import java.util.List;

public abstract class Command {
    protected final String robotName;
    protected final String command;
    protected final List<String> commandArguments;
    protected final World world = Play.getWorld();

    public Command(
            String robotName,
            String command,
            List<String> commandArguments
    ) {
        this.robotName = robotName;
        this.command = command;
        this.commandArguments = commandArguments;
    }

    public abstract JsonResponse execute(Play play);
}
