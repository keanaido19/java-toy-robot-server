package za.co.wethinkcode.robotworlds.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.clienthandler.commands.Launch;
import za.co.wethinkcode.robotworlds.world.objects.robots.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.io.FileNotFoundException;
import java.util.ArrayList;

class LaunchTest {

        @Test
        void executeTest() throws FileNotFoundException {
            ArrayList<Robot> robots = new ArrayList<>();
            World worldTest = new World(robots);
            Launch launchTest = new Launch("normal", "bob");
            String[] args = {};
            assertEquals("{" +
                    "\"result\":\"OK\"," +
                    "\"data\":{" +
                    "\"position\":[" +
                    "0," +
                    "0" +
                    "]," +
                    "\"visibility\":0," +
                    "\"reload\":0," +
                    "\"repair\":0," +
                    "\"shields\":0" +
                    "}," +
                    "\"state\":{" +
                    "\"position\":[" +
                    "0," +
                    "0" +
                    "]," +
                    "\"direction\":\"NORTH\"," +
                    "\"shields\":3," +
                    "\"shots\":3," +
                    "\"status\":\"normal\"" +
                    "}" +
                    "}", launchTest.execute(worldTest,args, null));
        }
    }