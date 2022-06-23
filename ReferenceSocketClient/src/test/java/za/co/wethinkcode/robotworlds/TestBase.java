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
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";

    public final RobotWorldClient serverClient = new RobotWorldJsonClient();

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

    public void testSuccessfulLaunch(JsonNode response, int worldSize) {
        // Then I should get a valid response from the server
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());

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

    public void testFailedLaunch(JsonNode response, String message) {
        // Then I should get an error response
        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());

        // And the message "No more space in this world"
        assertNotNull(response.get("data"));
        assertNotNull(response.get("data").get("message"));
        assertTrue(
                response.get("data").get("message").asText()
                        .contains(message)
        );
    }

    public void testFailedLaunchNoSpace(JsonNode response) {
        testFailedLaunch(response, "No more space in this world");
    }

    public List<JsonNode> multiRobotLaunch(int numberOfRobots) {
        List<JsonNode> returnList = new ArrayList<>();
        for (int i = 0; i < numberOfRobots; i++) {
            returnList.add(launchRobot(String.valueOf(i)));
        }
        return returnList;
    }
}
