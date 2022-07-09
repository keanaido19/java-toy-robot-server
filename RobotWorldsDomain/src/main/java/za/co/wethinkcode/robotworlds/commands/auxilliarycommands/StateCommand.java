package za.co.wethinkcode.robotworlds.commands.auxilliarycommands;

import za.co.wethinkcode.robotworlds.Play;
import za.co.wethinkcode.robotworlds.commands.CommandResult;
import za.co.wethinkcode.robotworlds.response.JsonResponse;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.builders.DataMapBuilder;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

public class StateCommand extends AuxiliaryCommand {

    public StateCommand(String robotName) {
        super(robotName, "state");
    }

    @Override
    public JsonResponse execute() {
        World world = Play.getWorld();
        Robot robot = world.getRobot(robotName);
        return
                new JsonResponse(
                        CommandResult.OK,
                        DataMapBuilder.getDataMap(world, robot),
                        robot.getRobotData()
                );
    }
}
