package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.Play;
import za.co.wethinkcode.robotworlds.databaseconnectors.DbConnector;
import za.co.wethinkcode.robotworlds.databaseconnectors.ORMLiteDbConnector;
import za.co.wethinkcode.robotworlds.dbobjects.WorldDbObject;
import za.co.wethinkcode.robotworlds.world.WorldDbObjectConverter;

import java.sql.SQLException;

public class RestoreCommand extends ConsoleCommand {
    private final String worldName;

    public RestoreCommand(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public boolean execute() {

        try {
            DbConnector databaseConnector = new ORMLiteDbConnector();
            WorldDbObject worldDbObject =
                    databaseConnector.restoreWorld(worldName);

            Play.setWorld(WorldDbObjectConverter.getWorld(worldDbObject));

            System.out.printf(
                    "World \"%s\" successfully restored.%n",
                    worldName
            );

        } catch (SQLException e) {
            System.out.printf(
                    "Unable to restore World (\"%s\"), world does not exist.%n",
                    worldName
            );
        }

        return true;
    }
}
