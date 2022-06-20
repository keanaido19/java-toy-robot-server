package za.co.wethinkcode.robotworlds.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.clienthandler.commands.Quit;
import za.co.wethinkcode.robotworlds.world.objects.robots.robot.Normal;
import za.co.wethinkcode.robotworlds.world.objects.robots.robot.Robot;
import za.co.wethinkcode.robotworlds.world.objects.robots.obstacles.SquareObstacle;
import za.co.wethinkcode.robotworlds.world.World;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QuitTest {


    @Test
    void QuitTest() throws FileNotFoundException {
        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        worldTest.setVisibility(7);
        SquareObstacle[] obstacles = {new SquareObstacle(1,3), new SquareObstacle(2,1)};
        worldTest.setObstacles(obstacles);
        Robot test = new Normal(worldTest, "Bob", "normal");
        test.setCurrentPosition(new Position(1,1));
        robots.add(test);
        Robot test2 = new Normal(worldTest, "Bill", "normal");
        robots.add(test2);
        test2.setCurrentPosition(new Position(1,2));
        Robot test3 = new Normal(worldTest,"Tim", "normal");
        test3.setCurrentPosition(new Position(1, -2));
        robots.add(test3);
        Quit quit = new Quit("Bill");
        String[] args = {};
        quit.execute(worldTest,args, null);
        assertEquals(worldTest.getRobots().size(), 2);
    }

}