package za.co.wethinkcode.robotworlds.commands.auxilliarycommands;

import com.fasterxml.jackson.databind.JsonNode;

public class LookCommand extends AuxiliaryCommand {
    public LookCommand(String robotName) {
        super(robotName, "look");
    }

    @Override
    public JsonNode execute() {
        return null;
    }
}
