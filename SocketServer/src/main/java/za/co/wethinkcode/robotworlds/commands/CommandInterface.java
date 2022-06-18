package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.ClientHandler;
import za.co.wethinkcode.robotworlds.world.World;

public interface CommandInterface {

    String execute(World world, String[] arguments, ClientHandler clientHandler);
}
