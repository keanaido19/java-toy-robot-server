package za.co.wethinkcode.robotworlds.world;

import za.co.wethinkcode.robotworlds.dbobjects.WorldDataDbObject;
import za.co.wethinkcode.robotworlds.dbobjects.WorldDbObject;
import za.co.wethinkcode.robotworlds.dbobjects.WorldObjectDbData;
import za.co.wethinkcode.robotworlds.world.data.WorldConfigData;
import za.co.wethinkcode.robotworlds.world.data.WorldData;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.SquareObstacle;

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

    public static World getWorld(WorldDbObject worldDb) {
        WorldDataDbObject worldDataDb = worldDb.getWorldData();

        WorldConfigData worldConfigData =
                new WorldConfigData(
                        worldDataDb.getVisibility(),
                        worldDataDb.getReloadTime(),
                        worldDataDb.getRepairTime(),
                        worldDataDb.getMineTime(),
                        worldDataDb.getMaxShield(),
                        worldDataDb.getMaxShots()
                );

        WorldData worldData =
                new WorldData(
                        worldDataDb.getWidth(),
                        worldDataDb.getHeight(),
                        worldConfigData
                );

        World world = new World(worldData);

        world.setObstacles(getObstacles(worldDb.getObstacles()));

        return world;
    }

    private static List<Obstacle> getObstacles(List<WorldObjectDbData> objects)
    {
        List<Obstacle> returnList = new ArrayList<>();
        for (WorldObjectDbData object : objects) {
            returnList.add(
                    new SquareObstacle(
                            object.getX(),
                            object.getY(),
                            object.getWidth()
                    )
            );
        }
        return returnList;
    }
}
