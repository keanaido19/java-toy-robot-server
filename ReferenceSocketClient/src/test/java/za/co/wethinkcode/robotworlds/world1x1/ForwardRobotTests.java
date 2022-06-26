package za.co.wethinkcode.robotworlds.world1x1;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.TestBase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player I want to launch my robot in the online robot world And
 * move my robot forward, so I can explore the world
 */
public class ForwardRobotTests extends TestBase {
    @Test
    void movingAtTheEdgeOfTheWorld() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1
        //      (The world is configured or hardcoded to this size)
        // And a robot called "HAL" is already connected and launched
        Assertions.assertTrue(serverClient.isConnected());
        testSuccessfulLaunch(launchRobot("HAL"), 1);

        // When I send a command for "HAL" to move forward by 5 steps
        JsonNode response = executeCommand(
                "HAL",
                "forward",
                List.of(5));

        // Then I should get an "OK" response
        testCommandSuccessful(response);

        // And the message "At the NORTH edge"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertEquals(
                "At the NORTH edge",
                response.get("data").get("message").asText()
        );

        // And the position information returned should be at
        //      co-ordinates [0,0]
        assertNotNull(response.get("data").get("position"));
        assertEquals(
                "[0,0]",
                response.get("data").get("position").toString()
        );
    }
}
