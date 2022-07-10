package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.console.Console;
import za.co.wethinkcode.robotworlds.response.JsonResponseBuilder;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.builders.WorldBuilder;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.ArrayList;
import java.util.List;

public class Play {
    private static boolean isRunning = true;
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

    public static boolean isIsRunning() {
        return isRunning;
    }

    public static void setIsRunning(boolean isRunning) {
        Play.isRunning = isRunning;
    }

    public void addRobot(Robot robot) {
        robots.add(robot);
    }

    public String getJsonStringResponse(JsonNode jsonRequest) {
        return jsonResponseBuilder.getStringResponse(jsonRequest);
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
}
