package za.co.wethinkcode.robotworlds.commands.auxilliarycommands;

import za.co.wethinkcode.robotworlds.response.JsonResponse;

public class LookCommand extends AuxiliaryCommand {
    public LookCommand(String robotName) {
        super(robotName, "look");
    }

    @Override
    public JsonResponse execute() {
        return null;
    }
}
