package za.co.wethinkcode.robotworlds.commands.auxilliarycommands;

import za.co.wethinkcode.robotworlds.response.JsonResponse;

public class StateCommand extends AuxiliaryCommand {

    public StateCommand(String robotName) {
        super(robotName, "state");
    }

    @Override
    public JsonResponse execute() {
        return null;
    }
}
