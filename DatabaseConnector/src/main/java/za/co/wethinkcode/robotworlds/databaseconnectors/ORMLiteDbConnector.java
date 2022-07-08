package za.co.wethinkcode.robotworlds.databaseconnectors;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.table.TableUtils;
import za.co.wethinkcode.robotworlds.dao.world.WorldDao;
import za.co.wethinkcode.robotworlds.dbobjects.WorldDataDbObject;
import za.co.wethinkcode.robotworlds.dbobjects.WorldDbObject;
import za.co.wethinkcode.robotworlds.dbobjects.WorldObjectDbData;
import za.co.wethinkcode.robotworlds.dbobjects.doobjects.WorldDO;
import za.co.wethinkcode.robotworlds.dbobjects.doobjects.WorldDataDO;
import za.co.wethinkcode.robotworlds.dbobjects.doobjects.worldobject.MineDO;
import za.co.wethinkcode.robotworlds.dbobjects.doobjects.worldobject.ObstacleDO;
import za.co.wethinkcode.robotworlds.dbobjects.doobjects.worldobject.PitDO;
import za.co.wethinkcode.robotworlds.dbobjects.doobjects.worldobject.WorldObjectDO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ORMLiteDbConnector implements DbConnector {
    private final JdbcPooledConnectionSource connectionSource =
            new JdbcPooledConnectionSource("jdbc:sqlite:world.db");

    public ORMLiteDbConnector() throws SQLException {
        Logger.setGlobalLogLevel(Level.ERROR);
    }

    private void createTables() throws SQLException {
        for (
                Class<?> doClass :
                List.of(
                        WorldDO.class,
                        WorldDataDO.class,
                        ObstacleDO.class,
                        PitDO.class,
                        MineDO.class
                )
        ) {
            TableUtils.createTableIfNotExists(connectionSource, doClass);
        }
    }

    private WorldDataDO getWorldDataDO(WorldDataDbObject worldData) {
        WorldDataDO worldDataDO = new WorldDataDO();
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

    private WorldObjectDO getWorldObjectDO(
            WorldObjectDO worldObjectDO,
            WorldObjectDbData worldObject
    ) {
        worldObjectDO.setWidth(worldObject.getWidth());
        worldObjectDO.setHeight(worldObject.getHeight());
        worldObjectDO.setX(worldObject.getX());
        worldObjectDO.setY(worldObject.getY());
        return worldObjectDO;
    }

    private List<ObstacleDO> getObstaclesDOS(List<WorldObjectDbData> obstacles) {
        List<ObstacleDO> returnList = new ArrayList<>();
        for (WorldObjectDbData obstacle : obstacles) {
            ObstacleDO obstacleDO = new ObstacleDO();
            returnList.add((ObstacleDO) getWorldObjectDO(obstacleDO, obstacle));
        }
        return returnList;
    }

    private List<PitDO> getPitsDOS(List<WorldObjectDbData> pits) {
        List<PitDO> returnList = new ArrayList<>();
        for (WorldObjectDbData pit : pits) {
            PitDO pitDO = new PitDO();
            returnList.add((PitDO) getWorldObjectDO(pitDO, pit));
        }
        return returnList;
    }

    private List<MineDO> getMinesDOS(List<WorldObjectDbData> mines) {
        List<MineDO> returnList = new ArrayList<>();
        for (WorldObjectDbData mine : mines) {
            MineDO mineDO = new MineDO();
            returnList.add((MineDO) getWorldObjectDO(mineDO, mine));
        }
        return returnList;
    }

    private WorldDO saveWorldData(
            String worldName,
            WorldDataDbObject worldData
    ) throws SQLException {
        WorldDO worldDO = new WorldDO();
        worldDO.setName(worldName);
        worldDO.setWorldData(getWorldDataDO(worldData));

        WorldDao worldDao =
                DaoManager.createDao(connectionSource, WorldDO.class);

        worldDao.create(worldDO);

        worldDao.refresh(worldDO);

        return worldDO;
    }

    private void saveObstacles(
            WorldDO worldDO,
            List<WorldObjectDbData> obstacles
    ) {
        for(ObstacleDO obstacleDO : getObstaclesDOS(obstacles)) {
            worldDO.getObstacles().add(obstacleDO);
        }
    }

    private void savePits(
            WorldDO worldDO,
            List<WorldObjectDbData> pits
    ) {
        for(PitDO pitDO : getPitsDOS(pits)) {
            worldDO.getPits().add(pitDO);
        }
    }

    private void saveMines(
            WorldDO worldDO,
            List<WorldObjectDbData> mines
    ) {
        for(MineDO mineDO : getMinesDOS(mines)) {
            worldDO.getMines().add(mineDO);
        }
    }

    @Override
    public void saveWorld(String worldName, WorldDbObject world)
            throws SQLException {

        createTables();

        WorldDO worldDO = saveWorldData(worldName, world.getWorldData());

        saveObstacles(worldDO, world.getObstacles());
        savePits(worldDO, world.getPits());
        saveMines(worldDO, world.getMines());
    }

    private WorldDO getWorldDO(String worldName) throws SQLException {
        WorldDao worldDao =
                DaoManager.createDao(connectionSource, WorldDO.class);
        return worldDao.getWorld(worldName);
    }

    private WorldDataDbObject getWorldData(WorldDataDO worldDataDO) {
        return new WorldDataDbObject(
                worldDataDO.getWidth(),
                worldDataDO.getHeight(),
                worldDataDO.getVisibility(),
                worldDataDO.getRepairTime(),
                worldDataDO.getReloadTime(),
                worldDataDO.getMineTime(),
                worldDataDO.getMaxShield(),
                worldDataDO.getMaxShots()
        );
    }

    private WorldObjectDbData getWorldObject(WorldObjectDO worldObjectDO) {
        return new WorldObjectDbData(
                worldObjectDO.getWidth(),
                worldObjectDO.getHeight(),
                worldObjectDO.getX(),
                worldObjectDO.getY()
        );
    }

    private List<WorldObjectDbData> getObstacles(
            ForeignCollection<ObstacleDO> obstacleDOS
    ) {
        List<WorldObjectDbData> returnList = new ArrayList<>();
        for (ObstacleDO obstacleDO : obstacleDOS) {
            returnList.add(getWorldObject(obstacleDO));
        }
        return returnList;
    }

    private List<WorldObjectDbData> getPits(
            ForeignCollection<PitDO> pitDOS
    ) {
        List<WorldObjectDbData> returnList = new ArrayList<>();
        for (PitDO pitDO : pitDOS) {
            returnList.add(getWorldObject(pitDO));
        }
        return returnList;
    }

    private List<WorldObjectDbData> getMines(
            ForeignCollection<MineDO> mineDOS
    ) {
        List<WorldObjectDbData> returnList = new ArrayList<>();
        for (MineDO mineDO : mineDOS) {
            returnList.add(getWorldObject(mineDO));
        }
        return returnList;
    }

    @Override
    public WorldDbObject restoreWorld(String worldName)
            throws SQLException {
        WorldDO worldDO = getWorldDO(worldName);
        return new WorldDbObject(
                getWorldData(worldDO.getWorldData()),
                getObstacles(worldDO.getObstacles()),
                getPits(worldDO.getPits()),
                getMines(worldDO.getMines())
        );
    }
}
