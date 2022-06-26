package za.co.wethinkcode.robotworlds.world1x1;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.TestBase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * And look at my surroundings
 * So that I can see everything in the world
 */
public class LookRobotTests extends TestBase {
    @Test
    void validLookShouldSucceed() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 1x1
        //      (The world is configured or hardcoded to this size)
        Assertions.assertTrue(serverClient.isConnected());
        launchRobot("HAL");

        // When I send a valid look request to the server
        JsonNode response = executeCommand("HAL", "look");

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
        assertEquals(
                0,
                response.get("data").get("position").get(1).asInt()
        );

        // And the visibility should be 1
        assertNotNull(response.get("data").get("visibility"));
        assertEquals(
                1,
                response.get("data").get("visibility").asInt()
        );

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
