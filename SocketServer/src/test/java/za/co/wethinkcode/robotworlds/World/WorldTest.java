package za.co.wethinkcode.robotworlds.World;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Robot.Robot;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    @Test
    void getRobots() throws FileNotFoundException {
        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        assertEquals(0, worldTest.getRobots().size());
    }

    @Test
    void testObstacles() throws FileNotFoundException{
        SquareObstacle[] obstacles = {new SquareObstacle(0,2)};
        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        worldTest.setObstacles(obstacles);
        assertEquals(1, worldTest.getOBSTACLES().length);
    }

}