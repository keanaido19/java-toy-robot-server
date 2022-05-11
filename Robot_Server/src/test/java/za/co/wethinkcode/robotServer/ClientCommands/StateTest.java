package za.co.wethinkcode.robotServer.ClientCommands;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotServer.Robot.Normal;
import za.co.wethinkcode.robotServer.Robot.Robot;
import za.co.wethinkcode.robotServer.World.World;

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
        assertEquals("{\n" +
                "  \"position\": [\n" +
                "    0,\n" +
                "    0\n" +
                "  ],\n" +
                "  \"direction\": \"NORTH\",\n" +
                "  \"shields\": 3,\n" +
                "  \"shots\": 3,\n" +
                "  \"status\": \"normal\"\n" +
                "}", testState.execute(worldTest, args));
    }

}