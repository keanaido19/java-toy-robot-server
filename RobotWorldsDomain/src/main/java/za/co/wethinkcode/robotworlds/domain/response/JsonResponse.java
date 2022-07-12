package za.co.wethinkcode.robotworlds.domain.response;

import za.co.wethinkcode.robotworlds.domain.commands.CommandResult;
import za.co.wethinkcode.robotworlds.domain.world.data.RobotData;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class JsonResponse {
    private CommandResult result;
    private Map<String, Object> data;
    private RobotData state;

    public JsonResponse() {}

    public JsonResponse(
            CommandResult result,
            Map<String, Object> data,
            RobotData state
    ) {
        this.result = result;
        this.data = data;
        this.state = state;
    }

    public JsonResponse(CommandResult result, Map<String, Object> data) {
        this.result = result;
        this.data = data;
    }

    public CommandResult getResult() {
        return result;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public RobotData getState() {
        return state;
    }

    public static JsonResponse getSuccessResponse(
            String key,
            Object o,
            RobotData robotData
    ) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put(key, o);
        return new JsonResponse(CommandResult.OK, data, robotData);
    }

    public static JsonResponse getSuccessResponse(
            Map<String, Object> data,
            RobotData robotData
    ) {
        return new JsonResponse(CommandResult.OK, data, robotData);
    }

    public static JsonResponse getErrorResponse(String message) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("message", message);
        return new JsonResponse(CommandResult.ERROR, data);
    }

    public static JsonResponse launchErrorResponse() {
        return getErrorResponse("Robot has not been launched");
    }

    public static JsonResponse commandErrorResponse() {
        return getErrorResponse("Unsupported command");
    }

    public static JsonResponse argumentErrorResponse() {
        return getErrorResponse("Could not parse arguments");
    }

    public static JsonResponse spaceErrorResponse() {
        return getErrorResponse("No more space in this world");
    }

    public static JsonResponse nameErrorResponse() {
        return getErrorResponse("Too many of you in this world");
    }

    public static JsonResponse robotErrorResponse() {
        return getErrorResponse("Robot does not exist");
    }

    public static JsonResponse illegalErrorResponse() {
        return getErrorResponse("Robot does not belong to you");
    }

    public static JsonResponse deadErrorResponse() {
        return getErrorResponse("Robot is dead");
    }

    public static JsonResponse reloadErrorResponse() {
        return getErrorResponse("Robot is busy reloading");
    }

    public static JsonResponse shotsErrorResponse() {
        return getErrorResponse("Robot has no gun to reload");
    }

    public static JsonResponse gunErrorResponse() {
        return getErrorResponse("Robot has no gun to fire");
    }

    public static JsonResponse repairErrorResponse() {
        return getErrorResponse("Robot is busy repairing");
    }

    public static JsonResponse shieldErrorResponse() {
        return getErrorResponse("Robot has no shields to repair");
    }

    public void setResult(CommandResult result) {
        this.result = result;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void setState(RobotData state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JsonResponse)) return false;
        JsonResponse that = (JsonResponse) o;
        return result == that.result
                && data.equals(that.data)
                && state.equals(that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, data, state);
    }
}
