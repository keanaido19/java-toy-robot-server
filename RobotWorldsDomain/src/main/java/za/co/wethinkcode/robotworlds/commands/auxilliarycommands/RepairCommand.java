package za.co.wethinkcode.robotworlds.commands.auxilliarycommands;

import za.co.wethinkcode.robotworlds.response.JsonResponse;

public class RepairCommand extends AuxiliaryCommand {
    public RepairCommand(String robotName) {
        super(robotName, "repair");
    }

    @Override
    public JsonResponse execute() {
        return null;
    }
}
