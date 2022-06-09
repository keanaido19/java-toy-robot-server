package za.co.wethinkcode.robotworlds.ClientCommands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.Robot.Normal;
import za.co.wethinkcode.robotworlds.Robot.Robot;
import za.co.wethinkcode.robotworlds.World.World;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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
                "}", turnTest.execute(worldTest, args));
    }

    @Test
    void executeTestTurnRightTwice() throws IOException {

        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Turn turnTest = new Turn("Bob", "right");
        String[] args = {};
        turnTest.execute(worldTest, args);
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"data\": {\n" +
                "    \"message\": \"Done\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"direction\": \"SOUTH\"\n" +
                "  }\n" +
                "}", turnTest.execute(worldTest, args));
    }

    @Test
    void executeTestTurnRightFourTimes() throws IOException {

        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Turn turnTest = new Turn("Bob", "right");
        String[] args = {};
        turnTest.execute(worldTest, args);
        turnTest.execute(worldTest, args);
        turnTest.execute(worldTest, args);
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"data\": {\n" +
                "    \"message\": \"Done\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"direction\": \"NORTH\"\n" +
                "  }\n" +
                "}", turnTest.execute(worldTest, args));
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
                "}", turnTest.execute(worldTest, args));
    }

    @Test
    void executeTestTurnLeftTwice() throws IOException {

        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Turn turnTest = new Turn("Bob", "left");
        String[] args = {};
        turnTest.execute(worldTest, args);
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"data\": {\n" +
                "    \"message\": \"Done\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"direction\": \"SOUTH\"\n" +
                "  }\n" +
                "}", turnTest.execute(worldTest, args));
    }

    @Test
    void executeTestTurnLeftFourTimes() throws IOException {

        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Turn turnTest = new Turn("Bob", "left");
        String[] args = {};
        turnTest.execute(worldTest,args);
        turnTest.execute(worldTest,args);
        turnTest.execute(worldTest,args);
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"data\": {\n" +
                "    \"message\": \"Done\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"direction\": \"NORTH\"\n" +
                "  }\n" +
                "}", turnTest.execute(worldTest, args));
    }
}