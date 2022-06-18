package za.co.wethinkcode.robotworlds.commands;

import za.co.wethinkcode.robotworlds.ClientHandler;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.util.ConcurrentModificationException;

public class Quit extends ClientCommands{

    public Quit(String name) {
        super("quit" ,name);
    }

    @Override
    public String execute(World world, String[] arguments, ClientHandler clientHandler) {

        try {
            for (Robot robot : world.getRobots()) {
                if (robot.getRobotName().equals(getArgument())) {
                    ClientHandler.broadcastMessage(robot.getRobotName().substring(0, 1).toUpperCase()
                            + robot.getRobotName().substring(1)+ " has left the game.");
                    world.getRobots().remove(robot);
                }
            }
        }catch(ConcurrentModificationException ignored){
        }
        return "Bye bye";
    }
}