package za.co.wethinkcode.robotworlds.ClientCommands;

import za.co.wethinkcode.robotworlds.ClientHandler;
import za.co.wethinkcode.robotworlds.World.World;

public interface CommandInterface {

    String execute(World world, String[] arguments, ClientHandler clientHandler);
}
