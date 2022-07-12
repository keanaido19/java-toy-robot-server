package za.co.wethinkcode.robotworlds.domain.commands.auxilliarycommands;

import za.co.wethinkcode.robotworlds.domain.Play;
import za.co.wethinkcode.robotworlds.domain.commands.CommandResult;
import za.co.wethinkcode.robotworlds.domain.response.JsonResponse;
import za.co.wethinkcode.robotworlds.domain.world.builders.DataMapBuilder;
import za.co.wethinkcode.robotworlds.domain.world.objects.robots.Robot;

public class StateCommand extends AuxiliaryCommand {

    public StateCommand(String robotName) {
        super(robotName, "state");
    }

    @Override
    public JsonResponse execute(Play play) {
        Robot robot = world.getRobot(robotName);
        return
                new JsonResponse(
                        CommandResult.OK,
                        DataMapBuilder.getDataMap(world, robot),
                        robot.getRobotData()
                );
    }
}
