package za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.enums.Status;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

public class ReloadCommand extends AuxiliaryCommand {
    int milliSeconds;

    public ReloadCommand(String robotName) {
        super(robotName, "reload");
    }

    @Override
    public ServerResponse execute(ClientHandler clientHandler) {
        Robot clientRobot = clientHandler.getRobot();
        int maximumShots = clientRobot.getMaximumShots();

        if (0 == maximumShots) return ServerResponse.shotsErrorResponse();

        if (Status.RELOAD.equals(clientRobot.getRobotStatus()))
            return
                    ServerResponse.getSuccessResponse(
                            "message",
                            "Robot is reloading",
                            clientRobot.getRobotData()
                    );

        if (clientRobot.getShots() != maximumShots) {
            milliSeconds =
                    clientHandler.getWorld().getWorldData()
                            .getReloadTime() * 1000;
            clientRobot.setRobotStatus(Status.RELOAD);
            clientRobot.timer(Status.RELOAD, milliSeconds);
            return new StateCommand(robotName).execute(clientHandler);
        }

        return
                ServerResponse.getSuccessResponse(
                        "message",
                        "Gun is at maximum shots",
                        clientRobot.getRobotData()
                );
    }
}
