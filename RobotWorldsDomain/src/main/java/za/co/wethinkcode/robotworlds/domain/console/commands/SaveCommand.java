package za.co.wethinkcode.robotworlds.domain.console.commands;

import za.co.wethinkcode.robotworlds.domain.Play;
import za.co.wethinkcode.robotworlds.database.connectors.DbConnector;
import za.co.wethinkcode.robotworlds.database.connectors.ORMLiteDbConnector;
import za.co.wethinkcode.robotworlds.database.objects.WorldDO;
import za.co.wethinkcode.robotworlds.domain.world.DbConverter;

import java.sql.SQLException;

public class SaveCommand extends ConsoleCommand {
    private final String worldName;

    public SaveCommand(String worldName) {
        this.worldName = worldName;
    }

    @Override
    public boolean execute() {
        WorldDO worldDO =
                DbConverter.getWorldDO(Play.getWorld());
        try {
            DbConnector databaseConnector = new ORMLiteDbConnector();
            databaseConnector.saveWorld(worldName, worldDO);

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
