package za.co.wethinkcode.robotworlds.ServerCommands;

import za.co.wethinkcode.robotworlds.ClientHandler;
import za.co.wethinkcode.robotworlds.Robot.Robot;
import za.co.wethinkcode.robotworlds.World.World;
import java.util.ArrayList;

public class Quit extends ServerCommand{

    @Override
    public void execute(ArrayList<ClientHandler> users, ArrayList<Robot> robots, World world){
        System.out.println("System shutting down");
        System.exit(0);
    }
}
