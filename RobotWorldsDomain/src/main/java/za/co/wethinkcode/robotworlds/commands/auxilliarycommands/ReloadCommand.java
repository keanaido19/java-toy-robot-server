package za.co.wethinkcode.robotworlds.commands.auxilliarycommands;

import com.fasterxml.jackson.databind.JsonNode;

public class ReloadCommand extends AuxiliaryCommand {
    public ReloadCommand(String robotName) {
        super(robotName, "reload");
    }

    @Override
    public JsonNode execute() {
        return null;
    }
}
