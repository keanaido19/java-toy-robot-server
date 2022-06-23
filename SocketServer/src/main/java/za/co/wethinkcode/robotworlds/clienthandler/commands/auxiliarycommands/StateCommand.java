package za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.clienthandler.commands.CommandResult;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.builders.DataMapBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class StateCommand extends AuxiliaryCommand {
    public StateCommand(String robotName) {
        super(robotName, "state");
    }

    @Override
    public ServerResponse execute(ClientHandler clientHandler) {
        return
                new ServerResponse(
                        CommandResult.OK,
                        DataMapBuilder.getDataMap(
                                clientHandler,
                                clientHandler.getRobot(robotName)
                        ),
                        clientHandler.getRobot(robotName).getRobotData()
                );
    }
}
