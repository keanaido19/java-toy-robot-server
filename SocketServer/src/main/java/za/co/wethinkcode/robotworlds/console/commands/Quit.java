package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;
import java.util.ArrayList;

public class Quit extends ServerCommand{

    @Override
    public void execute(ArrayList<ClientHandler> users, ArrayList<Robot> robots, World world){
        System.out.println("System shutting down");
        System.exit(0);
    }
}
