package za.co.wethinkcode.robotworlds.domain.world.builders;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robotworlds.cli.CommandLineArgumentHandler;
import za.co.wethinkcode.robotworlds.domain.Helpers;
import za.co.wethinkcode.robotworlds.domain.JsonHandler;
import za.co.wethinkcode.robotworlds.cli.arguments.ObstacleArgument;
import za.co.wethinkcode.robotworlds.cli.arguments.SizeOfWorldArgument;
import za.co.wethinkcode.robotworlds.domain.world.World;
import za.co.wethinkcode.robotworlds.domain.world.data.WorldConfigData;
import za.co.wethinkcode.robotworlds.domain.world.data.WorldData;
import za.co.wethinkcode.robotworlds.domain.world.objects.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.domain.world.objects.obstacles.SquareObstacle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

public class WorldBuilder {
    private static final String HOME = System.getProperty("user.home");
    private static final String CONFIG_DIRECTORY =
            HOME + "/.config/robotworlds/";
    private static final String CONFIG_FILE =
            CONFIG_DIRECTORY + "WorldData.json";
    private static final String DEFAULT_WORLD_DATA =
            "{\"visibility\":1,\"reload\":3,\"repair\":3,\"mine\":3," +
                    "\"shields\":5,\"shots\":5}";
    private static File file;

    private static void createConfigDirectory() {
        file = new File(CONFIG_DIRECTORY);
        file.mkdir();
    }

    private static void createConfigFile() {
        file = new File(CONFIG_FILE);
        try {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(CONFIG_FILE);
                fileWriter.write(DEFAULT_WORLD_DATA);
                fileWriter.close();
            }
        } catch (IOException ignored) {}
    }

    private static String getConfigData() {
        String configData = "{}";
        try {
            configData = Files.readString(Path.of(CONFIG_FILE));
        } catch (IOException ignored) {}
        return configData;
    }

    private static void deleteConfigFile() {
        file = new File(CONFIG_FILE);
        file.delete();
    }

    private static boolean verifyConfigData(String configData) {
        Pattern pattern = Pattern.compile(
                "^\\{\"visibility\":\\d+,\"reload\":\\d+," +
                        "\"repair\":\\d+,\"mine\":\\d+," +
                        "\"shields\":\\d+,\"shots\":\\d+}$");

        if (!pattern.matcher(configData).find()) return false;

        JsonObject jsonObject = JsonHandler.createJsonObject(configData);
        LinkedHashMap<Object, Object> data =
                JsonHandler.convertJsonObjectToHashMap(jsonObject);

        List<String> keys =
                Arrays.asList(
                        "visibility",
                        "reload",
                        "repair",
                        "mine",
                        "shields",
                        "shots"
                );

        for (String key : keys) {
            Object o = data.get(key);
            if (!Helpers.isObjectPositiveInteger(o)) return false;
        }
        return true;
    }

    private static WorldConfigData getWorldConfigData() {
        String configData = getConfigData();
        if (!verifyConfigData(configData)) {
            deleteConfigFile();
            createConfigDirectory();
            createConfigFile();
            configData = DEFAULT_WORLD_DATA;
        }
        return (WorldConfigData) JsonHandler.convertJsonStringToObject(
                configData,
                WorldConfigData.class
        );
    }

    private static List<Obstacle> getObstacles(List<int[]> obstaclePositions) {
        List<Obstacle> returnList = new ArrayList<>();
        for (int[] position : obstaclePositions) {
            returnList.add(new SquareObstacle(position[0], position[1], 1));
        }
        return returnList;
    }

    public static World getWorld(String[] args) {
        CommandLineArgumentHandler CLIHandler =
                new CommandLineArgumentHandler(args);

        int worldSize =
                (int) CLIHandler.getArgumentValue(new SizeOfWorldArgument());

        @SuppressWarnings("unchecked")
        List<int[]> obstaclePositions =
                (List<int[]>) CLIHandler
                        .getArgumentValue(new ObstacleArgument());

        WorldData worldData =
                new WorldData(
                        worldSize,
                        worldSize,
                        getWorldConfigData()
                );

        World world = new World(worldData);
        world.setObstacles(getObstacles(obstaclePositions));

        return world;
    }
}
