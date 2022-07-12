package za.co.wethinkcode.robotworlds.domain.world.builders;

import za.co.wethinkcode.robotworlds.domain.world.World;
import za.co.wethinkcode.robotworlds.domain.world.data.LookData;
import za.co.wethinkcode.robotworlds.domain.world.objects.robots.Robot;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class DataMapBuilder {
    public static LinkedHashMap<String, Object> getDataMap(
            World world,
            Robot robot,
            List<LookData> objects
    ) {
        return new LinkedHashMap<>() {
            {
                put("visibility", world.getVisibility());
                put("position", robot.getPosition().getPositionAsList());
                put("objects", objects);
            }
        };
    }

    public static LinkedHashMap<String, Object> getDataMap(
            World world,
            Robot robot
    ) {
        return getDataMap(world, robot, new ArrayList<>());
    }
}
