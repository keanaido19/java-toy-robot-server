package za.co.wethinkcode.robotworlds.api.server;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.*;
import za.co.wethinkcode.robotworlds.domain.Play;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiServerTest {

    private static ApiServer server;

    @BeforeAll
    static void startServer(){
        server = new ApiServer(5000);
        server.start(new String[] {});
    }

    @BeforeEach
    void resetWorld() {
        Play.setWorld(null);
        Play.start(new String[] {});
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    private JSONObject getServerResponse(String url) {
        HttpResponse<JsonNode> response =
                Unirest.get(url).asJson();
        assertEquals(200, response.getStatus());
        assertEquals("application/json",
                response.getHeaders().getFirst("Content-Type"));
        return response.getBody().getObject();
    }

    private HttpResponse<JsonNode> operateRobot(
            String name,
            String command,
            List<Object> arguments
    ) {
        JsonRequest jsonRequest =new JsonRequest();

        jsonRequest.setRobot(name);
        jsonRequest.setCommand(command);
        jsonRequest.setArguments(arguments);

        return Unirest.post("http://localhost:5000/robot")
                .header("Content-Type", "application/json")
                .body(jsonRequest)
                .asJson();
    }

    private HttpResponse<JsonNode> launchTestRobot() {
        return operateRobot(
                "testBot",
                "launch",
                List.of("sniper", 5, 5)
        );
    }

    @Test
    @DisplayName("GET /world")
    public void getWorld() {
        JSONObject jsonObject =
                getServerResponse("http://localhost:5000/world");

        assertEquals(
                "{\"worldName\":\"\"," +
                        "\"worldData\":{" +
                        "\"width\":1," +
                        "\"height\":1," +
                        "\"visibility\":1," +
                        "\"repairTime\":3," +
                        "\"reloadTime\":3," +
                        "\"mineTime\":3," +
                        "\"maxShield\":5," +
                        "\"maxShots\":5}," +
                        "\"obstacles\":[]," +
                        "\"pits\":[]," +
                        "\"mines\":[]}",
                jsonObject.toString());
    }

    @Test
    @DisplayName("GET /world/world1")
    public void getWorld1() {
        JSONObject jsonObject =
                getServerResponse("http://localhost:5000/world/world1");

        assertEquals(
                "{\"worldName\":\"world1\"," +
                        "\"worldData\":{" +
                        "\"width\":2," +
                        "\"height\":2," +
                        "\"visibility\":1," +
                        "\"repairTime\":3," +
                        "\"reloadTime\":3," +
                        "\"mineTime\":3," +
                        "\"maxShield\":5," +
                        "\"maxShots\":5}," +
                        "\"obstacles\":[{" +
                        "\"width\":1,\"height\":1,\"x\":0,\"y\":1}]," +
                        "\"pits\":[]," +
                        "\"mines\":[]}",
                jsonObject.toString());
    }

    @Test
    @DisplayName("GET /world/worldLOL")
    public void getWorldShouldFail() {
        HttpResponse<JsonNode> response =
                Unirest.get("http://localhost:5000/world/worldLOL").asJson();
        assertEquals(404, response.getStatus());
        assertEquals("application/json",
                response.getHeaders().getFirst("Content-Type"));
        JSONObject jsonObject = response.getBody().getObject();
        assertEquals(
                "{\"title\":\"World not found: 'worldLOL'\"," +
                        "\"status\":404," +
                        "\"type\":" +
                        "\"https://javalin.io/documentation#notfoundresponse\"," +
                        "\"details\":{}}",
                jsonObject.toString());
    }

    @Test
    @DisplayName("POST /robot")
    public void launchRobot() {
        HttpResponse<JsonNode> response = launchTestRobot();

        assertEquals(200, response.getStatus());
        assertEquals(
                "/robot",
                response.getHeaders().getFirst("Location")
        );

        assertEquals(
                "{\"result\":\"OK\",\"data\":{\"visibility\":1," +
                "\"position\":[0,0],\"objects\":[]},\"state\":{" +
                        "\"position\":[0,0],\"direction\":\"NORTH\"," +
                        "\"shields\":5,\"shots\":5,\"status\":\"NORMAL\"}}",
                response.getBody().toString());
    }

    @Test
    @DisplayName("POST /robot")
    public void lookRobot() {
        launchTestRobot();

        HttpResponse<JsonNode> response =
                operateRobot("testBot", "look", new ArrayList<>());

        assertEquals(200, response.getStatus());
        assertEquals(
                "/robot",
                response.getHeaders().getFirst("Location")
        );

        assertEquals(
                "{\"result\":\"OK\",\"data\":{\"visibility\":1," +
                        "\"position\":[0,0],\"objects\":[{\"direction\":" +
                        "\"NORTH\",\"type\":\"EDGE\",\"distance\":0}," +
                        "{\"direction\":\"EAST\",\"type\":\"EDGE\"," +
                        "\"distance\":0},{\"direction\":\"SOUTH\"," +
                        "\"type\":\"EDGE\",\"distance\":0},{\"direction\":" +
                        "\"WEST\",\"type\":\"EDGE\",\"distance\":0}]}," +
                        "\"state\":{\"position\":[0,0],\"direction\":\"NORTH\"," +
                        "\"shields\":5,\"shots\":5,\"status\":\"NORMAL\"}}",
                response.getBody().toString());
    }
}