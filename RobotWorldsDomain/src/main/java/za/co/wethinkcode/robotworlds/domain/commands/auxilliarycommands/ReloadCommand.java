package za.co.wethinkcode.robotworlds.domain.commands.auxilliarycommands;

import za.co.wethinkcode.robotworlds.domain.Play;
import za.co.wethinkcode.robotworlds.domain.response.JsonResponse;
import za.co.wethinkcode.robotworlds.domain.world.enums.Status;
import za.co.wethinkcode.robotworlds.domain.world.objects.robots.Robot;

public class ReloadCommand extends AuxiliaryCommand {

    int milliSeconds;

    public ReloadCommand(String robotName) {
        super(robotName, "reload");
    }

    @Override
    public JsonResponse execute(Play play) {
        Robot robot = world.getRobot(robotName);
        int maximumShots = robot.getMaximumShots();

        if (0 == maximumShots) return JsonResponse.shotsErrorResponse();

        if (Status.RELOAD.equals(robot.getRobotStatus()))
            return
                    JsonResponse.getSuccessResponse(
                            "message",
                            "Robot is reloading",
                            robot.getRobotData()
                    );

        if (robot.getShots() != maximumShots) {
            milliSeconds = world.getReload() * 1000;
            robot.setRobotStatus(Status.RELOAD);
            robot.timer(Status.RELOAD, milliSeconds);
            return new StateCommand(robotName).execute(play);
        }

        return
                JsonResponse.getSuccessResponse(
                        "message",
                        "Gun is at maximum shots",
                        robot.getRobotData()
                );
    }
}
