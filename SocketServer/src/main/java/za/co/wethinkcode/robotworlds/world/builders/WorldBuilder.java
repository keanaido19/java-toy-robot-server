package za.co.wethinkcode.robotworlds.world.builders;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robotworlds.CLIhandler.CommandLineArgumentHandler;
import za.co.wethinkcode.robotworlds.CLIhandler.arguments.ObstacleArgument;
import za.co.wethinkcode.robotworlds.CLIhandler.arguments.ServerPortArgument;
import za.co.wethinkcode.robotworlds.CLIhandler.arguments.SizeOfWorldArgument;
import za.co.wethinkcode.robotworlds.Helpers;
import za.co.wethinkcode.robotworlds.JsonHandler;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.data.WorldConfigData;
import za.co.wethinkcode.robotworlds.world.data.WorldData;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.Obstacle;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
            "{\"visibility\":1,\"reload\":3,\"repair\":3,\"shields\"" +
            ":5,\"shots\":5}";
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
                        "\"repair\":\\d+,\"shields\":\\d+,\"shots\":\\d+}$");

        if (!pattern.matcher(configData).find()) return false;

        JsonObject jsonObject = JsonHandler.createJsonObject(configData);
        LinkedHashMap<Object, Object> data =
                JsonHandler.convertJsonObjectToHashMap(jsonObject);

        List<String> keys =
                Arrays.asList(
                        "visibility",
                        "reload",
                        "repair",
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

    public static World getWorld(String[] args) {
        CommandLineArgumentHandler CLIHandler =
                new CommandLineArgumentHandler(args);

        int worldSize =
                (int) CLIHandler.getArgumentValue(new SizeOfWorldArgument());

        @SuppressWarnings("unchecked")
        List<Obstacle> obstacles =
                (List<Obstacle>) CLIHandler
                        .getArgumentValue(new ObstacleArgument());

        WorldData worldData =
                new WorldData(
                        worldSize,
                        worldSize,
                        getWorldConfigData()
                );

        World world = new World(worldData);
        world.setObstacles(obstacles);

        return world;
    }
}
