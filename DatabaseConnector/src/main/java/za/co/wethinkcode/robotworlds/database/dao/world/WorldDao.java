package za.co.wethinkcode.robotworlds.database.dao.world;

import com.j256.ormlite.dao.Dao;
import za.co.wethinkcode.robotworlds.database.objects.orm.World;

import java.sql.SQLException;

public interface WorldDao extends Dao<World, Integer> {
    World getWorld(String worldName) throws SQLException;
}
