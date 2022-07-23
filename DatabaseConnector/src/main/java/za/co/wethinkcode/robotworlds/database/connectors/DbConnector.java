package za.co.wethinkcode.robotworlds.database.connectors;

import za.co.wethinkcode.robotworlds.database.objects.WorldDO;

import java.sql.SQLException;

public interface DbConnector {
    void saveWorld(String worldName, WorldDO world) throws SQLException;

    WorldDO restoreWorld(String worldName) throws SQLException;

    void deleteWorld(String worldName) throws SQLException;
}
