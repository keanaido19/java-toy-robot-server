package za.co.wethinkcode.robotworlds.commands.auxilliarycommands;

import com.fasterxml.jackson.databind.JsonNode;

public class StateCommand extends AuxiliaryCommand {

    public StateCommand(String robotName) {
        super(robotName, "state");
    }

    @Override
    public JsonNode execute() {
        return null;
    }
}
