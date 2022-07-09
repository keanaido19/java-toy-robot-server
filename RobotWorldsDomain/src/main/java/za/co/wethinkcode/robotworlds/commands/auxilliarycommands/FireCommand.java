package za.co.wethinkcode.robotworlds.commands.auxilliarycommands;

import za.co.wethinkcode.robotworlds.response.JsonResponse;

public class FireCommand extends AuxiliaryCommand {
    public FireCommand(String robotName) {
        super(robotName, "fire");
    }

    @Override
    public JsonResponse execute() {
        return null;
    }
}
