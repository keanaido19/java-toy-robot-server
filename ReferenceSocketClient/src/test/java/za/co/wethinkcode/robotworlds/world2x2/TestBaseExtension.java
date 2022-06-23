package za.co.wethinkcode.robotworlds.world2x2;

import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.TestBase;

public class TestBaseExtension extends TestBase {
    public void testSuccessfulLaunch(JsonNode response) {
        super.testSuccessfulLaunch(response, 2);
    }
}
