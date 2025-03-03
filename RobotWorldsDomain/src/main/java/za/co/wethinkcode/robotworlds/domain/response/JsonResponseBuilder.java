package za.co.wethinkcode.robotworlds.domain.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.wethinkcode.robotworlds.domain.JsonHandler;
import za.co.wethinkcode.robotworlds.domain.Play;
import za.co.wethinkcode.robotworlds.domain.commandhandler.CommandHandler;
import za.co.wethinkcode.robotworlds.domain.commands.Command;
import za.co.wethinkcode.robotworlds.domain.commands.LaunchCommand;
import za.co.wethinkcode.robotworlds.domain.commands.auxilliarycommands.LookCommand;
import za.co.wethinkcode.robotworlds.domain.commands.auxilliarycommands.StateCommand;
import za.co.wethinkcode.robotworlds.domain.world.enums.Status;
import za.co.wethinkcode.robotworlds.domain.world.objects.robots.Robot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonResponseBuilder {
    private final Play play;
    private String robotName;
    private String command;
    private List<String> commandArguments;
    private CommandHandler commandHandler;

    public JsonResponseBuilder(Play play) {
        this.play = play;
    }

    private List<String> processCommandArguments(JsonNode commandArguments) {
        List<String> returnList = new ArrayList<>();
        for (
                Iterator<JsonNode> it = commandArguments.elements();
                it.hasNext();
                ) {
            returnList.add(it.next().asText());
        }
        return returnList;
    }

    private void processJsonStringRequest(String jsonStringRequest)
            throws Exception {
        JsonNode jsonRequest = new ObjectMapper().readTree(jsonStringRequest);
        robotName = jsonRequest.get("robot").asText();
        command = jsonRequest.get("command").asText();
        commandArguments =
                processCommandArguments(jsonRequest.get("arguments"));
    }

    private void setCommandHandler() {
        commandHandler =
                new CommandHandler(
                        robotName,
                        command,
                        commandArguments
                );
    }

    public synchronized String getStringResponse(String jsonStringRequest) {
        try {
            processJsonStringRequest(jsonStringRequest);
            setCommandHandler();

        } catch (Exception e) {
            return JsonHandler.convertJavaObjectToJsonString(
                    JsonResponse.badRequestErrorResponse()
            );
        }
        return getStringResponse();
    }

    private String getStringResponse() {
        JsonResponse jsonResponse;

        if (!commandHandler.isValidCommand()) {
            jsonResponse = JsonResponse.commandErrorResponse();
            return JsonHandler.convertJavaObjectToJsonString(jsonResponse);
        }
        if (!commandHandler.isValidCommandArguments()) {
            jsonResponse = JsonResponse.argumentErrorResponse();
            return JsonHandler.convertJavaObjectToJsonString(jsonResponse);
        }

        Command command = commandHandler.getCommand();

        if (null == robotName) {
            jsonResponse = JsonResponse.launchErrorResponse();
            return JsonHandler.convertJavaObjectToJsonString(jsonResponse);
        }

        if (command instanceof LaunchCommand) {
            jsonResponse = command.execute(play);
            return JsonHandler.convertJavaObjectToJsonString(jsonResponse);
        }

        Robot robot = Play.getWorld().getRobot(robotName);

        if (null == robot) {
            jsonResponse = JsonResponse.robotErrorResponse();
            return JsonHandler.convertJavaObjectToJsonString(jsonResponse);
        }

        if (command instanceof StateCommand) {
            jsonResponse = command.execute(play);
            return JsonHandler.convertJavaObjectToJsonString(jsonResponse);
        }

        Status currentStatus = robot.getRobotStatus();

        if (Status.DEAD.equals(currentStatus)) {
            jsonResponse = JsonResponse.deadErrorResponse();
            return JsonHandler.convertJavaObjectToJsonString(jsonResponse);
        }

        if (command instanceof LookCommand) {
            jsonResponse = command.execute(play);
            return JsonHandler.convertJavaObjectToJsonString(jsonResponse);
        }

        switch (currentStatus) {
            case RELOAD:
                jsonResponse = JsonResponse.reloadErrorResponse();
                return
                        JsonHandler
                                .convertJavaObjectToJsonString(jsonResponse);
            case REPAIR:
                jsonResponse = JsonResponse.repairErrorResponse();
                return
                        JsonHandler
                                .convertJavaObjectToJsonString(jsonResponse);
        }

        if (command != null) {
            jsonResponse = command.execute(play);
            return JsonHandler.convertJavaObjectToJsonString(jsonResponse);
        }

        return "{}";
    }
}
