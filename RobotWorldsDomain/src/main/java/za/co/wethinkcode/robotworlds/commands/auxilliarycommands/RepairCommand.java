package za.co.wethinkcode.robotworlds.commands.auxilliarycommands;

import com.fasterxml.jackson.databind.JsonNode;

public class RepairCommand extends AuxiliaryCommand {
    public RepairCommand(String robotName) {
        super(robotName, "repair");
    }

    @Override
    public JsonNode execute() {
        return null;
    }
}
