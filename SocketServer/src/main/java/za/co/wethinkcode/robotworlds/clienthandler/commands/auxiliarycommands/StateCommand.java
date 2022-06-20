package za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.response.ServerResponse;

public class StateCommand extends AuxiliaryCommand {
    public StateCommand(String robotName) {
        super(robotName, "state");
    }

    @Override
    public ServerResponse execute(ClientHandler clientHandler) {
        return
                ServerResponse.getSuccessResponse(
                        "message",
                        "done",
                        clientHandler.getRobot().getRobotData()
                );
    }
}
