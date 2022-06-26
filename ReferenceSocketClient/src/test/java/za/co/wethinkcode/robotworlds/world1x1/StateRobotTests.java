package za.co.wethinkcode.robotworlds.world1x1;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.TestBase;

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * And get the state of my robot
 * So that I can use the information to plan my next move
 */
public class StateRobotTests extends TestBase {
    /**
     * A Valid State Command is issued to the server
     * and Connection has been established
     * and Robot World Server( of size 1x1)  is running
     * and Given Robot has NOT been launched into the world
     * THEN server should failure and declare that
     * the robot does not exist
     */
    @Test
    public void validStateCommandShouldFailure() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1
        //      (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid state request to the server
        JsonNode response =
                executeCommand("Hal", "state");

        // Then I should get an error response from the server
        testFailedCommand(response, "Robot does not exist");
    }

    @Test
    void validStateCommandShouldSucceed() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1
        //      (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());
        launchRobot("HAL");

        // When I send a valid state request to the server
        JsonNode response =
                executeCommand("HAL", "state");

        // Then I should get a valid response from the server
        testCommandSuccessful(response);

        // And the position should be (x:0, y:0)
        assertNotNull(response.get("state"));
        assertNotNull(response.get("state").get("position"));
        assertEquals(
                0,
                response.get("data").get("position").get(0).asInt()
        );
        assertEquals(
                0,
                response.get("data").get("position").get(1).asInt()
        );

        // And I should receive some data
        assertNotNull(response.get("data"));
        assertEquals(
                "{\"visibility\":1,\"position\":[0,0]," +
                        "\"objects\":[]}",
                response.get("data").toString());

        // And the direction should be North
        assertNotNull(response.get("state").get("direction"));
        assertEquals("\"NORTH\"",
                response.get("state").get("direction").toString());

        // And I should have shields
        assertNotNull(response.get("state").get("shields"));
        assertTrue(
                0 <= response.get("state").get("shields").asInt()
        );

        // And I should have shots
        assertNotNull(response.get("state").get("shots"));
        assertTrue(
                0 <= response.get("state").get("shots").asInt()
        );

        // And I should have a status
        assertNotNull(response.get("state").get("status"));
        String status = response.get("state").get("status").toString();
        assertEquals(0, status.indexOf("\""));
        assertEquals(
                status.length() - 1,
                status.lastIndexOf("\"")
        );
    }
}
