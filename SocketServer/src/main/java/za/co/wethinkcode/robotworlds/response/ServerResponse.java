package za.co.wethinkcode.robotworlds.response;

import za.co.wethinkcode.robotworlds.clienthandler.commands.CommandResult;
import za.co.wethinkcode.robotworlds.world.data.RobotData;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ServerResponse {
    private CommandResult result;
    private Map<String, Object> data;
    private RobotData state;

    public ServerResponse(
            CommandResult result,
            Map<String, Object> data,
            RobotData state
    ) {
        this.result = result;
        this.data = data;
        this.state = state;
    }

    public ServerResponse(CommandResult result, Map<String, Object> data) {
        this.result = result;
        this.data = data;
    }

    public static ServerResponse getSuccessResponse(
            String key,
            Object o,
            RobotData robotData
    ) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put(key, o);
        return new ServerResponse(CommandResult.OK, data, robotData);
    }

    public static ServerResponse getSuccessResponse(
            Map<String, Object> data,
            RobotData robotData
    ) {
        return new ServerResponse(CommandResult.OK, data, robotData);
    }

    public static ServerResponse getErrorResponse(String message) {
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("message", message);
        return new ServerResponse(CommandResult.ERROR, data);
    }

    public static ServerResponse launchErrorResponse() {
        return getErrorResponse("Robot has not been launched");
    }

    public static ServerResponse commandErrorResponse() {
        return getErrorResponse("Unsupported command");
    }

    public static ServerResponse argumentErrorResponse() {
        return getErrorResponse("Could not parse arguments");
    }

    public static ServerResponse spaceErrorResponse() {
        return getErrorResponse("No more space in this world");
    }

    public static ServerResponse nameErrorResponse() {
        return getErrorResponse("Too many of you in this world");
    }

    public static ServerResponse robotErrorResponse() {
        return getErrorResponse("Robot is not in this world");
    }

    public static ServerResponse illegalErrorResponse() {
        return getErrorResponse("Robot does not belong to you");
    }

    public static ServerResponse deadErrorResponse() {
        return getErrorResponse("Robot is dead");
    }

    public static ServerResponse reloadErrorResponse() {
        return getErrorResponse("Robot is busy reloading");
    }

    public static ServerResponse shotsErrorResponse() {
        return getErrorResponse("Robot has no gun to reload");
    }

    public static ServerResponse gunErrorResponse() {
        return getErrorResponse("Robot has no gun to fire");
    }

    public static ServerResponse repairErrorResponse() {
        return getErrorResponse("Robot is busy repairing");
    }

    public static ServerResponse shieldErrorResponse() {
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
        if (!(o instanceof ServerResponse)) return false;
        ServerResponse that = (ServerResponse) o;
        return result == that.result
                && data.equals(that.data)
                && state.equals(that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, data, state);
    }
}
