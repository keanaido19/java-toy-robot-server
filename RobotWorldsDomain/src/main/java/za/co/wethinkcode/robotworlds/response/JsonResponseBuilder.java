package za.co.wethinkcode.robotworlds.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonResponseBuilder {
    private String robotName;
    private String command;
    private List<String> commandArguments;

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

    public synchronized JsonNode getResponse(JsonNode jsonRequest) {
        processJsonRequest(jsonRequest);
        return null;
    }
}
