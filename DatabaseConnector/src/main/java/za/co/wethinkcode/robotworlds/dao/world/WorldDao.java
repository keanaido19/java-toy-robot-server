package za.co.wethinkcode.robotworlds.dao.world;

import com.j256.ormlite.dao.Dao;
import za.co.wethinkcode.robotworlds.dbobjects.doobjects.WorldDO;

import java.sql.SQLException;

public interface WorldDao extends Dao<WorldDO, Integer> {
    WorldDO getWorld(String worldName) throws SQLException;
}
