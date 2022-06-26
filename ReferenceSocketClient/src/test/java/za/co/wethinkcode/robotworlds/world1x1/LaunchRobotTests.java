package za.co.wethinkcode.robotworlds.world1x1;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.TestBase;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * So that I can break the record for the most robot kills
 */
public class LaunchRobotTests extends TestBase {
    @Test
    void validLaunchShouldSucceed() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1
        //      (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        JsonNode response = launchRobot("HAL");

        // Then I should get a valid response from the server
        // And the position should be (x:0, y:0)
        // And I should also get the state of the robot
        testSuccessfulLaunch(response, 1);
    }

    @Test
    void invalidLaunchShouldFail() {
        // Given that I am connected to a running Robot Worlds server
        assertTrue(serverClient.isConnected());

        // When I send an invalid launch request with the command
        //      "luanch" instead of "launch"
        String request = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"luanch\"," +
                "\"arguments\": [\"sniper\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response
        // And the message "Unsupported command"
        testFailedCommand(response, "Unsupported command");
    }

    @Test
    void validLaunchShouldFailNoSpaceForRobotInWorld() {
        // Given that I am connected to a running Robot Worlds server
        // And there is already someone else connected to the same running
        //      Robot Worlds server
        // And the world is of size 1x1
        //      (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());
        testSuccessfulLaunch(launchRobot("A"), 1);

        // When I send a valid launch request to the server
        JsonNode response = launchRobot("HAL");

        // Then I should get an error response
        // And the message "No more space in this world"
        testFailedCommand(response, "No more space in this world");
    }

    @Test
    void validLaunchShouldFailRobotAlreadyExists() {
        // Given that I am connected to a running Robot Worlds server
        // And there is already someone else connected to the same running
        //      Robot Worlds server
        // And the name of their robot is the same name I wish to name my
        //      robot
        // And the world is of size 1x1 (The world is configured or
        //      hardcoded to this size)
        assertTrue(serverClient.isConnected());
        testSuccessfulLaunch(launchRobot("HAL"), 1);

        // When I send a valid launch request to the server
        JsonNode response = launchRobot("HAL");

        // Then I should get an error response
        // And the message "Too many of you in this world"
        testFailedCommand(response, "Too many of you in this world");
    }
}
