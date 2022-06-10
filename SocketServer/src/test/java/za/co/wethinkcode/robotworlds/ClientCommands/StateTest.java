package za.co.wethinkcode.robotworlds.ClientCommands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Robot.Normal;
import za.co.wethinkcode.robotworlds.Robot.Robot;
import za.co.wethinkcode.robotworlds.World.World;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StateTest {

    @Test
    void executeTest() throws IOException {
        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        State testState = new State("Bob");
        String[] args = {};
        assertEquals("{" +
                "\"position\":[" +
                "0," +
                "0" +
                "]," +
                "\"direction\":\"NORTH\"," +
                "\"shields\":3," +
                "\"shots\":3," +
                "\"status\":\"normal\"" +
                "}", testState.execute(worldTest, args, null));
    }

}