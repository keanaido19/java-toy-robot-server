package za.co.wethinkcode.robotworlds.domain.world;

import za.co.wethinkcode.robotworlds.database.objects.WorldDataDO;
import za.co.wethinkcode.robotworlds.database.objects.WorldDO;
import za.co.wethinkcode.robotworlds.database.objects.WorldObjectDO;
import za.co.wethinkcode.robotworlds.domain.world.data.WorldConfigData;
import za.co.wethinkcode.robotworlds.domain.world.data.WorldData;
import za.co.wethinkcode.robotworlds.domain.world.objects.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.domain.world.objects.obstacles.SquareObstacle;

import java.util.ArrayList;
import java.util.List;

public class DbConverter {

    private static int[] getWorldData(World world) {
        int[] returnArray = new int[8];
        returnArray[0] = world.getWidth();
        returnArray[1] = world.getHeight();
        returnArray[2] = world.getVisibility();
        returnArray[3] = world.getRepair();
        returnArray[4] = world.getReload();
        returnArray[5] = world.getMine();
        returnArray[6] = world.getShields();
        returnArray[7] = world.getShots();
        return returnArray;
    }

    public static WorldDO getWorldDO(World world) {
        WorldDataDO worldDataDO = new WorldDataDO(getWorldData(world));

        List<WorldObjectDO> obstacleDOS =
                getWorldObjectDOS(world.getObstacles());
        List<WorldObjectDO> pitDOS = new ArrayList<>();
        List<WorldObjectDO> mineDOS = new ArrayList<>();

        return new WorldDO(
                world.getWorldName(),
                worldDataDO,
                obstacleDOS,
                pitDOS,
                mineDOS
        );
    }

    private static List<WorldObjectDO> getWorldObjectDOS(List<Obstacle> objects)
    {
        List<WorldObjectDO> returnList = new ArrayList<>();
        for (Obstacle object : objects) {
            returnList.add(
                    new WorldObjectDO(
                            object.getWidth(),
                            object.getHeight(),
                            object.getBottomLeftX(),
                            object.getBottomLeftY()
                    )
            );
        }
        return returnList;
    }

    private static int[] getWorldConfigData(WorldDataDO worldDataDO) {
        int[] returnArray = new int[6];
        returnArray[0] = worldDataDO.getVisibility();
        returnArray[1] = worldDataDO.getReloadTime();
        returnArray[2] = worldDataDO.getRepairTime();
        returnArray[3] = worldDataDO.getMaxShield();
        returnArray[4] = worldDataDO.getMaxShots();
        returnArray[5] = worldDataDO.getMineTime();
        return  returnArray;
    }

    public static World getWorld(WorldDO worldDO) {
        WorldDataDO worldDataDO = worldDO.getWorldData();

        WorldConfigData worldConfigData =
                new WorldConfigData(
                        getWorldConfigData(worldDataDO)
                );

        WorldData worldData =
                new WorldData(
                        worldDataDO.getWidth(),
                        worldDataDO.getHeight(),
                        worldConfigData
                );

        World world = new World(worldDO.getWorldName(), worldData);

        world.setObstacles(getObstacles(worldDO.getObstacles()));

        return world;
    }

    private static List<Obstacle> getObstacles(List<WorldObjectDO> objects)
    {
        List<Obstacle> returnList = new ArrayList<>();
        for (WorldObjectDO object : objects) {
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
