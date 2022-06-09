//package za.co.wethinkcode.robotworlds.ClientCommands;
//
//import org.junit.jupiter.api.Test;
//import za.co.wethinkcode.robotworlds.Direction;
//import za.co.wethinkcode.robotworlds.Position;
//import za.co.wethinkcode.robotworlds.Robot.Normal;
//import za.co.wethinkcode.robotworlds.Robot.Robot;
//import za.co.wethinkcode.robotworlds.World.SquareObstacle;
//import za.co.wethinkcode.robotworlds.World.World;
//
//import java.io.FileNotFoundException;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class LookTest {
//    @Test
//    void lookTest() throws FileNotFoundException {
//        ArrayList<Robot> robots = new ArrayList<>();
//        World worldTest = new World(robots);
//        SquareObstacle[] obstacles = {new SquareObstacle(-1,2), new SquareObstacle(-3,-4)};
//        worldTest.setObstacles(obstacles);
//        Robot test = new Normal(worldTest, "Bob", "normal");
//        test.setCurrentPosition(new Position(1,1));
//        robots.add(test);
//        Look look = new Look("Bob");
//        String[] args = {};
//        assertEquals("{\n" +
//                "  \"result\": \"ok\",\n" +
//                "  \"data\": {\n" +
//                "    \"objects\": [\n" +
//                "      {\n" +
//                "        \"direction\": \"NORTH\",\n" +
//                "        \"objectType\": \"OBSTACLE\",\n" +
//                "        \"steps\": 1\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"direction\": \"EAST\",\n" +
//                "        \"objectType\": \"EDGE\",\n" +
//                "        \"steps\": 4\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  \"state\": {\n" +
//                "    \"position\": [\n" +
//                "      1,\n" +
//                "      1\n" +
//                "    ],\n" +
//                "    \"direction\": \"NORTH\",\n" +
//                "    \"shields\": 3,\n" +
//                "    \"shots\": 3,\n" +
//                "    \"status\": \"normal\"\n" +
//                "  }\n" +
//                "}",look.execute(worldTest, args, null));
//    }
//
//    @Test
//    void lookTestThrough() throws FileNotFoundException {
//        ArrayList<Robot> robots = new ArrayList<>();
//        World worldTest = new World(robots);
//        worldTest.setVISIBILITY(3);
//        SquareObstacle[] obstacles = {new SquareObstacle(1,3), new SquareObstacle(2,1)};
//        worldTest.setObstacles(obstacles);
//        Robot test = new Normal(worldTest, "Bob", "normal");
//        test.setCurrentPosition(new Position(1,1));
//        robots.add(test);
//        Robot test2 = new Normal(worldTest, "Bill", "normal");
//        robots.add(test2);
//        test2.setCurrentPosition(new Position(1,2));
//        Look look = new Look("Bob");
//        String[] args = {};
//        assertEquals("{\n" +
//                "  \"result\": \"ok\",\n" +
//                "  \"data\": {\n" +
//                "    \"objects\": [\n" +
//                "      {\n" +
//                "        \"direction\": \"NORTH\",\n" +
//                "        \"objectType\": \"ROBOT\",\n" +
//                "        \"steps\": 1\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"direction\": \"EAST\",\n" +
//                "        \"objectType\": \"OBSTACLE\",\n" +
//                "        \"steps\": 1\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  \"state\": {\n" +
//                "    \"position\": [\n" +
//                "      1,\n" +
//                "      1\n" +
//                "    ],\n" +
//                "    \"direction\": \"NORTH\",\n" +
//                "    \"shields\": 3,\n" +
//                "    \"shots\": 3,\n" +
//                "    \"status\": \"normal\"\n" +
//                "  }\n" +
//                "}",look.execute(worldTest, args, null));
//    }
//
//    @Test
//    void lookTestAllAround() throws FileNotFoundException {
//        ArrayList<Robot> robots = new ArrayList<>();
//        World worldTest = new World(robots);
//        worldTest.setVISIBILITY(7);
//        SquareObstacle[] obstacles = {new SquareObstacle(1,3), new SquareObstacle(2,1)};
//        worldTest.setObstacles(obstacles);
//        Robot test = new Normal(worldTest, "Bob", "normal");
//        test.setCurrentPosition(new Position(1,1));
//        robots.add(test);
//        Robot test2 = new Normal(worldTest, "Bill", "normal");
//        robots.add(test2);
//        test2.setCurrentPosition(new Position(1,2));
//        Robot test3 = new Normal(worldTest,"Tim", "normal");
//        test3.setCurrentPosition(new Position(1, -2));
//        robots.add(test3);
//        Look look = new Look("Bob");
//        String[] args = {};
//        assertEquals("{\n" +
//                "  \"result\": \"ok\",\n" +
//                "  \"data\": {\n" +
//                "    \"objects\": [\n" +
//                "      {\n" +
//                "        \"direction\": \"NORTH\",\n" +
//                "        \"objectType\": \"ROBOT\",\n" +
//                "        \"steps\": 1\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"direction\": \"EAST\",\n" +
//                "        \"objectType\": \"OBSTACLE\",\n" +
//                "        \"steps\": 1\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"direction\": \"SOUTH\",\n" +
//                "        \"objectType\": \"ROBOT\",\n" +
//                "        \"steps\": 3\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"direction\": \"WEST\",\n" +
//                "        \"objectType\": \"EDGE\",\n" +
//                "        \"steps\": 6\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  \"state\": {\n" +
//                "    \"position\": [\n" +
//                "      1,\n" +
//                "      1\n" +
//                "    ],\n" +
//                "    \"direction\": \"NORTH\",\n" +
//                "    \"shields\": 3,\n" +
//                "    \"shots\": 3,\n" +
//                "    \"status\": \"normal\"\n" +
//                "  }\n" +
//                "}",look.execute(worldTest, args, null));
//    }
//
//    @Test
//    void lookTestForEdges() throws FileNotFoundException {
//        ArrayList<Robot> robots = new ArrayList<>();
//        World worldTest = new World(robots);
//        worldTest.setVISIBILITY(7);
//        SquareObstacle[] obstacles = {new SquareObstacle(1,3), new SquareObstacle(2,1)};
//        worldTest.setObstacles(obstacles);
//        Robot test = new Normal(worldTest, "Bob", "normal");
//        test.setCurrentPosition(new Position(0,0));
//        robots.add(test);
//        Look look = new Look("Bob");
//        String[] args = {};
//        assertEquals("{\n" +
//                "  \"result\": \"ok\",\n" +
//                "  \"data\": {\n" +
//                "    \"objects\": [\n" +
//                "      {\n" +
//                "        \"direction\": \"NORTH\",\n" +
//                "        \"objectType\": \"EDGE\",\n" +
//                "        \"steps\": 5\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"direction\": \"EAST\",\n" +
//                "        \"objectType\": \"EDGE\",\n" +
//                "        \"steps\": 5\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"direction\": \"SOUTH\",\n" +
//                "        \"objectType\": \"EDGE\",\n" +
//                "        \"steps\": 5\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"direction\": \"WEST\",\n" +
//                "        \"objectType\": \"EDGE\",\n" +
//                "        \"steps\": 5\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  \"state\": {\n" +
//                "    \"position\": [\n" +
//                "      0,\n" +
//                "      0\n" +
//                "    ],\n" +
//                "    \"direction\": \"NORTH\",\n" +
//                "    \"shields\": 3,\n" +
//                "    \"shots\": 3,\n" +
//                "    \"status\": \"normal\"\n" +
//                "  }\n" +
//                "}",look.execute(worldTest, args, null));
//    }
//
//    @Test
//    void lookTestAllAroundFacingEast() throws FileNotFoundException {
//        ArrayList<Robot> robots = new ArrayList<>();
//        World worldTest = new World(robots);
//        worldTest.setVISIBILITY(7);
//        SquareObstacle[] obstacles = {new SquareObstacle(1,3), new SquareObstacle(2,1)};
//        worldTest.setObstacles(obstacles);
//        Robot test = new Normal(worldTest, "Bob", "normal");
//        test.setCurrentPosition(new Position(1,1));
//        test.setCurrentDirection(Direction.EAST);
//        robots.add(test);
//        Robot test2 = new Normal(worldTest, "Bill", "normal");
//        robots.add(test2);
//        test2.setCurrentPosition(new Position(1,2));
//        Robot test3 = new Normal(worldTest,"Tim", "normal");
//        test3.setCurrentPosition(new Position(1, -2));
//        robots.add(test3);
//        Look look = new Look("Bob");
//        String[] args = {};
//        assertEquals("{\n" +
//                "  \"result\": \"ok\",\n" +
//                "  \"data\": {\n" +
//                "    \"objects\": [\n" +
//                "      {\n" +
//                "        \"direction\": \"NORTH\",\n" +
//                "        \"objectType\": \"OBSTACLE\",\n" +
//                "        \"steps\": 1\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"direction\": \"EAST\",\n" +
//                "        \"objectType\": \"ROBOT\",\n" +
//                "        \"steps\": 3\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"direction\": \"SOUTH\",\n" +
//                "        \"objectType\": \"EDGE\",\n" +
//                "        \"steps\": 6\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"direction\": \"WEST\",\n" +
//                "        \"objectType\": \"ROBOT\",\n" +
//                "        \"steps\": 1\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  \"state\": {\n" +
//                "    \"position\": [\n" +
//                "      1,\n" +
//                "      1\n" +
//                "    ],\n" +
//                "    \"direction\": \"EAST\",\n" +
//                "    \"shields\": 3,\n" +
//                "    \"shots\": 3,\n" +
//                "    \"status\": \"normal\"\n" +
//                "  }\n" +
//                "}",look.execute(worldTest, args, null));
//    }
//}