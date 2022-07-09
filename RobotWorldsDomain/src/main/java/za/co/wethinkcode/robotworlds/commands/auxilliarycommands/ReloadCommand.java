package za.co.wethinkcode.robotworlds.commands.auxilliarycommands;

import za.co.wethinkcode.robotworlds.response.JsonResponse;

public class ReloadCommand extends AuxiliaryCommand {
    public ReloadCommand(String robotName) {
        super(robotName, "reload");
    }

    @Override
    public JsonResponse execute() {
        return null;
    }
}
