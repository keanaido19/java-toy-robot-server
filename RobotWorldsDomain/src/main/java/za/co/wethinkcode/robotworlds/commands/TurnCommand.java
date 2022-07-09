package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.Play;
import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.StateCommand;
import za.co.wethinkcode.robotworlds.response.JsonResponse;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

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
        Robot robot = Play.getWorld().getRobot(robotName);

        if ("right".equals(commandArguments.get(0)))
            robot.turnRight();
        else
            robot.turnLeft();

        return new StateCommand(robotName).execute();
    }
}
