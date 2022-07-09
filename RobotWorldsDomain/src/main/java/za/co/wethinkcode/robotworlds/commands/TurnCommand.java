package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.response.JsonResponse;

import java.util.List;

public class TurnCommand extends Command {
    public TurnCommand(
            String robotName,
            String command,
            List<String> commandArguments
    ) {
        super(robotName, command, commandArguments);
    }

    @Override
    public JsonResponse execute() {
        return null;
    }
}
