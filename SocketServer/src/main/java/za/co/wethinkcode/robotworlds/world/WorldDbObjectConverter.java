package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.dbobjects.WorldDataDbObject;
import za.co.wethinkcode.robotworlds.dbobjects.WorldDbObject;
import za.co.wethinkcode.robotworlds.world.data.WorldData;

public class WorldDbObjectConverter {
    public static WorldDbObject getWorldDbObject(World world) {
        WorldData worldData = world.getWorldData();

        WorldDataDbObject worldDataDbObject =
                new WorldDataDbObject(
                        worldData.getWidth(),
                        worldData.getHeight(),
                        worldData.getWorldConfigData().getRepair(),
                        worldData.getWorldConfigData().getReload(),
                        worldData.getWorldConfigData().getMine(),
                        worldData.getWorldConfigData().getRepair()
                );
        return null;
    }
}
