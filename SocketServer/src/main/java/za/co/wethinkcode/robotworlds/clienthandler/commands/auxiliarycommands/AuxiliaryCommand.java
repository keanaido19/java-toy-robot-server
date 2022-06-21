package za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.clienthandler.commands.Command;
import za.co.wethinkcode.robotworlds.response.ServerResponse;

import java.util.ArrayList;

public abstract class AuxiliaryCommand extends Command {
    public AuxiliaryCommand(String robotName, String command) {
        super(robotName, command, new ArrayList<>() {});
    }

    @Override
    public abstract ServerResponse execute(ClientHandler clientHandler);
}
