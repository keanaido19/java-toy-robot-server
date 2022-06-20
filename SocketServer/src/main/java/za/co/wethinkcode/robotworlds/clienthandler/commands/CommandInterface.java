package za.co.wethinkcode.robotworlds.clienthandler.commands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.World;

public interface CommandInterface {

    ServerResponse execute(World world, String[] arguments, ClientHandler clientHandler);
}
