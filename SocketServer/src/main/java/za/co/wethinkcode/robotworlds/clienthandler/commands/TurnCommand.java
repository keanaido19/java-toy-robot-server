package za.co.wethinkcode.robotworlds.clienthandler.commands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.StateCommand;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.enums.Status;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.List;

public class TurnCommand extends Command {
    public TurnCommand(
            String robotName,
            String command,
            List<Object> commandArguments
    ) {
        super(robotName, command, commandArguments);
    }

    @Override
    public ServerResponse execute(ClientHandler clientHandler) {
        Robot clientRobot = clientHandler.getRobot();

        if ("right".equals(commandArguments.get(0)))
            clientRobot.turnRight();
        else
            clientRobot.turnLeft();

        clientRobot.setRobotStatus(Status.MOVING);
        clientRobot.timer(Status.MOVING, 66);

        return new StateCommand(robotName).execute(clientHandler);
    }
}
