package za.co.wethinkcode.robotworlds.world2x2.obstacles.obstacle1_1;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.world2x2.TestBaseExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * So that I can break the record for the most robot kills
 */
public class LaunchRobotTests extends TestBaseExtension {
    @Test
    void launchRobotsIntoWorldWithObstacle() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 2x2
        //      (The world is configured or hardcoded to this size)
        // And there is an obstacle at coordinate [1,1]
        Assertions.assertTrue(serverClient.isConnected());

        // When I launch 8 robots
        List<JsonNode> responses = multiRobotLaunch(8);
        for (JsonNode response : responses) {
            testSuccessfulLaunch(response);

            //And the robot position should not be at [1,1]
            assertNotEquals(
                    "[1,1]",
                    response.get("data").get("position").toString()
            );
        }
    }

    @Test
    void worldWithObstaclesIsFull() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 2x2
        //      (The world is configured or hardcoded to this size)
        // And there is an obstacle at coordinate [1,1]
        Assertions.assertTrue(serverClient.isConnected());

        // And I have successfully launched 8 robots into the world
        launchRobotsIntoWorldWithObstacle();

        // When I launch a 9th robot it should fail
        testFailedLaunchNoSpace(launchRobot("Hal"));
    }
}
