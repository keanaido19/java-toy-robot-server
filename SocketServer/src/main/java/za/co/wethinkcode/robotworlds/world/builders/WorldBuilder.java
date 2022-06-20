//package za.co.wethinkcode.robotworlds.world.builders;
//
//import com.google.gson.JsonObject;
//import za.co.wethinkcode.robotworlds.Helpers;
//import za.co.wethinkcode.robotworlds.JsonHandler;
//import za.co.wethinkcode.robotworlds.world.Position;
//import za.co.wethinkcode.robotworlds.world.World;
//import za.co.wethinkcode.robotworlds.world.data.WorldData;
//import za.co.wethinkcode.robotworlds.world.objects.obstacles.SquareObstacle;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.Arrays;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Random;
//import java.util.regex.Pattern;
//
//public class WorldBuilder {
//    private static final String HOME = System.getProperty("user.home");
//    private static final String CONFIG_DIRECTORY =
//            HOME + "/.config/robotworlds/";
//    private static final String CONFIG_FILE =
//            CONFIG_DIRECTORY + "WorldData.json";
//    private static final String DEFAULT_WORLD_DATA =
//            "{\"width\":200,\"height\":400,\"visibility\":10,\"reload\":3," +
//                    "\"repair\":3,\"shields\":5,\"shots\":5}";
//    private static File file;
//
//    private static void createConfigDirectory() {
//        file = new File(CONFIG_DIRECTORY);
//        file.mkdir();
//    }
//
//    public static void createConfigFile() {
//        file = new File(CONFIG_FILE);
//        try {
//            if (file.createNewFile()) {
//                FileWriter fileWriter = new FileWriter(CONFIG_FILE);
//                fileWriter.write(DEFAULT_WORLD_DATA);
//                fileWriter.close();
//            }
//        } catch (IOException e) {}
//    }
//
//    private static String getConfigData() {
//        String configData = "{}";
//        try {
//            configData = Files.readString(Path.of(CONFIG_FILE));
//        } catch (IOException ignored) {}
//        return configData;
//    }
//
//    private static void deleteConfigFile() {
//        file = new File(CONFIG_FILE);
//        file.delete();
//    }
//
//    private static boolean verifyConfigData(String configData) {
//        Pattern pattern = Pattern.compile(
//                "^\\{\"width\":\\d+,\"height\":\\d+," +
//                        "\"visibility\":\\d+,\"reload\":\\d+,\"repair\":\\d+," +
//                        "\"shields\":\\d+,\"shots\":\\d+}$");
//
//        if (!pattern.matcher(configData).find()) return false;
//
//        JsonObject jsonObject = JsonHandler.createJsonObject(configData);
//        LinkedHashMap<Object, Object> data =
//                JsonHandler.convertJsonObjectToHashMap(jsonObject);
//
//        List<String> keys = Arrays.asList(
//                "width", "height", "visibility", "reload", "repair", "shields",
//                "shots"
//        );
//
//        for (String key : keys) {
//            Object o = data.get(key);
//            if (!Helpers.isObjectPositiveInteger(o)) return false;
//        }
//        return true;
//    }
//
//    private static WorldData getWorldData() {
//        String configData = getConfigData();
//        if (!verifyConfigData(configData)) {
//            deleteConfigFile();
//            createConfigDirectory();
//            createConfigFile();
//            configData = DEFAULT_WORLD_DATA;
//        }
//        return (WorldData) JsonHandler.convertJsonStringToObject(configData,
//                WorldData.class);
//    }
//
//    public static World getWorldWithSquareObstacles() {
//        SquareObstacle o;
//        Position p;
//        World world = new World(getWorldData());
//
//        int obstacleAmount = new Random().nextInt(30) + 20;
//        int size = 5;
//
//        for (int i = 0; i < obstacleAmount; i++) {
//            p = world.getUnoccupiedPosition(size);
//            if (p == null) break;
//            o = new SquareObstacle(p, 5);
//            world.addObjectToWorld(o);
//        }
//        return world;
//    }
//}
