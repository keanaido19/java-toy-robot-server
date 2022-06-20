package za.co.wethinkcode.robotworlds.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.clienthandler.commands.State;
import za.co.wethinkcode.robotworlds.world.objects.robots.Normal;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.io.IOException;
import java.util.ArrayList;

class StateTest {

    @Test
    void executeTest() throws IOException {
        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        State testState = new State("Bob");
        String[] args = {};
        assertEquals("{\"result\":\"OK\",\"data\":" +
                "{\"visibility\":5,\"position\":[0,0],\"objects\":[]}," +
                "\"state\":{\"position\":[0,0],\"direction\":\"NORTH\"," +
                "\"shields\":3,\"shots\":3,\"status\":\"normal\"}}",
                testState.execute(worldTest, args, null)
        );
    }

}