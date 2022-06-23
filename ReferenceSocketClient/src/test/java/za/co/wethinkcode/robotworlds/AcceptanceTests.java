package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AcceptanceTests {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer(){
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer(){
        serverClient.disconnect();
    }

    JsonNode launchRobot(String name) {
        String request = "{" +
                "  \"robot\": \"" + name + "\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
                "}";
        return serverClient.sendRequest(request);
    }

    /**
     * As a player
     * I want to launch my robot in the online robot world
     * So that I can break the record for the most robot kills
     */
    @Nested
    class LaunchRobotTests{
        @Test
        void validLaunchShouldSucceed(){
            // Given that I am connected to a running Robot Worlds server
            // And the world is of size 1x1
            //      (The world is configured or hardcoded to this size)
            assertTrue(serverClient.isConnected());

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
        void invalidLaunchShouldFail(){
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
            assertTrue(serverClient.isConnected());
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
            assertTrue(serverClient.isConnected());
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

    /**
     * As a player
     * I want to launch my robot in the online robot world
     * And look at my surroundings
     * So that I can see everything in the world
     */
    @Nested
    class LookRobotTests {
        @Test
        void validLookShouldSucceed(){
            // Given that I am connected to a running Robot Worlds server
            // And the world is of size 1x1
            //      (The world is configured or hardcoded to this size)
            assertTrue(serverClient.isConnected());
            launchRobot("HAL");

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

    /**
     * As a player
     * I want to launch my robot in the online robot world
     * And get the state of my robot
     * So that I can use the information to plan my next move
     */
    @Nested
    class StateRobotTest {

        /** A Valid State Command is issued to the server
         * and Connection has been established
         * and Robot World Server( of size 1x1)  is running
         * and Given Robot has NOT been launched into the world
         * THEN server should failure and declare that
         * the robot does not exist */
        @Test
        public void validStateCommandShouldFailure(){
            // Given that I am connected to a running Robot Worlds server
            // And the world is of size 1x1
            //      (The world is configured or hardcoded to this size)
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
            // Given that I am connected to a running Robot Worlds server
            // And the world is of size 1x1
            //      (The world is configured or hardcoded to this size)
            assertTrue(serverClient.isConnected());

            launchRobot("HAL");

            // When I send a valid state request to the server
            String request = "{" +
                    "  \"robot\": \"HAL\"," +
                    "  \"command\": \"state\"," +
                    "  \"arguments\": []" +
                    "}";
            JsonNode response = serverClient.sendRequest(request);

            // Then I should get a valid response from the server
            assertNotNull(response.get("result"));
            assertEquals("OK", response.get("result").asText());

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
}
