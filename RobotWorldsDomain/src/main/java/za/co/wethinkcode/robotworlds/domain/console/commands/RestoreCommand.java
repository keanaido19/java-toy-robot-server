package za.co.wethinkcode.robotworlds.domain.console.commands;

import za.co.wethinkcode.robotworlds.domain.Play;
import za.co.wethinkcode.robotworlds.database.connectors.DbConnector;
import za.co.wethinkcode.robotworlds.database.connectors.ORMLiteDbConnector;
import za.co.wethinkcode.robotworlds.database.objects.WorldDO;
import za.co.wethinkcode.robotworlds.domain.world.DbConverter;

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
            WorldDO worldDO =
                    databaseConnector.restoreWorld(worldName);

            Play.setWorld(DbConverter.getWorld(worldDO));

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
