package za.co.wethinkcode.robotworlds.response;

import com.google.gson.JsonObject;
import za.co.wethinkcode.robotworlds.JsonHandler;
import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.clienthandler.commandhandler.CommandHandler;
import za.co.wethinkcode.robotworlds.clienthandler.commands.Command;
import za.co.wethinkcode.robotworlds.clienthandler.commands.LaunchCommand;
import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.LookCommand;
import za.co.wethinkcode.robotworlds.clienthandler.commands.auxiliarycommands.StateCommand;
import za.co.wethinkcode.robotworlds.world.enums.Status;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.util.List;

public class ServerResponseBuilder {
    private List<Object> commandArguments;

    private CommandHandler commandHandler;
    private ClientHandler clientHandler;
    private String robotName;
    private String command;

    public ServerResponseBuilder() {
    }

    public synchronized void setupServerResponseBuilder(
            ClientHandler clientHandler
    ) {
        this.clientHandler = clientHandler;
    }

    private void processJsonStringRequest(String jsonStringRequest) {
        JsonObject jsonRequestObject =
                JsonHandler.createJsonObject(jsonStringRequest);
        robotName =
                JsonHandler.getJsonValueAsString(
                        "robot",
                        jsonRequestObject
                );
        command =
                JsonHandler.getJsonValueAsString(
                        "command",
                        jsonRequestObject
                );
        commandArguments =
                JsonHandler.getJsonValueAsList(
                        "arguments",
                        jsonRequestObject
                );
    }

    private void setCommandHandler() {
        commandHandler =
                new CommandHandler(
                        robotName,
                        command,
                        commandArguments
                );
    }

    public synchronized String getJsonStringResponse(String jsonStringRequest) {
        ServerResponse serverResponse;

        processJsonStringRequest(jsonStringRequest);

        setCommandHandler();

        if (!commandHandler.isValidCommand()) {
            serverResponse = ServerResponse.commandErrorResponse();
            return JsonHandler.convertJavaObjectToJsonString(serverResponse);
        }
        if (!commandHandler.isValidCommandArguments()) {
            serverResponse = ServerResponse.argumentErrorResponse();
            return JsonHandler.convertJavaObjectToJsonString(serverResponse);
        }

        Command command = commandHandler.getCommand();

        if (null == robotName) {
            serverResponse = ServerResponse.launchErrorResponse();
            return JsonHandler.convertJavaObjectToJsonString(serverResponse);
        }

        if (command instanceof LaunchCommand) {
            serverResponse = command.execute(clientHandler);
            return JsonHandler.convertJavaObjectToJsonString(serverResponse);
        }

        Robot clientRobot = clientHandler.getRobot();

        if (clientRobot == null) {
            serverResponse = ServerResponse.robotErrorResponse();
            return JsonHandler.convertJavaObjectToJsonString(serverResponse);
        }

        String clientRobotName = clientRobot.getName();

        if (!robotName.equals(clientRobotName)) {
            serverResponse = ServerResponse.illegalErrorResponse();
            return JsonHandler.convertJavaObjectToJsonString(serverResponse);
        }

        if (command instanceof StateCommand) {
            serverResponse = command.execute(clientHandler);
            return JsonHandler.convertJavaObjectToJsonString(serverResponse);
        }

        Status currentStatus = clientRobot.getRobotStatus();

        if (Status.DEAD.equals(currentStatus)) {
            serverResponse = ServerResponse.deadErrorResponse();
            return JsonHandler.convertJavaObjectToJsonString(serverResponse);
        }

        if (command instanceof LookCommand) {
            serverResponse = command.execute(clientHandler);
            return JsonHandler.convertJavaObjectToJsonString(serverResponse);
        }

        switch (currentStatus) {
            case RELOAD:
                serverResponse = ServerResponse.reloadErrorResponse();
                return
                        JsonHandler
                                .convertJavaObjectToJsonString(serverResponse);
            case REPAIR:
                serverResponse = ServerResponse.repairErrorResponse();
                return
                        JsonHandler
                                .convertJavaObjectToJsonString(serverResponse);
        }

        if (command != null) {
            serverResponse = command.execute(clientHandler);
            return JsonHandler.convertJavaObjectToJsonString(serverResponse);
        }

        return "{}";
    }
}
