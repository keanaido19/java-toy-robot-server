package za.co.wethinkcode.robotworlds.commands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.world.Position;
import za.co.wethinkcode.robotworlds.clienthandler.commands.Fire;
import za.co.wethinkcode.robotworlds.world.objects.robots.robot.Normal;
import za.co.wethinkcode.robotworlds.world.objects.robots.robot.Robot;
import za.co.wethinkcode.robotworlds.world.objects.robots.obstacles.SquareObstacle;
import za.co.wethinkcode.robotworlds.world.World;

import java.io.IOException;
import java.util.ArrayList;

class FireTest {


    @Test
    void executeFireTestHit() throws IOException {
        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Robot test2 = new Normal(worldTest, "Bill", "normal");
        robots.add(test2);
        test2.setCurrentPosition(new Position(0, 2));
        Fire testFire = new Fire("Bob");
        String[] args = {};
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"data\": {\n" +
                "    \"message\": \"Hit\",\n" +
                "    \"distance\": 2,\n" +
                "    \"robot\": \"Bill\",\n" +
                "    \"enemyState\": {\n" +
                "      \"position\": [\n" +
                "        0,\n" +
                "        2\n" +
                "      ],\n" +
                "      \"direction\": \"NORTH\",\n" +
                "      \"shields\": 2,\n" +
                "      \"shots\": 3,\n" +
                "      \"status\": \"normal\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"shots\": 2\n" +
                "  }\n" +
                "}", testFire.execute(worldTest, args, null));
    }

    @Test
    void executeFireTestMiss() throws IOException {
        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Fire testFire = new Fire("Bob");
        String[] args = {};
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"miss\": {\n" +
                "    \"message\": \"Miss\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"shots\": 2\n" +
                "  }\n" +
                "}", testFire.execute(worldTest, args, null));
    }

    @Test
    void executeFireTestNotEnoughRange() throws IOException {
        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Robot test2 = new Normal(worldTest, "Bill", "normal");
        robots.add(test2);
        test2.setCurrentPosition(new Position(0, 4));
        Fire testFire = new Fire("Bob");
        String[] args = {};
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"miss\": {\n" +
                "    \"message\": \"Miss\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"shots\": 2\n" +
                "  }\n" +
                "}", testFire.execute(worldTest, args, null));
    }

    @Test
    void executeFireTestHitMaxRange() throws IOException {
        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        Robot test2 = new Normal(worldTest, "Bill", "normal");
        robots.add(test2);
        test2.setCurrentPosition(new Position(0, 3));
        Fire testFire = new Fire("Bob");
        String[] args = {};
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"data\": {\n" +
                "    \"message\": \"Hit\",\n" +
                "    \"distance\": 3,\n" +
                "    \"robot\": \"Bill\",\n" +
                "    \"enemyState\": {\n" +
                "      \"position\": [\n" +
                "        0,\n" +
                "        3\n" +
                "      ],\n" +
                "      \"direction\": \"NORTH\",\n" +
                "      \"shields\": 2,\n" +
                "      \"shots\": 3,\n" +
                "      \"status\": \"normal\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"shots\": 2\n" +
                "  }\n" +
                "}", testFire.execute(worldTest, args, null));
    }

    @Test
    void executeFireTestMissObstacle() throws IOException {
        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        SquareObstacle[] obstacles = {new SquareObstacle(0,3), new SquareObstacle(2,1)};
        worldTest.setObstacles(obstacles);
        robots.add(test);
        test.setShotDistance(20);
        test.setCurrentPosition(new Position(0, -4));
        Robot test2 = new Normal(worldTest, "Bill", "normal");
        robots.add(test2);
        test2.setCurrentPosition(new Position(0, 9));
        Fire testFire = new Fire("Bob");
        String[] args = {};
        assertEquals("{\n" +
                "  \"result\": \"OK\",\n" +
                "  \"miss\": {\n" +
                "    \"message\": \"Miss\"\n" +
                "  },\n" +
                "  \"state\": {\n" +
                "    \"shots\": 2\n" +
                "  }\n" +
                "}", testFire.execute(worldTest, args, null));
    }
}