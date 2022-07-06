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
        WorldDataDbObject worldData =
                new WorldDataDbObject(
                        world.getWidth(),
                        world.getHeight(),
                        world.getVisibility(),
                        world.getRepair(),
                        world.getReload(),
                        world.getMine(),
                        world.getShields(),
                        world.getShots()
                );

        List<WorldObjectDbData> obstacles = getDbObjects(world.getObstacles());
        List<WorldObjectDbData> pits = new ArrayList<>();
        List<WorldObjectDbData> mines = new ArrayList<>();

        return new WorldDbObject(worldData, obstacles, pits, mines);
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

    public static World getWorld(WorldObjectDbData worldObjectDb) {
        return null;
    }
}
