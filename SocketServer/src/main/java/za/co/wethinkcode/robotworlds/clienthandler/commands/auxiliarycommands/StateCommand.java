package za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.clienthandler.commands.CommandResult;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class StateCommand extends AuxiliaryCommand {
    public StateCommand(String robotName) {
        super(robotName, "state");
    }

    @Override
    public ServerResponse execute(ClientHandler clientHandler) {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("visibility", clientHandler.getWorld().getVisibility());
        data.put(
                "position",
                clientHandler.getRobot().getPosition().getPositionAsList()
        );
        data.put("objects", new ArrayList<>());

        return
                new ServerResponse(
                        CommandResult.OK,
                        data,
                        clientHandler.getRobot().getRobotData()
                );
    }
}
