package za.co.wethinkcode.robotworlds.iteration2;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.RobotWorldClient;
import za.co.wethinkcode.robotworlds.RobotWorldJsonClient;

import static org.junit.jupiter.api.Assertions.*;


class LookRobotTests {
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
@Test
    void LookRobotTest() {
    // Given that I am connected to a running Robot Worlds server
    // And the world is of size 1x1 (The world is configured or hardcoded to this size)
    assertTrue(serverClient.isConnected());

    // When I send a valid launch request to the server
    String request = "{" +
            "  \"robot\": \"HAL\"," +
            "  \"command\": \"launch\"," +
            "  \"arguments\": [\"sniper\",\"5\",\"5\"]" +
            "}";
    JsonNode response = serverClient.sendRequest(request);

    String request1 = "{" +
            "  \"robot\": \"HAL\"," +
            "  \"command\": \"look\"," +
            "  \"arguments\": []" +
            "}";
    JsonNode responses = serverClient.sendRequest(request1);

    System.out.println(responses);

    // Then I should get a valid response from the server
    assertNotNull(responses.get("result"));
    assertEquals("OK", responses.get("result").asText());

    assertNotNull(responses.get("data"));
    assertNotNull(responses.get("data").get("objects"));
    System.out.println(responses.get("data").get("objects"));
    System.out.println(response.get("data").get("objects").elements());

    // And the visibility should be 10
    assertNotNull(response.get("data").get("visibility"));
    assertEquals(10, response.get("data").get("visibility").asInt());

}
}




