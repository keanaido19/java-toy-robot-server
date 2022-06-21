package za.co.wethinkcode.robotworlds.world.builders;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.world.data.LookData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataMapBuilder {
    public static HashMap<String, Object> getDataMap(
            ClientHandler clientHandler,
            List<LookData> objects
    ) {
        return new HashMap<>() {
            {
                put("visibility", clientHandler.getWorld().getVisibility());
                put(
                        "position",
                        clientHandler.getRobot()
                                .getPosition().getPositionAsList()
                );
                put("objects", objects);
            }
        };
    }

    public static HashMap<String, Object> getDataMap(
            ClientHandler clientHandler
    ) {
        return getDataMap(clientHandler, new ArrayList<>());
    }
}
