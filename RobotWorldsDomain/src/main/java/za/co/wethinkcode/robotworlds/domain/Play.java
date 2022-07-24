package za.co.wethinkcode.robotworlds.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.wethinkcode.robotworlds.domain.console.Console;
import za.co.wethinkcode.robotworlds.domain.response.JsonResponseBuilder;
import za.co.wethinkcode.robotworlds.domain.world.World;
import za.co.wethinkcode.robotworlds.domain.world.builders.WorldBuilder;
import za.co.wethinkcode.robotworlds.domain.world.objects.robots.Robot;

import java.util.ArrayList;
import java.util.List;

public class Play {

    private static World world;

    private final List<Robot> robots = new ArrayList<>();

    private final JsonResponseBuilder jsonResponseBuilder =
            new JsonResponseBuilder(this);

    public Play() {}

    public static World getWorld() {
        return world;
    }

    public static void setWorld(World world) {
        Play.world = world;
    }

    public void addRobot(Robot robot) {
        robots.add(robot);
    }

    public String getJsonStringResponse(String jsonStringRequest) {
        return jsonResponseBuilder.getStringResponse(jsonStringRequest);
    }

    public static void start(String[] args) {
        if (null == world)
            world = WorldBuilder.getWorld(args);
    }

    public void stop() {
        for (Robot robot : robots) {
            world.removeRobot(robot);
        }
    }

    public static void main(String[] args) {
        try {
            String lol = "{}";
            JsonNode loll = new ObjectMapper().readTree(lol);
            loll.get("pie").asText();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        start(args);
//        new Console().start();
    }
}
