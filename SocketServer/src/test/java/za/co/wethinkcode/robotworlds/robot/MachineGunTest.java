package za.co.wethinkcode.robotworlds.robot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.world.enums.Direction;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MachineGunTest {
    @Test
    void executeMachineGunRobot() throws FileNotFoundException {
        ArrayList<Robot> robots = new ArrayList<>();
        MachineGun machineGun = new MachineGun(new World(robots),"hal","machinegun");
        assertEquals(2,machineGun.shields);
        assertEquals(6,machineGun.shots);
        assertEquals(2,machineGun.maxShields);
        assertEquals(6,machineGun.maxShots);
        assertEquals(Direction.NORTH,machineGun.getCurrentDirection());
        assertEquals(2,machineGun.shotDistance);


    }

}
