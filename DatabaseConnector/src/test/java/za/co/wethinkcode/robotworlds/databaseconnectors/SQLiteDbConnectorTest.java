package za.co.wethinkcode.robotworlds.databaseconnectors;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.dbobjects.WorldDataDbObject;
import za.co.wethinkcode.robotworlds.dbobjects.WorldDbObject;
import za.co.wethinkcode.robotworlds.dbobjects.WorldObjectDbData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SQLiteDbConnectorTest {

    WorldDbObject createWorld() {
        WorldDataDbObject worldData =
                new WorldDataDbObject(
                        10,
                        10,
                        10,
                        3,
                        3,
                        3,
                        5,
                        5
        );

        WorldObjectDbData worldObject1 =
                new WorldObjectDbData(1, 1, 0, 1);

        WorldObjectDbData worldObject2 =
                new WorldObjectDbData(3, 3, 4, -3);

        WorldObjectDbData worldObject3 =
                new WorldObjectDbData(5, 5, -9, -9);

        List<WorldObjectDbData> worldObjects =
                List.of(worldObject1, worldObject2, worldObject3);

        return new WorldDbObject(
                worldData,
                worldObjects,
                worldObjects,
                worldObjects
        );
    }

    @Test
    void createTables() throws SQLException {
        SQLiteDbConnector dbConnector = new SQLiteDbConnector(":memory:");
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
        SQLiteDbConnector dbConnector = new SQLiteDbConnector(":memory:");
        Connection connection = dbConnector.getConnection();

        dbConnector.saveWorld("world", createWorld());

        assertFalse(checkTableEmpty("world", connection));
        assertFalse(checkTableEmpty("worldData", connection));
        assertFalse(checkTableEmpty("mines", connection));
        assertFalse(checkTableEmpty("obstacles", connection));
        assertFalse(checkTableEmpty("pits", connection));
    }

    @Test
    void restoreWorld() throws SQLException {
        SQLiteDbConnector dbConnector = new SQLiteDbConnector(":memory:");
        Connection connection = dbConnector.getConnection();

        WorldDbObject world = createWorld();
        dbConnector.saveWorld("world", world);

        WorldDbObject worldFromDb = dbConnector.restoreWorld("world");

        assertEquals(world, worldFromDb);
    }
}