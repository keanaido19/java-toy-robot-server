package za.co.wethinkcode.robotworlds.iteration1;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.RobotWorldClient;
import za.co.wethinkcode.robotworlds.RobotWorldJsonClient;

import java.io.IOException;


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
                "  \"arguments\": [\"\"]" +
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
        Assertions.assertTrue(serverClient.isConnected());

        request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
                "}";
        response = serverClient.sendRequest(request);

        // When I send a valid state request to the server
        request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"state\"," +
                "  \"arguments\": [\"\"]" +
                "}";
        response = serverClient.sendRequest(request);

        System.out.println(response);

        // Then I should get a valid response from the server
        Assertions.assertNotNull(response.get("result"));
        Assertions.assertEquals("OK", response.get("result").asText());

        // And the position should be (x:0, y:0)
        Assertions.assertNotNull(response.get("state"));
        Assertions.assertNotNull(response.get("state").get("position"));
        Assertions.assertEquals(0, response.get("data").get("position").get(0).asInt());
        Assertions.assertEquals(0, response.get("data").get("position").get(1).asInt());

        // And I should receive some data
        Assertions.assertNotNull(response.get("data"));
        Assertions.assertEquals(
                "{\"visibility\":1,\"position\":[0,0],\"objects\":[]}",
                response.get("data").toString());

        // And the direction should be North
        Assertions.assertNotNull(response.get("state").get("direction"));
        Assertions.assertEquals("\"NORTH\"",
                response.get("state").get("direction").toString());

        // And I should have shields
        Assertions.assertNotNull(response.get("state").get("shields"));
        Assertions.assertTrue(
                0 <= response.get("state").get("shields").asInt()
        );

        // And I should have shots
        Assertions.assertNotNull(response.get("state").get("shots"));
        Assertions.assertTrue(
                0 <= response.get("state").get("shots").asInt()
        );

        // And I should have a status
        Assertions.assertNotNull(response.get("state").get("status"));
        String status = response.get("state").get("status").toString();
        Assertions.assertEquals(0, status.indexOf("\""));
        Assertions.assertEquals(
                status.length() - 1,
                status.lastIndexOf("\"")
        );
    }
}
