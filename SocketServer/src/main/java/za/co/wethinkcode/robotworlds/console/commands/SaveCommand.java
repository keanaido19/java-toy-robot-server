package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.RobotServer;
import za.co.wethinkcode.robotworlds.databaseconnectors.DbConnector;
import za.co.wethinkcode.robotworlds.databaseconnectors.SQLiteDbConnector;
import za.co.wethinkcode.robotworlds.dbobjects.WorldDbObject;
import za.co.wethinkcode.robotworlds.world.WorldDbObjectConverter;

import java.sql.SQLException;

public class SaveCommand extends ServerCommand {
    private final String worldName;

    public SaveCommand(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public boolean execute(RobotServer server) {
        WorldDbObject worldDbObject =
                WorldDbObjectConverter.getWorldDbObject(server.getWorld());
        try {
            DbConnector databaseConnector =
                    new SQLiteDbConnector("world.sqlite");
            databaseConnector.saveWorld(worldName, worldDbObject);

            System.out.printf(
                    "Saved world as \"%s\" in database.%n",
                    worldName
            );

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return true;
    }
}
