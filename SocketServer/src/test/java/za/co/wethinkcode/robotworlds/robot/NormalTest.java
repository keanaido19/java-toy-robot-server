package za.co.wethinkcode.robotworlds.robot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.world.enums.Direction;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NormalTest {

    @Test
    void executeNormalRobot() throws FileNotFoundException {
        ArrayList<Robot> robots = new ArrayList<>();
        Normal normal = new Normal(new World(robots),"hal","normal");
        assertEquals(3,normal.shields);
        assertEquals(3,normal.shots);
        assertEquals(3,normal.maxShields);
        assertEquals(3,normal.maxShots);
        assertEquals(Direction.NORTH,normal.getCurrentDirection());
        assertEquals(3,normal.shotDistance);


    }

}
