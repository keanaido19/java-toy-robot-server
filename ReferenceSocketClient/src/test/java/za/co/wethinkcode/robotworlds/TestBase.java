package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestBase {
    public final RobotWorldClient serverClient = new RobotWorldJsonClient();

    private final static String DEFAULT_IP = "localhost";
    private final static int DEFAULT_PORT = 5000;

    private String request;

    @BeforeEach
    void connectToServer(){
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer(){
        serverClient.disconnect();
    }

    public JsonNode launchRobot(String name) {
        request = "{" +
                "  \"robot\": \"" + name + "\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
                "}";
        return serverClient.sendRequest(request);
    }

    public List<JsonNode> multiRobotLaunch(int numberOfRobots) {
        List<JsonNode> returnList = new ArrayList<>();
        for (int i = 0; i < numberOfRobots; i++) {
            returnList.add(launchRobot(String.valueOf(i)));
        }
        return returnList;
    }

    public JsonNode executeCommand(
            String robotName,
            String command,
            List<Object> arguments
    ) {
        List<String> listOfArguments = new ArrayList<>();

        for (Object arg : arguments) {
            listOfArguments.add("\"" + arg.toString() + "\"");
        }

        String args = StringUtils.join(listOfArguments, ",");

        request = "{" +
                "  \"robot\": \"" + robotName + "\"," +
                "  \"command\": \"" + command + "\"," +
                "  \"arguments\": [" + args + "]" +
                "}";
        return serverClient.sendRequest(request);
    }

    public JsonNode executeCommand(String robotName, String command) {
        return executeCommand(robotName, command, new ArrayList<>());
    }

    public void testCommandResult(JsonNode response, String result) {
        assertNotNull(response.get("result"));
        assertEquals(result, response.get("result").asText());
    }

    public void testCommandSuccessful(JsonNode response) {
        testCommandResult(response, "OK");
    }

    public void testCommandFailed(JsonNode response) {
        testCommandResult(response, "ERROR");
    }

    public void testSuccessfulLaunch(JsonNode response, int worldSize) {
        // Then I should get a valid response from the server
        testCommandSuccessful(response);

        // And the position should be random within worldSize x worldSize world
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("position"));

        List<Integer> possibleCoordinates =
                IntStream.rangeClosed(-worldSize/2, worldSize/2)
                        .boxed().collect(Collectors.toList());

        assertNotNull(response.get("data").get("position").get(0));
        assertNotNull(response.get("data").get("position").get(1));

        int x = response.get("data").get("position").get(0).asInt();
        int y = response.get("data").get("position").get(1).asInt();

        assertTrue(
                possibleCoordinates.contains(x)
                        && possibleCoordinates.contains(y)
        );

        // And I should also get the state of the robot
        assertNotNull(response.get("state"));
    }

    public void testFailedCommand(JsonNode response, String message) {
        // Then I should get an error response
        testCommandFailed(response);

        // And the message "No more space in this world"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(
                response.get("data").get("message").asText()
                        .contains(message)
        );
    }

    public void testFailedLaunchNoSpace(JsonNode response) {
        testFailedCommand(response, "No more space in this world");
    }

    public String createObject(String objectType, String direction,
                             int distance) {
        return "{\"direction\":\"" + direction + "\"," +
                "\"type\":\"" + objectType + "\",\"distance\":" +
                distance + "}";
    }

    public String createObstacle(String direction, int distance) {
        return createObject("OBSTACLE", direction, distance);
    }

    public String createRobot(String direction, int distance) {
        return createObject("ROBOT", direction, distance);
    }
}
