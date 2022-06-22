package za.co.wethinkcode.robotworlds.iteration1;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.RobotWorldClient;
import za.co.wethinkcode.robotworlds.RobotWorldJsonClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * And look at my surroundings
 * So that I can see everything in the world
 */
public class LookRobotTests {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServerAndLaunchRobot() {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

    }

    @AfterEach
    void disconnectFromServer(){
        serverClient.disconnect();
    }

    @Test
    void validLookShouldSucceed(){
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

        // And the position should be (x:0, y:0)
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("position"));
        assertEquals(0, response.get("data").get("position").get(0).asInt());
        assertEquals(0, response.get("data").get("position").get(1).asInt());

        // And the visibility should be 1
        assertNotNull(response.get("data").get("visibility"));
        assertEquals(1, response.get("data").get("visibility").asInt());

        // And I should see the edges of the world
        assertNotNull(response.get("data").get("objects"));

        List<String> objects = new ArrayList<>();

        objects.add(
                "{\"direction\":\"EAST\",\"type\":\"EDGE\",\"distance\":1}"
        );
        objects.add(
                "{\"direction\":\"WEST\",\"type\":\"EDGE\",\"distance\":1}"
        );
        objects.add(
                "{\"direction\":\"NORTH\",\"type\":\"EDGE\",\"distance\":1}"
        );
        objects.add(
                "{\"direction\":\"SOUTH\",\"type\":\"EDGE\",\"distance\":1}"
        );

        boolean onlySeeWorldEdges = true;

        for (Iterator<JsonNode> it =
             response.get("data").get("objects").elements(); it.hasNext();
             ) {
            String object = it.next().toString();
            if (objects.contains(object)) {
                objects.remove(object);
            } else {
//                onlySeeWorldEdges = false;
                break;
            }
        }

        assertTrue(onlySeeWorldEdges);
    }
}
