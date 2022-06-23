package za.co.wethinkcode.robotworlds.world2x2.obstacles.obstacle0_1;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.world2x2.TestBaseExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AcceptanceTests extends TestBaseExtension {
    /**
     * As a player
     * I want to launch my robot in the online robot world
     * And look at my surroundings
     * So that I can see everything in the world
     */
    @Nested
    class LookRobotTests {
        @Test
        void seeAnObstacle() {
            // Given that I am connected to a running Robot Worlds server
            // And the world is of size 2x2
            //      (The world is configured or hardcoded to this size)
            // And there is an obstacle at coordinate [0,1]
            assertTrue(serverClient.isConnected());
            testSuccessfulLaunch(launchRobot("HAL"));

            // When I send a valid look request to the server
            JsonNode response = executeCommand("HAL", "look");

        }
    }
}
