package za.co.wethinkcode.robotworlds.database.connectors;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.database.connectors.SQLiteDbConnector;
import za.co.wethinkcode.robotworlds.database.objects.WorldDataDO;
import za.co.wethinkcode.robotworlds.database.objects.WorldDO;
import za.co.wethinkcode.robotworlds.database.objects.WorldObjectDO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SQLiteDbConnectorTest {
    private final List<String> tableNames =
            List.of("world", "worldData", "mines", "obstacles", "pits");
    SQLiteDbConnector dbConnector = new SQLiteDbConnector(":memory:");

    SQLiteDbConnectorTest() throws SQLException {}

    private static int[] getWorldData() {
        int[] returnArray = new int[8];
        returnArray[0] = 10;
        returnArray[1] = 10;
        returnArray[2] = 10;
        returnArray[3] = 3;
        returnArray[4] = 3;
        returnArray[5] = 3;
        returnArray[6] = 5;
        returnArray[7] = 5;
        return returnArray;
    }

    WorldDO createWorld() {
        WorldDataDO worldData = new WorldDataDO(getWorldData());

        WorldObjectDO worldObject1 =
                new WorldObjectDO(1, 1, 0, 1);

        WorldObjectDO worldObject2 =
                new WorldObjectDO(3, 3, 4, -3);

        WorldObjectDO worldObject3 =
                new WorldObjectDO(5, 5, -9, -9);

        List<WorldObjectDO> worldObjects =
                List.of(worldObject1, worldObject2, worldObject3);

        return new WorldDO(
                worldData,
                worldObjects,
                worldObjects,
                worldObjects
        );
    }

    @Test
    void createTables() throws SQLException {
        Connection connection = dbConnector.getConnection();

        dbConnector.saveWorld("world", createWorld());

        ResultSet resultSet =
                connection.createStatement().executeQuery(
                        "SELECT * FROM sqlite_master WHERE type='table'"
                );

        List<String> tableNames =
                List.of("world", "worldData", "obstacles", "mines", "pits",
                        "sqlite_sequence");

        while (resultSet.next()) {
            assertTrue(
                    tableNames.contains(resultSet.getString("name"))
            );
        }
    }

    boolean checkTableEmpty(String tableName, Connection connection)
            throws SQLException {
        ResultSet resultSet =
                connection.createStatement().executeQuery(
                        String.format("SELECT * FROM %s", tableName)
                );

        return resultSet.isClosed();
    }

    @Test
    void saveWorld() throws SQLException {
        Connection connection = dbConnector.getConnection();

        dbConnector.saveWorld("world", createWorld());

        for (String tableName : tableNames) {
            assertFalse(checkTableEmpty(tableName, connection));
        }
    }

    @Test
    void restoreWorld() throws SQLException {
        Connection connection = dbConnector.getConnection();

        WorldDO world = createWorld();
        dbConnector.saveWorld("world", world);

        WorldDO worldFromDb = dbConnector.restoreWorld("world");

        assertEquals(world, worldFromDb);
    }

    @Test
    void deleteWorld() throws SQLException {
        Connection connection = dbConnector.getConnection();

        saveWorld();

        dbConnector.deleteWorld("world");

        for (String tableName : tableNames) {
            assertTrue(checkTableEmpty(tableName, connection));
        }
    }
}