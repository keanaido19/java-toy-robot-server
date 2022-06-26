package za.co.wethinkcode.robotworlds.world2x2;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * So that I can break the record for the most robot kills
 */
public class LaunchRobotTests extends TestBaseExtension {
    @Test
    void canLaunchAnotherRobot() {
        // Given that I am connected to a running Robot Worlds server
        // And there is already someone else connected to the same running
        //      Robot Worlds server
        // And the world is of size 2x2 (The world is configured or
        //      hardcoded to this size)
        assertTrue(serverClient.isConnected());
        testSuccessfulLaunch(launchRobot("HAL"));

        // When I send a valid launch request to the server
        testSuccessfulLaunch(launchRobot("R2D2"));
    }

    @Test
    void worldWithoutObstaclesIsFull() {
        // Given that I am connected to a running Robot Worlds server
        // And the Robot Worlds server is already full of robots
        // And the world is of size 2x2 (The world is configured or
        //      hardcoded to this size)
        assertTrue(serverClient.isConnected());
        List<JsonNode> responses = multiRobotLaunch(9);
        for (JsonNode response : responses) {
            testSuccessfulLaunch(response);
        }

        // When I send a valid launch request to the server
        JsonNode response = launchRobot("HAL");

        // Then...
        testFailedLaunchNoSpace(response);
    }
}
