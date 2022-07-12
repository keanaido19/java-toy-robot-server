package za.co.wethinkcode.robotworlds.domain.console.commands;

import za.co.wethinkcode.robotworlds.domain.Play;
import za.co.wethinkcode.robotworlds.domain.world.objects.robots.Robot;

import java.util.List;

public class RobotsCommand extends ConsoleCommand {
    @Override
    public boolean execute() {
        List<Robot> robots = Play.getWorld().getRobots();

        if (robots.isEmpty()) {
            System.out.println("There are currently no robots in the world.");
        } else {
            System.out.println("There are some robots:");
            for (Robot robot: robots) {
                System.out.println(robot);
            }
        }
        return true;
    }
}
