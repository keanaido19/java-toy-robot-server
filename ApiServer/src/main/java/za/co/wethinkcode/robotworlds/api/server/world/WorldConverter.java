package za.co.wethinkcode.robotworlds.api.server.world;

import za.co.wethinkcode.robotworlds.domain.world.objects.obstacles.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class WorldConverter {

    private static WorldObject getWorldObject(Obstacle obstacle) {
        WorldObject worldObject = new WorldObject();

        worldObject.setWidth(obstacle.getWidth());
        worldObject.setHeight(obstacle.getHeight());
        worldObject.setX(obstacle.getBottomLeftX());
        worldObject.setY(obstacle.getBottomLeftY());

        return worldObject;
    }

    private static List<WorldObject> getObstacles(List<Obstacle> obstacles) {
        List<WorldObject> returnList = new ArrayList<>();
        for (Obstacle obstacle : obstacles) {
            returnList.add(getWorldObject(obstacle));
        }
        return returnList;
    }

    private static WorldData getWorldData(
            za.co.wethinkcode.robotworlds.domain.world.World domainWorld
    ) {
        WorldData worldData = new WorldData();

        worldData.setWidth(domainWorld.getWidth());
        worldData.setHeight(domainWorld.getHeight());
        worldData.setVisibility(domainWorld.getVisibility());
        worldData.setRepairTime(domainWorld.getRepair());
        worldData.setReloadTime(domainWorld.getReload());
        worldData.setMineTime(domainWorld.getMine());
        worldData.setMaxShield(domainWorld.getShields());
        worldData.setMaxShots(domainWorld.getShots());

        return worldData;
    }

    public static World getWorld(
            za.co.wethinkcode.robotworlds.domain.world.World domainWorld
    ) {

        World world = new World();

        world.setWorldName(domainWorld.getWorldName());
        world.setWorldData(getWorldData(domainWorld));
        world.setObstacles(getObstacles(domainWorld.getObstacles()));
        world.setMines(new ArrayList<>());
        world.setPits(new ArrayList<>());

        return world;
    }
}
