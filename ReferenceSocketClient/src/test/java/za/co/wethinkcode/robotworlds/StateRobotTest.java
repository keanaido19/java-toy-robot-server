package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


public class StateRobotTest {

    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();


    @BeforeEach
    void connectToServer() throws IOException {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer(){
        serverClient.disconnect();
    }

    /** A Valid State Command is issued to the server
     * and Connection has been established
     * and Robot World Server( of size 1x1)  is running
     * and Given Robot has NOT been launched into the world
     * THEN server should failure and declare that
     * the robot does not exist */
    @Test
    public void validStateCommandShouldFailure(){
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid state request to the server
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"state\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request);
        // Then I should get an error response from the server
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());
        assertNotNull(response.get("data"));
        assertEquals("Robot does not exist",
                response.get("data").get("message").asText());
    }

    @Test
    void validStateCommandShouldSucceed() {
        String request;
        JsonNode response;
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
                "}";
        serverClient.sendRequest(request);

        // When I send a valid state request to the server
        request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"state\"," +
                "  \"arguments\": []" +
                "}";
        response = serverClient.sendRequest(request);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And the position should be (x:0, y:0)
        assertNotNull(response.get("state"));
        assertNotNull(response.get("state").get("position"));
        assertEquals(0, response.get("data").get("position").get(0).asInt());
        assertEquals(0, response.get("data").get("position").get(1).asInt());

        // And I should receive some data
        assertNotNull(response.get("data"));
        assertEquals(
                "{\"visibility\":1,\"position\":[0,0],\"objects\":[]}",
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
