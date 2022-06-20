package za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.enums.Status;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

public class RepairCommand extends AuxiliaryCommand {
    int milliSeconds;

    public RepairCommand(String robotName) {
        super(robotName, "repair");
    }

    @Override
    public ServerResponse execute(ClientHandler clientHandler) {
        Robot clientRobot = clientHandler.getRobot();
        int maximumShields = clientRobot.getMaximumShields();

        if (0 == maximumShields) return ServerResponse.shieldErrorResponse();

        if (Status.REPAIR.equals(clientRobot.getRobotStatus()))
            return
                    ServerResponse.getSuccessResponse(
                            "message",
                            "Robot is repairing",
                            clientRobot.getRobotData()
                    );

        if (clientRobot.getShields() != maximumShields) {
            milliSeconds =
                    clientHandler.getWorld().getWorldData()
                            .getReloadTime() * 1000;
            clientRobot.setRobotStatus(Status.REPAIR);
            clientRobot.timer(Status.REPAIR, milliSeconds);
            return new StateCommand(robotName).execute(clientHandler);
        }

        return
                ServerResponse.getSuccessResponse(
                        "message",
                        "Shields are already at maximum",
                        clientRobot.getRobotData()
                );
    }
}
