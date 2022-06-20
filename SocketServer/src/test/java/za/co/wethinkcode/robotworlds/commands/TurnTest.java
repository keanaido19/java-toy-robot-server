package za.co.wethinkcode.robotworlds.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.clienthandler.commands.Turn;
import za.co.wethinkcode.robotworlds.world.objects.robots.robot.Normal;
import za.co.wethinkcode.robotworlds.world.objects.robots.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.io.IOException;
import java.util.ArrayList;

class TurnTest {

    @Test
    void executeTestTurnRight() throws IOException {

        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Turn turnTest = new Turn("Bob", "right");
        String[] args = {};
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"data\": {\n" +
                "    \"message\": \"Done\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"direction\": \"EAST\"\n" +
                "  }\n" +
                "}", turnTest.execute(worldTest, args, null));
    }

    @Test
    void executeTestTurnRightTwice() throws IOException {

        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Turn turnTest = new Turn("Bob", "right");
        String[] args = {};
        turnTest.execute(worldTest, args, null);
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"data\": {\n" +
                "    \"message\": \"Done\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"direction\": \"SOUTH\"\n" +
                "  }\n" +
                "}", turnTest.execute(worldTest, args, null));
    }

    @Test
    void executeTestTurnRightFourTimes() throws IOException {

        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Turn turnTest = new Turn("Bob", "right");
        String[] args = {};
        turnTest.execute(worldTest, args, null);
        turnTest.execute(worldTest, args, null);
        turnTest.execute(worldTest, args, null);
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"data\": {\n" +
                "    \"message\": \"Done\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"direction\": \"NORTH\"\n" +
                "  }\n" +
                "}", turnTest.execute(worldTest, args,null ));
    }

    @Test
    void executeTestTurnLeft() throws IOException {

        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Turn turnTest = new Turn("Bob", "left");
        String[] args = {};
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"data\": {\n" +
                "    \"message\": \"Done\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"direction\": \"WEST\"\n" +
                "  }\n" +
                "}", turnTest.execute(worldTest, args, null));
    }

    @Test
    void executeTestTurnLeftTwice() throws IOException {

        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Turn turnTest = new Turn("Bob", "left");
        String[] args = {};
        turnTest.execute(worldTest, args, null);
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"data\": {\n" +
                "    \"message\": \"Done\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"direction\": \"SOUTH\"\n" +
                "  }\n" +
                "}", turnTest.execute(worldTest, args, null));
    }

    @Test
    void executeTestTurnLeftFourTimes() throws IOException {

        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Turn turnTest = new Turn("Bob", "left");
        String[] args = {};
        turnTest.execute(worldTest,args, null);
        turnTest.execute(worldTest,args, null);
        turnTest.execute(worldTest,args, null);
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"data\": {\n" +
                "    \"message\": \"Done\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"direction\": \"NORTH\"\n" +
                "  }\n" +
                "}", turnTest.execute(worldTest, args, null));
    }
}