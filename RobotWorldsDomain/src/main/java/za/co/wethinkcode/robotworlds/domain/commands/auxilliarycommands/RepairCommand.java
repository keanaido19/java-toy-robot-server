package za.co.wethinkcode.robotworlds.domain.commands.auxilliarycommands;

import za.co.wethinkcode.robotworlds.domain.Play;
import za.co.wethinkcode.robotworlds.domain.response.JsonResponse;
import za.co.wethinkcode.robotworlds.domain.world.enums.Status;
import za.co.wethinkcode.robotworlds.domain.world.objects.robots.Robot;

public class RepairCommand extends AuxiliaryCommand {

    int milliSeconds;

    public RepairCommand(String robotName) {
        super(robotName, "repair");
    }

    @Override
    public JsonResponse execute(Play play) {
        Robot robot = world.getRobot(robotName);
        int maximumShields = robot.getMaximumShields();

        if (0 == maximumShields) return JsonResponse.shieldErrorResponse();

        if (Status.REPAIR.equals(robot.getRobotStatus()))
            return
                    JsonResponse.getSuccessResponse(
                            "message",
                            "Robot is repairing",
                            robot.getRobotData()
                    );

        if (robot.getShields() != maximumShields) {
            milliSeconds = world.getReload() * 1000;
            robot.setRobotStatus(Status.REPAIR);
            robot.timer(Status.REPAIR, milliSeconds);
            return new StateCommand(robotName).execute(play);
        }

        return
                JsonResponse.getSuccessResponse(
                        "message",
                        "Shields are already at maximum",
                        robot.getRobotData()
                );
    }
}
