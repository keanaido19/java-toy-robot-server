package za.co.wethinkcode.robotworlds.domain.commands;

import za.co.wethinkcode.robotworlds.domain.Play;
import za.co.wethinkcode.robotworlds.domain.commands.auxilliarycommands.StateCommand;
import za.co.wethinkcode.robotworlds.domain.response.JsonResponse;
import za.co.wethinkcode.robotworlds.domain.world.objects.robots.Robot;

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
    public JsonResponse execute(Play play) {
        Robot robot = world.getRobot(robotName);

        if ("right".equals(commandArguments.get(0)))
            robot.turnRight();
        else
            robot.turnLeft();

        return new StateCommand(robotName).execute(play);
    }
}
