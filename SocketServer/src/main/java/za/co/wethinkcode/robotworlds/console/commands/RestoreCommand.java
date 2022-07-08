package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.RobotServer;
import za.co.wethinkcode.robotworlds.databaseconnectors.DbConnector;
import za.co.wethinkcode.robotworlds.databaseconnectors.ORMLiteDbConnector;
import za.co.wethinkcode.robotworlds.databaseconnectors.SQLiteDbConnector;
import za.co.wethinkcode.robotworlds.dbobjects.WorldDbObject;
import za.co.wethinkcode.robotworlds.world.WorldDbObjectConverter;

import java.sql.SQLException;

public class RestoreCommand extends ServerCommand {
    private final String worldName;

    public RestoreCommand(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public boolean execute(RobotServer server) {

        try {
            DbConnector databaseConnector = new ORMLiteDbConnector();
            WorldDbObject worldDbObject =
                    databaseConnector.restoreWorld(worldName);

            server.setWorld(WorldDbObjectConverter.getWorld(worldDbObject));

            System.out.printf(
                    "World \"%s\" successfully restored.%n",
                    worldName
            );

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return true;
    }
}
