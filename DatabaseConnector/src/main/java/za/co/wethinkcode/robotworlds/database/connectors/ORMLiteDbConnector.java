package za.co.wethinkcode.robotworlds.database.connectors;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.table.TableUtils;
import za.co.wethinkcode.robotworlds.database.dao.world.WorldDao;
import za.co.wethinkcode.robotworlds.database.objects.WorldDataDO;
import za.co.wethinkcode.robotworlds.database.objects.WorldDO;
import za.co.wethinkcode.robotworlds.database.objects.WorldObjectDO;
import za.co.wethinkcode.robotworlds.database.objects.orm.World;
import za.co.wethinkcode.robotworlds.database.objects.orm.WorldData;
import za.co.wethinkcode.robotworlds.database.objects.orm.worldobject.Mine;
import za.co.wethinkcode.robotworlds.database.objects.orm.worldobject.Obstacle;
import za.co.wethinkcode.robotworlds.database.objects.orm.worldobject.Pit;
import za.co.wethinkcode.robotworlds.database.objects.orm.worldobject.WorldObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ORMLiteDbConnector implements DbConnector {
    private final JdbcPooledConnectionSource connectionSource;

    public ORMLiteDbConnector(String dbUrl) throws SQLException {
        Logger.setGlobalLogLevel(Level.ERROR);
        connectionSource =
                new JdbcPooledConnectionSource("jdbc:sqlite:" + dbUrl);
    }

    public ORMLiteDbConnector() throws SQLException {
        this("world.sqlite");
    }

    private void createTables() throws SQLException {
        for (
                Class<?> doClass :
                List.of(
                        World.class,
                        WorldData.class,
                        Obstacle.class,
                        Pit.class,
                        Mine.class
                )
        ) {
            TableUtils.createTableIfNotExists(connectionSource, doClass);
        }
    }

    private WorldData getWorldDataDO(WorldDataDO worldData) {
        WorldData worldDataDO = new WorldData();
        worldDataDO.setWidth(worldData.getWidth());
        worldDataDO.setHeight(worldData.getHeight());
        worldDataDO.setVisibility(worldData.getVisibility());
        worldDataDO.setRepairTime(worldData.getRepairTime());
        worldDataDO.setReloadTime(worldData.getReloadTime());
        worldDataDO.setMineTime(worldData.getMineTime());
        worldDataDO.setMaxShield(worldData.getMaxShield());
        worldDataDO.setMaxShots(worldData.getMaxShots());
        return worldDataDO;
    }

    private WorldObject getWorldObjectDO(
            WorldObject worldObjectDO,
            WorldObjectDO worldObject
    ) {
        worldObjectDO.setWidth(worldObject.getWidth());
        worldObjectDO.setHeight(worldObject.getHeight());
        worldObjectDO.setX(worldObject.getX());
        worldObjectDO.setY(worldObject.getY());
        return worldObjectDO;
    }

    private List<Obstacle> getObstaclesDOS(List<WorldObjectDO> obstacles) {
        List<Obstacle> returnList = new ArrayList<>();
        for (WorldObjectDO obstacle : obstacles) {
            Obstacle obstacleDO = new Obstacle();
            returnList.add((Obstacle) getWorldObjectDO(obstacleDO, obstacle));
        }
        return returnList;
    }

    private List<Pit> getPitsDOS(List<WorldObjectDO> pits) {
        List<Pit> returnList = new ArrayList<>();
        for (WorldObjectDO pit : pits) {
            Pit pitDO = new Pit();
            returnList.add((Pit) getWorldObjectDO(pitDO, pit));
        }
        return returnList;
    }

    private List<Mine> getMinesDOS(List<WorldObjectDO> mines) {
        List<Mine> returnList = new ArrayList<>();
        for (WorldObjectDO mine : mines) {
            Mine mineDO = new Mine();
            returnList.add((Mine) getWorldObjectDO(mineDO, mine));
        }
        return returnList;
    }

    private World saveWorldData(
            String worldName,
            WorldDataDO worldData
    ) throws SQLException {
        World world = new World();
        world.setName(worldName);
        world.setWorldData(getWorldDataDO(worldData));

        WorldDao worldDao =
                DaoManager.createDao(connectionSource, World.class);

        worldDao.create(world);

        worldDao.refresh(world);

        return world;
    }

    private void saveObstacles(
            World world,
            List<WorldObjectDO> obstacles
    ) {
        for(Obstacle obstacle : getObstaclesDOS(obstacles)) {
            world.getObstacles().add(obstacle);
        }
    }

    private void savePits(
            World world,
            List<WorldObjectDO> pits
    ) {
        for(Pit pit : getPitsDOS(pits)) {
            world.getPits().add(pit);
        }
    }

    private void saveMines(
            World world,
            List<WorldObjectDO> mines
    ) {
        for(Mine mine : getMinesDOS(mines)) {
            world.getMines().add(mine);
        }
    }

    @Override
    public void saveWorld(String worldName, WorldDO world)
            throws SQLException {

        createTables();

        World worldDO = saveWorldData(worldName, world.getWorldData());

        saveObstacles(worldDO, world.getObstacles());
        savePits(worldDO, world.getPits());
        saveMines(worldDO, world.getMines());
    }

    private World getWorldDO(String worldName) throws SQLException {
        WorldDao worldDao =
                DaoManager.createDao(connectionSource, World.class);
        return worldDao.getWorld(worldName);
    }

    private WorldDataDO getWorldData(WorldData worldData) {
        return new WorldDataDO(
                worldData.getWidth(),
                worldData.getHeight(),
                worldData.getVisibility(),
                worldData.getRepairTime(),
                worldData.getReloadTime(),
                worldData.getMineTime(),
                worldData.getMaxShield(),
                worldData.getMaxShots()
        );
    }

    private WorldObjectDO getWorldObject(WorldObject worldObject) {
        return new WorldObjectDO(
                worldObject.getWidth(),
                worldObject.getHeight(),
                worldObject.getX(),
                worldObject.getY()
        );
    }

    private List<WorldObjectDO> getObstacles(
            ForeignCollection<Obstacle> obstacles
    ) {
        List<WorldObjectDO> returnList = new ArrayList<>();
        for (Obstacle obstacle : obstacles) {
            returnList.add(getWorldObject(obstacle));
        }
        return returnList;
    }

    private List<WorldObjectDO> getPits(
            ForeignCollection<Pit> pits
    ) {
        List<WorldObjectDO> returnList = new ArrayList<>();
        for (Pit pit : pits) {
            returnList.add(getWorldObject(pit));
        }
        return returnList;
    }

    private List<WorldObjectDO> getMines(
            ForeignCollection<Mine> mines
    ) {
        List<WorldObjectDO> returnList = new ArrayList<>();
        for (Mine mine : mines) {
            returnList.add(getWorldObject(mine));
        }
        return returnList;
    }

    @Override
    public WorldDO restoreWorld(String worldName)
            throws SQLException {
        World world = getWorldDO(worldName);
        if (null == world) throw new SQLException();
        return new WorldDO(
                worldName,
                getWorldData(world.getWorldData()),
                getObstacles(world.getObstacles()),
                getPits(world.getPits()),
                getMines(world.getMines())
        );
    }

    @Override
    public void deleteWorld(String worldName) throws SQLException {
        WorldDao worldDao =
                DaoManager.createDao(connectionSource, World.class);

        World world = worldDao.getWorld(worldName);
        if (null == world) throw new SQLException();

        worldDao.deleteById(world.getWorldID());
    }
}
