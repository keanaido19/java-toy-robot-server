package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.Play;
import za.co.wethinkcode.robotworlds.databaseconnectors.DbConnector;
import za.co.wethinkcode.robotworlds.databaseconnectors.ORMLiteDbConnector;
import za.co.wethinkcode.robotworlds.dbobjects.WorldDbObject;
import za.co.wethinkcode.robotworlds.world.WorldDbObjectConverter;

import java.sql.SQLException;

public class SaveCommand extends ConsoleCommand {
    private final String worldName;

    public SaveCommand(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public boolean execute() {
        WorldDbObject worldDbObject =
                WorldDbObjectConverter.getWorldDbObject(Play.getWorld());
        try {
            DbConnector databaseConnector = new ORMLiteDbConnector();
            databaseConnector.saveWorld(worldName, worldDbObject);

            System.out.printf(
                    "Saved world as \"%s\" in database.%n",
                    worldName
            );

        } catch (SQLException e) {
            System.out.printf(
                    "Unable to save World (\"%s\"), world name already in " +
                            "use.%n",
                    worldName
            );
        }

        return true;
    }
}
