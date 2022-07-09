package za.co.wethinkcode.robotworlds.commands.auxilliarycommands;

import com.fasterxml.jackson.databind.JsonNode;

public class FireCommand extends AuxiliaryCommand {
    public FireCommand(String robotName) {
        super(robotName, "fire");
    }

    @Override
    public JsonNode execute() {
        return null;
    }
}
