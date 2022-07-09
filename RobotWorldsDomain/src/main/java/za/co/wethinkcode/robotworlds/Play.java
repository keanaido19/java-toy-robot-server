package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.console.Console;
import za.co.wethinkcode.robotworlds.response.JsonResponseBuilder;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.builders.WorldBuilder;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

public class Play {
    private static World world;
    private static final JsonResponseBuilder JSON_RESPONSE_BUILDER =
            new JsonResponseBuilder();

    public Play(String[] args) {
        new Console().start();
        if (null == world)
            world = WorldBuilder.getWorld(args);
    }

    public static World getWorld() {
        return world;
    }

    public static void setWorld(World world) {
        Play.world = world;
    }

    public String getJsonStringResponse(JsonNode jsonRequest) {
        return JSON_RESPONSE_BUILDER.getStringResponse(jsonRequest);
    }

    public JsonResponseBuilder getResponseBuilder() {
        return JSON_RESPONSE_BUILDER;
    }

    public static void main(String[] args) {
    }
}
