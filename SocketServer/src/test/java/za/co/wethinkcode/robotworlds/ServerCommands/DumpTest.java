package za.co.wethinkcode.robotworlds.ServerCommands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.robot.Normal;
import za.co.wethinkcode.robotworlds.robot.Robot;
import za.co.wethinkcode.robotworlds.console.commands.Dump;
import za.co.wethinkcode.robotworlds.world.SquareObstacle;
import za.co.wethinkcode.robotworlds.world.World;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DumpTest {


    private void simulateGame(String simulatedUserInput, String expectedLastLine) throws FileNotFoundException {                     //<1>
        InputStream simulatedInputStream = new ByteArrayInputStream(simulatedUserInput.getBytes());     //<2>
        System.setIn(simulatedInputStream);                                                             //<3>

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();                         //<4>
        System.setOut(new PrintStream(outputStreamCaptor));                                             //<5>

        ArrayList<Robot> robots = new ArrayList<>();
        World worldTest = new World(robots);
        Robot test = new Normal(worldTest, "Bob", "normal");
        robots.add(test);
        ArrayList<ClientHandler> testUserList = new ArrayList<>();
        SquareObstacle[] obstacles = {new SquareObstacle(0,3), new SquareObstacle(2,1)};
        worldTest.setObstacles(obstacles);
        Dump dump = new Dump();
        dump.execute(testUserList, robots, worldTest);


        String linesOutput = outputStreamCaptor.toString();
        assertEquals(expectedLastLine, linesOutput);                                                       //<12>
    }

    @Test
    void areRobots() throws FileNotFoundException {
            String simulatedUserInput = "";
            String expectedOutput =
                    "There are some obstacles\n" +
                            "- At position 0,3 (to 4,7)\n" +
                            "- At position 2,1 (to 6,5)\n" +
                            "ROBOTS:\n" +
                            "Robot : Bob\n" +
                            "Position [0,0] \n" +
                            "Direction [NORTH]\n \n";
            simulateGame(simulatedUserInput, expectedOutput);                                               //<3>
        }
    }