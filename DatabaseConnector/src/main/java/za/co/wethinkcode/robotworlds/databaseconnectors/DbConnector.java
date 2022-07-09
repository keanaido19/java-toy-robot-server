package za.co.wethinkcode.robotworlds.databaseconnectors;

import za.co.wethinkcode.robotworlds.dbobjects.WorldDbObject;

import java.sql.SQLException;

public interface DbConnector {
    void saveWorld(String worldName, WorldDbObject world) throws SQLException;

    WorldDbObject restoreWorld(String worldName) throws SQLException;

    void deleteWorld(String worldName) throws SQLException;
}
