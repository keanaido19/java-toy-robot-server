package za.co.wethinkcode.robotworlds.world2x2.obstacles.obstacle0_1;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.world2x2.TestBaseExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * And look at my surroundings
 * So that I can see everything in the world
 */
public class LookRobotTests extends TestBaseExtension {
    boolean checkIfObjectsAreSeen(JsonNode response, List<String> objects) {
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("objects"));

        for (Iterator<JsonNode> it =
             response.get("data").get("objects").elements(); it.hasNext();
        ) {
            String object = it.next().toString();

            for (String o : objects) {
                if (o.equals(object)) {
                    objects.remove(o);
                    break;
                }
            }
        }
        return objects.isEmpty();
    }

    @Test
    void seeAnObstacle() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 2x2
        //      (The world is configured or hardcoded to this size)
        // And there is an obstacle at coordinate [0,1]
        Assertions.assertTrue(serverClient.isConnected());
        testSuccessfulLaunch(launchRobot("HAL"));

        // When I send a valid look request to the server
        JsonNode response = executeCommand("HAL", "look");

        // Then I should get a valid response from the server
        testCommandSuccessful(response);

        // And I should see an OBSTACLE a distance of 1 step in the
        //      direction NORTH
        List<String> objects =
                new ArrayList<>(
                        List.of(
                                createObstacle("NORTH", 1)
                        )
                );
        assertTrue(checkIfObjectsAreSeen(response, objects));
    }

    @Test
    void seeAnObstacleAnd3Robots() {
        // Given that I am connected to a running Robot Worlds server
        // And the world is of size 2x2
        //      (The world is configured or hardcoded to this size)
        // And there is an obstacle at coordinate [0,1]
        Assertions.assertTrue(serverClient.isConnected());
        testSuccessfulLaunch(launchRobot("HAL"));

        // And I launch 7 other robots to fill the world
        multiRobotLaunch(7);

        // When I send a valid look request to the server
        JsonNode response = executeCommand("HAL", "look");

        // Then I should get a valid response from the server
        testCommandSuccessful(response);

        // And I should see an OBSTACLE a distance of 1 step in the
        //      direction NORTH
        // And I should see a ROBOT a distance of 1 step in the direction
        //      SOUTH, EAST and WEST
        List<String> objects =
                new ArrayList<>(
                        List.of(
                                createObstacle("NORTH", 1),
                                createRobot("EAST", 1),
                                createRobot("WEST", 1),
                                createRobot("SOUTH", 1)
                        )
                );
        assertTrue(checkIfObjectsAreSeen(response, objects));
    }
}
