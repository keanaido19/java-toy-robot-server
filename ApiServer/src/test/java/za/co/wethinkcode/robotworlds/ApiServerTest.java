package za.co.wethinkcode.robotworlds;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiServerTest {

    private ApiServer server;

    @BeforeEach
    void startServer() {
        server = new ApiServer(5000);
        server.start(new String[] {});
    }
    @AfterEach
    void stopServer() {
        Play.setWorld(null);
        server.stop();
    }

    @Test
    @DisplayName("GET /world")
    public void getWorld() {
        HttpResponse<JsonNode> response =
                Unirest.get("http://localhost:5000/world").asJson();
        assertEquals(200, response.getStatus());
        assertEquals("application/json",
                response.getHeaders().getFirst("Content-Type"));
        JSONObject jsonObject = response.getBody().getObject();

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
        HttpResponse<JsonNode> response =
                Unirest.get("http://localhost:5000/world/world1").asJson();
        assertEquals(200, response.getStatus());
        assertEquals("application/json",
                response.getHeaders().getFirst("Content-Type"));
        JSONObject jsonObject = response.getBody().getObject();

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


}