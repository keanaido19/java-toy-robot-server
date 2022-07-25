package za.co.wethinkcode.robotworlds.database.connectors;

import za.co.wethinkcode.robotworlds.database.TableName;
import za.co.wethinkcode.robotworlds.database.objects.WorldDO;
import za.co.wethinkcode.robotworlds.database.objects.WorldDataDO;
import za.co.wethinkcode.robotworlds.database.objects.WorldObjectDO;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDbConnector implements DbConnector {
    private final Connection dbConnection;

    private String worldName;
    private int worldID;

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

    private void createObjectTable(TableName tableName) throws SQLException {
        Statement sqlStatement = dbConnection.createStatement();
        sqlStatement.executeUpdate(
                MessageFormat.format(
                        "CREATE TABLE IF NOT EXISTS `{0}` (" +
                                "`{0}ID` INTEGER PRIMARY KEY AUTOINCREMENT , " +
                                "`width` INTEGER NOT NULL , " +
                                "`height` INTEGER NOT NULL , " +
                                "`x` INTEGER NOT NULL , " +
                                "`y` INTEGER NOT NULL , " +
                                "`world_id` INTEGER " +
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
                        "`worldData_id` INTEGER NOT NULL ,  " +
                        "UNIQUE (`name`),  " +
                        "UNIQUE (`worldData_id`)" +
                        ")"
        );
    }

    private void createTables() throws SQLException {
        createWorldTable();
        createWorldDataTable();
        for (
                TableName tableName :
                List.of(TableName.obstacles, TableName.pits, TableName.mines)
        ) {
            createObjectTable(tableName);
        }
    }

    private int getLastTableIndex(TableName tableName) throws SQLException {
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

    private void saveWorldName() throws SQLException {
        int worldDataID = getLastTableIndex(TableName.worldData);

        Statement statement = dbConnection.createStatement();

        String sqlStatement;

        if (worldName == null) {
            sqlStatement = MessageFormat.format(
                    "INSERT INTO world(worldID, name, worldData_id) " +
                            "VALUES({0}, {0}, {1})",
                    getLastTableIndex(TableName.world) + 1,
                    worldDataID
            );
        } else {
            sqlStatement = String.format(
                    "INSERT INTO world(name, worldData_id) " +
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

    private void saveWorldData(WorldDataDO worldData)
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

    private void saveObjects(TableName tableName, List<WorldObjectDO> objects)
            throws SQLException {
        Statement statement = dbConnection.createStatement();
        for (WorldObjectDO object : objects) {
            statement.executeUpdate(
                    MessageFormat.format(
                            "INSERT INTO {0}" +
                                    "(width, height, x, y, world_id) " +
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

    private void saveObstacles(List<WorldObjectDO> obstacles)
            throws SQLException {
        saveObjects(TableName.obstacles, obstacles);
    }

    private void savePits(List<WorldObjectDO> pits)
            throws SQLException {
        saveObjects(TableName.pits, pits);
    }

    private void saveMines(List<WorldObjectDO> mines)
            throws SQLException {
        saveObjects(TableName.mines, mines);
    }

    @Override
    public void saveWorld(String worldName, WorldDO world)
            throws SQLException {
        this.worldName = worldName;
        createTables();
        saveWorldData(world.getWorldData());
        saveWorldName();

        worldID = getLastTableIndex(TableName.world);

        saveObstacles(world.getObstacles());
        savePits(world.getPits());
        saveMines(world.getMines());
    }

    private int getWorldID() throws SQLException {
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

    private int getWorldDataID() throws SQLException {
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

        return resultSet.getInt("worldData_id");
    }

    private ResultSet getResultsLinkedToWorldID(TableName tableName)
            throws SQLException {
        Statement statement = dbConnection.createStatement();
        return statement.executeQuery(
                MessageFormat.format(
                        "SELECT * FROM {0} WHERE world_id = {1}",
                        tableName,
                        worldID
                )
        );
    }

    private int[] getWorldDateArray(ResultSet resultSet) throws SQLException {
        int[] returnArray = new int[8];
        returnArray[0] = resultSet.getInt("width");
        returnArray[1] = resultSet.getInt("height");
        returnArray[2] = resultSet.getInt("visibility");
        returnArray[3] = resultSet.getInt("repairTime");
        returnArray[4] = resultSet.getInt("reloadTime");
        returnArray[5] = resultSet.getInt("mineTime");
        returnArray[6] = resultSet.getInt("maxShield");
        returnArray[7] = resultSet.getInt("maxShots");
        return returnArray;
    }

    private WorldDataDO getWorldData()
            throws SQLException {
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet =
                statement.executeQuery(
                        MessageFormat.format(
                                "SELECT * FROM worldData WHERE " +
                                        "worldDataID = {0}",
                                getWorldDataID()
                        )
                );
        if (resultSet.isClosed()) return new WorldDataDO();
        return new WorldDataDO(getWorldDateArray(resultSet));
    }

    private List<WorldObjectDO> getWorldObjects(TableName tableName)
            throws SQLException {
        ArrayList<WorldObjectDO> returnList = new ArrayList<>();

        ResultSet resultSet =
                getResultsLinkedToWorldID(tableName);

        while (!resultSet.isClosed() && resultSet.next()) {
            returnList.add(
                    new WorldObjectDO(
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
    public WorldDO restoreWorld(String worldName) throws SQLException {
        setWorldNameAndID(worldName);

        return new WorldDO(
                worldName,
                getWorldData(),
                getWorldObjects(TableName.obstacles),
                getWorldObjects(TableName.pits),
                getWorldObjects(TableName.mines)
        );
    }

    private void deleteRowUsingWorldID(TableName tableName)
            throws SQLException {
        dbConnection.createStatement().executeUpdate(
                String.format(
                        "DELETE FROM %s WHERE world_id = %d",
                        tableName,
                        worldID
                )
        );
    }

    private void deleteWorldDataRow() throws SQLException {
        dbConnection.createStatement().executeUpdate(
                String.format(
                        "DELETE FROM worldData WHERE worldDataID = %d",
                        getWorldDataID()
                )
        );
    }

    private void deleteWorldRow() throws  SQLException {
        dbConnection.createStatement().executeUpdate(
                String.format(
                        "DELETE FROM world WHERE worldID = %d",
                        worldID
                )
        );
    }

    @Override
    public void deleteWorld(String worldName) throws SQLException {
        setWorldNameAndID(worldName);

        deleteWorldDataRow();
        deleteWorldRow();

        for (
                TableName tableName :
                List.of(TableName.pits, TableName.obstacles, TableName.mines)
        ) {
            deleteRowUsingWorldID(tableName);
        }
    }

    private void setWorldNameAndID(String worldName) throws SQLException {
        this.worldName = worldName;
        this.worldID = getWorldID();
    }
}
