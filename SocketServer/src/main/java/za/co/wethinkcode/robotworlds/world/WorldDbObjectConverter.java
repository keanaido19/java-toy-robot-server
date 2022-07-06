package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.dbobjects.WorldDataDbObject;
import za.co.wethinkcode.robotworlds.dbobjects.WorldDbObject;
import za.co.wethinkcode.robotworlds.dbobjects.WorldObjectDbData;
import za.co.wethinkcode.robotworlds.world.data.WorldData;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class WorldDbObjectConverter {
    public static WorldDbObject getWorldDbObject(World world) {
        WorldData worldData = world.getWorldData();

        WorldDataDbObject worldDataDbObject =
                new WorldDataDbObject(
                        worldData.getWidth(),
                        worldData.getHeight(),
                        worldData.getWorldConfigData().getVisibility(),
                        worldData.getWorldConfigData().getRepair(),
                        worldData.getWorldConfigData().getReload(),
                        worldData.getWorldConfigData().getMine(),
                        worldData.getWorldConfigData().getShields()
                );

        List<WorldObjectDbData> obstacles = getDbObjects(world.getObstacles());
        List<WorldObjectDbData> pits = new ArrayList<>();
        List<WorldObjectDbData> mines = new ArrayList<>();

        return new WorldDbObject(worldDataDbObject, obstacles, pits, mines);
    }

    private static List<WorldObjectDbData> getDbObjects(List<Obstacle> objects)
    {
        List<WorldObjectDbData> returnList = new ArrayList<>();
        for (Obstacle object : objects) {
            returnList.add(
                    new WorldObjectDbData(
                            object.getWidth(),
                            object.getHeight(),
                            object.getBottomLeftX(),
                            object.getBottomLeftY()
                    )
            );
        }
        return returnList;
    }
}
