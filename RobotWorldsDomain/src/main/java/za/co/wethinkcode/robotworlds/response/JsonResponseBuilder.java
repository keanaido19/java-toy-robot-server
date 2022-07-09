package za.co.wethinkcode.robotworlds.response;

import com.fasterxml.jackson.databind.JsonNode;
import za.co.wethinkcode.robotworlds.JsonHandler;
import za.co.wethinkcode.robotworlds.Play;
import za.co.wethinkcode.robotworlds.commandhandler.CommandHandler;
import za.co.wethinkcode.robotworlds.commands.Command;
import za.co.wethinkcode.robotworlds.commands.LaunchCommand;
import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.LookCommand;
import za.co.wethinkcode.robotworlds.commands.auxilliarycommands.StateCommand;
import za.co.wethinkcode.robotworlds.world.enums.Status;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonResponseBuilder {
    private String robotName;
    private String command;
    private List<String> commandArguments;
    private CommandHandler commandHandler;

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

    private void processJsonRequest(JsonNode jsonRequest) {
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

    public synchronized String getStringResponse(JsonNode jsonRequest) {
        JsonResponse jsonResponse;

        processJsonRequest(jsonRequest);
        setCommandHandler();

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
            jsonResponse = command.execute();
            return JsonHandler.convertJavaObjectToJsonString(jsonResponse);
        }

        Robot robot = Play.getWorld().getRobot(robotName);

        if (null == robot) {
            jsonResponse = JsonResponse.robotErrorResponse();
            return JsonHandler.convertJavaObjectToJsonString(jsonResponse);
        }

        if (command instanceof StateCommand) {
            jsonResponse = command.execute();
            return JsonHandler.convertJavaObjectToJsonString(jsonResponse);
        }

        Status currentStatus = robot.getRobotStatus();

        if (Status.DEAD.equals(currentStatus)) {
            jsonResponse = JsonResponse.deadErrorResponse();
            return JsonHandler.convertJavaObjectToJsonString(jsonResponse);
        }

        if (command instanceof LookCommand) {
            jsonResponse = command.execute();
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
            jsonResponse = command.execute();
            return JsonHandler.convertJavaObjectToJsonString(jsonResponse);
        }

        return "{}";
    }
}
