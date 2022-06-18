package za.co.wethinkcode.robotworlds.robot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Direction;
import za.co.wethinkcode.robotworlds.world.World;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TankTest {

    @Test
    void executeTankRobot() throws FileNotFoundException {
        ArrayList<Robot> robots = new ArrayList<>();
        Tank tank = new Tank(new World(robots),"hal","tank");
        assertEquals(5,tank.shields);
        assertEquals(3,tank.shots);
        assertEquals(5,tank.maxShields);
        assertEquals(3,tank.maxShots);
        assertEquals(Direction.NORTH,tank.getCurrentDirection());
        assertEquals(2,tank.shotDistance);


    }

}
