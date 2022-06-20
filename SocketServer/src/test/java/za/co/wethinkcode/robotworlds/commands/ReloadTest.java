package za.co.wethinkcode.robotworlds.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.clienthandler.commands.Fire;
import za.co.wethinkcode.robotworlds.clienthandler.commands.Reload;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.io.IOException;
import java.util.ArrayList;

class ReloadTest {


    @Test
    void executeTestReload() throws IOException {
        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Fire fireTest = new Fire("Bob");
        String[] args = {};
        fireTest.execute(worldTest, args, null);
        Reload reloadTest = new Reload("Bob");
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"data\": {\n" +
                "    \"message\": \"Done\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"position\": [\n" +
                "      0,\n" +
                "      0\n" +
                "    ],\n" +
                "    \"direction\": \"NORTH\",\n" +
                "    \"shields\": 3,\n" +
                "    \"shots\": 3,\n" +
                "    \"status\": \"RELOAD\"\n" +
                "  }\n" +
                "}", reloadTest.execute(worldTest, args, null));
    }
}