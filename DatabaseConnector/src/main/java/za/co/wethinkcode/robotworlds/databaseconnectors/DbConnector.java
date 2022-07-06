package za.co.wethinkcode.robotworlds.databaseconnectors;

import za.co.wethinkcode.robotworlds.dbobjects.WorldDbObject;

public interface DbConnector {
    void saveWorld(String worldName, WorldDbObject world) throws Exception;

    WorldDbObject restoreWorld(String worldName) throws Exception;
}
