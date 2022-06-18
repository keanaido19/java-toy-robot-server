package za.co.wethinkcode.robotworlds.robot;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.world.enums.Direction;
import za.co.wethinkcode.robotworlds.world.World;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SniperTest {
    @Test
    void executeSniperRobot() throws FileNotFoundException {
        ArrayList<Robot> robots = new ArrayList<>();
        Sniper sniper = new Sniper(new World(robots),"hal","sniper");
        assertEquals(1,sniper.shields);
        assertEquals(2,sniper.shots);
        assertEquals(1,sniper.maxShields);
        assertEquals(2,sniper.maxShots);
        assertEquals(Direction.NORTH,sniper.getCurrentDirection());
        assertEquals(5,sniper.shotDistance);


    }

}
