package za.co.wethinkcode.robotworlds.world1x1;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.TestBase;

import static org.junit.jupiter.api.Assertions.*;

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
        Assertions.assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        JsonNode response = launchRobot("HAL");

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And the position should be (x:0, y:0)
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("position"));
        assertEquals(
                0,
                response.get("data").get("position").get(0).asInt()
        );
        assertEquals(0,
                response.get("data").get("position").get(1).asInt()
        );

        // And I should also get the state of the robot
        assertNotNull(response.get("state"));
    }

    @Test
    void invalidLaunchShouldFail() {
        // Given that I am connected to a running Robot Worlds server
        Assertions.assertTrue(serverClient.isConnected());

        // When I send an invalid launch request with the command
        //      "luanch" instead of "launch"
        String request = "{" +
                "\"robot\": \"HAL\"," +
                "\"command\": \"luanch\"," +
                "\"arguments\": [\"sniper\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(
                response.get("data").get("message")
                        .asText().contains("Unsupported command")
        );
    }

    @Test
    void validLaunchShouldFailNoSpaceForRobotInWorld() {
        JsonNode response;

        // Given that I am connected to a running Robot Worlds server
        // And there is already someone else connected to the same running
        //      Robot Worlds server
        // And the world is of size 1x1
        //      (The world is configured or hardcoded to this size)
        Assertions.assertTrue(serverClient.isConnected());
        response = launchRobot("A");
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("position"));
        assertEquals(
                0,
                response.get("data").get("position").get(0).asInt()
        );
        assertEquals(
                0,
                response.get("data").get("position").get(1).asInt()
        );
        assertNotNull(response.get("state"));

        // When I send a valid launch request to the server
        response = launchRobot("HAL");

        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "No more space in this world"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(
                response.get("data").get("message").asText()
                        .contains("No more space in this world")
        );
    }

    @Test
    void validLaunchShouldFailRobotAlreadyExists() {
        JsonNode response;

        // Given that I am connected to a running Robot Worlds server
        // And there is already someone else connected to the same running
        //      Robot Worlds server
        // And the name of their robot is the same name I wish to name my
        //      robot
        // And the world is of size 1x1 (The world is configured or
        //      hardcoded to this size)
        Assertions.assertTrue(serverClient.isConnected());
        response = launchRobot("HAL");
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("position"));
        assertEquals(
                0,
                response.get("data").get("position").get(0).asInt()
        );
        assertEquals(
                0,
                response.get("data").get("position").get(1).asInt()
        );
        assertNotNull(response.get("state"));

        // When I send a valid launch request to the server
        response = launchRobot("HAL");

        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Too many of you in this world"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(
                response.get("data").get("message").asText()
                        .contains("Too many of you in this world")
        );
    }
}
