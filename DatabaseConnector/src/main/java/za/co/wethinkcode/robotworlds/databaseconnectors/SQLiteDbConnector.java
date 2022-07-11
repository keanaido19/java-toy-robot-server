package za.co.wethinkcode.robotworlds.databaseconnectors;

import za.co.wethinkcode.robotworlds.dbobjects.WorldDataDbObject;
import za.co.wethinkcode.robotworlds.dbobjects.WorldDbObject;
import za.co.wethinkcode.robotworlds.dbobjects.WorldObjectDbData;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDbConnector implements DbConnector {
    private final Connection dbConnection;

    public SQLiteDbConnector(String dbUrl) throws SQLException {
        dbConnection =
                DriverManager.getConnection("jdbc:sqlite:" + dbUrl);
    }
    public SQLiteDbConnector() throws SQLException {
        this("world.sqlite");
    }

    public Connection getConnection() {
        return dbConnection;
    }

    private void createWorldDataTable() throws SQLException {
        Statement sqlStatement = dbConnection.createStatement();
        sqlStatement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS `worldData` (" +
                        "`worldDataID` INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "`width` INTEGER NOT NULL , " +
                        "`height` INTEGER NOT NULL , " +
                        "`visibility` INTEGER NOT NULL , " +
                        "`repairTime` INTEGER NOT NULL , " +
                        "`reloadTime` INTEGER NOT NULL , " +
                        "`mineTime` INTEGER NOT NULL , " +
                        "`maxShield` INTEGER NOT NULL , " +
                        "`maxShots` INTEGER NOT NULL )"
        );
    }

    private void createObjectTable(String tableName) throws SQLException {
        Statement sqlStatement = dbConnection.createStatement();
        sqlStatement.executeUpdate(
                MessageFormat.format(
                        "CREATE TABLE IF NOT EXISTS `{0}` (" +
                                "`{0}ID` INTEGER PRIMARY KEY AUTOINCREMENT , " +
                                "`width` INTEGER NOT NULL , " +
                                "`height` INTEGER NOT NULL , " +
                                "`x` INTEGER NOT NULL , " +
                                "`y` INTEGER NOT NULL , " +
                                "`worldDO_id` INTEGER " +
                                ")",
                        tableName
                )
        );
    }

    private void createWorldTable() throws SQLException {
        Statement sqlStatement = dbConnection.createStatement();
        sqlStatement.executeUpdate(
                "CREATE TABLE IF NOT EXISTS `world` (" +
                        "`worldID` INTEGER PRIMARY KEY AUTOINCREMENT , " +
                        "`name` VARCHAR NOT NULL , " +
                        "`worldDataDO_id` INTEGER NOT NULL ,  " +
                        "UNIQUE (`name`),  " +
                        "UNIQUE (`worldDataDO_id`)" +
                        ")"
        );
    }

    private void createTables() throws SQLException {
        createWorldTable();
        createWorldDataTable();
        createObjectTable("obstacles");
        createObjectTable("mines");
        createObjectTable("pits");
    }

    private int getLastTableIndex(String tableName) throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet result =
                statement.executeQuery(
                        MessageFormat.format(
                                "SELECT * FROM {0} WHERE {0}ID = " +
                                        "(SELECT MAX({0}ID) FROM {0})",
                                tableName
                        )
                );
        if (result.isClosed()) return 0;
        return result.getInt(tableName + "ID");
    }

    private void saveWorldName(String worldName, int worldDataID)
            throws SQLException {
        Statement statement = dbConnection.createStatement();
        String sqlStatement;
        if (worldName == null) {
            sqlStatement = MessageFormat.format(
                    "INSERT INTO world(worldID, name, worldDataDO_id) " +
                            "VALUES({0}, {0}, {1})",
                    getLastTableIndex("world") + 1,
                    worldDataID
            );
        } else {
            sqlStatement = String.format(
                    "INSERT INTO world(name, worldDataDO_id) " +
                            "VALUES('%s', %d)",
                    worldName,
                    worldDataID
            );
        }

        try {
            statement.executeUpdate(sqlStatement);
        } catch (SQLException e) {
            throw new SQLException("Name is already saved in database!");
        }
    }

    private void saveWorldData(WorldDataDbObject worldData)
            throws SQLException {
        Statement statement = dbConnection.createStatement();
        statement.executeUpdate(
                MessageFormat.format(
                        "INSERT INTO worldData" +
                                "(width, height, visibility, repairTime, " +
                                "reloadTime, mineTime, maxShield, " +
                                "maxShots) " +
                                "VALUES({0}, {1}, {2}, {3}, {4}, {5}, {6}, " +
                                "{7})",
                        worldData.getWidth(),
                        worldData.getHeight(),
                        worldData.getVisibility(),
                        worldData.getRepairTime(),
                        worldData.getReloadTime(),
                        worldData.getMineTime(),
                        worldData.getMaxShield(),
                        worldData.getMaxShots()
                )
        );
    }

    private void saveObjects(
            String tableName,
            List<WorldObjectDbData> objects,
            int worldID
    ) throws SQLException {
        Statement statement = dbConnection.createStatement();
        for (WorldObjectDbData object : objects) {
            statement.executeUpdate(
                    MessageFormat.format(
                            "INSERT INTO {0}" +
                                    "(width, height, x, y, worldDO_id) " +
                                    "VALUES({1}, {2}, {3}, {4}, {5})",
                            tableName,
                            object.getWidth(),
                            object.getHeight(),
                            object.getX(),
                            object.getY(),
                            worldID
                    )
            );
        }
    }

    private void saveObstacles(List<WorldObjectDbData> obstacles, int worldID)
            throws SQLException {
        saveObjects("obstacles", obstacles, worldID);
    }

    private void savePits(List<WorldObjectDbData> pits, int worldID)
            throws SQLException {
        saveObjects("pits", pits, worldID);
    }

    private void saveMines(List<WorldObjectDbData> mines, int worldID)
            throws SQLException {
        saveObjects("mines", mines, worldID);
    }

    @Override
    public void saveWorld(String worldName, WorldDbObject world)
            throws SQLException {
        createTables();
        saveWorldData(world.getWorldData());
        saveWorldName(worldName, getLastTableIndex("worldData"));

        int worldID = getLastTableIndex("world");

        saveObstacles(world.getObstacles(), worldID);
        savePits(world.getPits(), worldID);
        saveMines(world.getMines(), worldID);
    }

    private int getWorldID(String worldName) throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet =
                statement.executeQuery(
                        String.format(
                                "SELECT * FROM world WHERE name = \"%s\"",
                                worldName
                        )
                );
        if (resultSet.isClosed())
            throw new SQLException("World name does not exist!");

        return resultSet.getInt("worldID");
    }

    private int getWorldDataID(String worldName) throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet =
                statement.executeQuery(
                        String.format(
                                "SELECT * FROM world WHERE name = \"%s\"",
                                worldName
                        )
                );
        if (resultSet.isClosed())
            throw new SQLException("World name does not exist!");

        return resultSet.getInt("worldDataDO_id");
    }

    private ResultSet getResultsLinkedToWorldID(
            String tableName,
            int worldID
    ) throws SQLException {
        Statement statement = dbConnection.createStatement();
        return statement.executeQuery(
                MessageFormat.format(
                        "SELECT * FROM {0} WHERE worldDO_id = {1}",
                        tableName,
                        worldID
                )
        );
    }

    private WorldDataDbObject getWorldData(int worldDataID)
            throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet =
                statement.executeQuery(
                        MessageFormat.format(
                                "SELECT * FROM worldData WHERE " +
                                        "worldDataID = {0}",
                                worldDataID
                        )
                );
        if (resultSet.isClosed()) return new WorldDataDbObject();
        return new WorldDataDbObject(
                resultSet.getInt("width"),
                resultSet.getInt("height"),
                resultSet.getInt("visibility"),
                resultSet.getInt("repairTime"),
                resultSet.getInt("reloadTime"),
                resultSet.getInt("mineTime"),
                resultSet.getInt("maxShield"),
                resultSet.getInt("maxShots")
        );
    }

    private List<WorldObjectDbData> getWorldObjects(
            String tableName,
            int worldID
    ) throws SQLException {
        ArrayList<WorldObjectDbData> returnList = new ArrayList<>();

        ResultSet resultSet =
                getResultsLinkedToWorldID(tableName, worldID);

        while (!resultSet.isClosed() && resultSet.next()) {
            returnList.add(
                    new WorldObjectDbData(
                            resultSet.getInt("width"),
                            resultSet.getInt("height"),
                            resultSet.getInt("x"),
                            resultSet.getInt("y")
                    )
            );
        }

        return returnList;
    }

    @Override
    public WorldDbObject restoreWorld(String worldName) throws SQLException {
        int worldID = getWorldID(worldName);
        return new WorldDbObject(
                worldName,
                getWorldData(getWorldDataID(worldName)),
                getWorldObjects("obstacles", worldID),
                getWorldObjects("pits", worldID),
                getWorldObjects("mines", worldID)
        );
    }

    private void deleteRowUsingWorldID(String tableName, int worldID)
            throws SQLException {
        dbConnection.createStatement().executeUpdate(
                String.format(
                        "DELETE FROM %s WHERE worldDO_id = %d",
                        tableName,
                        worldID
                )
        );
    }

    private void deleteWorldDataRow(String worldName) throws SQLException {
        dbConnection.createStatement().executeUpdate(
                String.format(
                        "DELETE FROM worldData WHERE worldDataID = %d",
                        getWorldDataID(worldName)
                )
        );
    }

    private void deleteWorldRow(String worldName) throws  SQLException {
        dbConnection.createStatement().executeUpdate(
                String.format(
                        "DELETE FROM world WHERE worldID = %d",
                        getWorldID(worldName)
                )
        );
    }

    @Override
    public void deleteWorld(String worldName) throws SQLException {
        int worldID = getWorldID(worldName);

        deleteWorldDataRow(worldName);
        deleteWorldRow(worldName);

        for (
                String tableName :
                List.of("pits", "obstacles", "mines")
        ) {
            deleteRowUsingWorldID(tableName, worldID);
        }
    }
}
