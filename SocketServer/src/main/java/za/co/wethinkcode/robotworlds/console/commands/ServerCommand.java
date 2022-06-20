package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;
import za.co.wethinkcode.robotworlds.world.World;



import java.util.ArrayList;

public abstract class ServerCommand {

    public abstract void execute(ArrayList<ClientHandler> users, ArrayList<Robot> robots, World world);

    public static ServerCommand create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");
        switch (args[0]) {
            case "quit":
                return new Quit();
            case "robots":
                return new Robots();
            case "dump":
                return new Dump();
            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }
}
