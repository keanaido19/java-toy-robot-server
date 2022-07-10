package za.co.wethinkcode.robotworlds;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Json Handler Class.
 */
public class JsonHandler {
    private static final Gson gson = new Gson();
    private static Type type;

    /**
     * Creates a json object from a given json string.
     *
     * @param jsonString a json string
     * @return json object
     */
    public static JsonObject createJsonObject(String jsonString) {
        JsonObject jsonObject = new JsonObject();
        try{
            if (jsonString != null) {
                jsonObject =
                        JsonParser.parseString(jsonString).getAsJsonObject();
            }
        } catch (JsonSyntaxException | IllegalStateException ignored) {}
        return jsonObject;
    }

    /**
     * Converts a json array to a list of objects.
     *
     * @param jsonArray a json array
     * @return list of objects
     */
    public static List<Object> convertJsonArrayToList(JsonArray jsonArray) {
        type = new TypeToken<List<Object>>() {}.getType();
        return gson.fromJson(jsonArray, type);
    }

    /**
     * Converts a json object to a list objects.
     *
     * @param jsonObject a json object
     * @return list of objects
     */
    public static List<Object> convertJsonObjectToList(JsonObject jsonObject) {
        type = new TypeToken<HashMap<Object, Object>>() {}.getType();
        return Collections.singletonList(gson.fromJson(jsonObject, type));
    }

    /**
     * Converts json object to a linked hash map.
     *
     * @param jsonObject a json object
     * @return linked hash map
     */
    public static LinkedHashMap<Object, Object> convertJsonObjectToHashMap(
            JsonObject jsonObject
    )
    {
        type = new TypeToken<LinkedHashMap<Object, Object>>() {}.getType();
        return gson.fromJson(jsonObject, type);
    }

    /**
     * Gets json value from a json object as a list of objects.
     *
     * @param key        the key
     * @param jsonObject the json object
     * @return the json value as list
     */
    public static List<Object> getJsonValueAsList(
            String key, JsonObject jsonObject
    )
    {
        List<Object> returnList = new ArrayList<>(Collections.emptyList());

        JsonElement a = jsonObject.get(key);

        if (a == null) {
            return returnList;
        }

        if (a.isJsonArray()) {
            JsonArray b = a.getAsJsonArray();
            returnList = convertJsonArrayToList(b);
        } else if (a.isJsonObject()) {
            JsonObject c = a.getAsJsonObject();
            returnList = convertJsonObjectToList(c);
        } else if (a.isJsonPrimitive()) {
            returnList.add(a.getAsString());
        }
        return returnList;
    }

    /**
     * Gets json value from a json object as string.
     *
     * @param key        the key
     * @param jsonObject the json object
     * @return the json value as string
     */
    public static String getJsonValueAsString(
            String key, JsonObject jsonObject
    )
    {
        String returnString = null;

        JsonElement a = jsonObject.get(key);

        if (a == null) {
            return null;
        }

        if (a.isJsonPrimitive()) {
            returnString = a.getAsString();
        } else if (a.isJsonArray()) {
            JsonArray b = a.getAsJsonArray();
            returnString = convertJsonArrayToList(b).toString();
        } else if (a.isJsonObject()) {
            JsonObject c = a.getAsJsonObject();
            returnString = convertJsonObjectToHashMap(c).toString();
        }
        return returnString;
    }

    /**
     * Converts a java object to a json string.
     *
     * @param o the given object
     * @return json string representation of the given object
     */
    public static String convertJavaObjectToJsonString(Object o) {
        return gson.toJson(o);
    }

    public static boolean isJsonStringParseableToObject(
            String jsonString,
            Class<?> c
    ) {
        try {
            gson.fromJson(jsonString, c);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }

    public static Object convertJsonStringToObject(
            String jsonString,
            Class<?> c
    ) {
        return gson.fromJson(jsonString, c);
    }
}
