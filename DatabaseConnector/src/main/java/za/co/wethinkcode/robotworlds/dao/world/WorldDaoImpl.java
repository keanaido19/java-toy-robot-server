package za.co.wethinkcode.robotworlds.dao.world;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import za.co.wethinkcode.robotworlds.dbobjects.doobjects.WorldDO;

import java.sql.SQLException;

public class WorldDaoImpl
        extends BaseDaoImpl<WorldDO, Integer>
        implements WorldDao
{
    public WorldDaoImpl(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, WorldDO.class);
    }

    @Override
    public WorldDO getWorld(String worldName) throws SQLException {
        return super.queryBuilder()
                .where()
                .eq("name", worldName)
                .queryForFirst();
    }
}
