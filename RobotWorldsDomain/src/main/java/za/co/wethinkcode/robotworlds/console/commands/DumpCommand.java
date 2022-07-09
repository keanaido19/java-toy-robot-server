package za.co.wethinkcode.robotworlds.console.commands;

import za.co.wethinkcode.robotworlds.Play;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.List;

public class DumpCommand extends ConsoleCommand {
    @Override
    public boolean execute() {
        World world = Play.getWorld();

        List<Obstacle> obstacles = world.getObstacles();
        List<Robot> robots = world.getRobots();

        if (obstacles.isEmpty() && robots.isEmpty()) {
            System.out.println("There are no objects in the world.");
            return true;
        }
        if (!obstacles.isEmpty()) {
            System.out.println("There are some obstacles:");
            for (Obstacle obstacle : obstacles) {
                System.out.println(obstacle);
            }
        }

        if (!robots.isEmpty()) {
            System.out.println("There are some robots:");
            for (Robot robot : robots) {
                System.out.println(robot);
            }
        }

        return true;
    }
}
