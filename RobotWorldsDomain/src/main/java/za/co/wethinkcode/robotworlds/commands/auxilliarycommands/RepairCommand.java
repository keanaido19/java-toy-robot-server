package za.co.wethinkcode.robotworlds.commands.auxilliarycommands;

import za.co.wethinkcode.robotworlds.response.JsonResponse;
import za.co.wethinkcode.robotworlds.world.enums.Status;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

public class RepairCommand extends AuxiliaryCommand {

    int milliSeconds;

    public RepairCommand(String robotName) {
        super(robotName, "repair");
    }

    @Override
    public JsonResponse execute() {
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
            return new StateCommand(robotName).execute();
        }

        return
                JsonResponse.getSuccessResponse(
                        "message",
                        "Shields are already at maximum",
                        robot.getRobotData()
                );
    }
}
