package za.co.wethinkcode.robotworlds.ClientCommands;

import com.google.gson.Gson;

public class Look extends ClientCommands{

    public Look() {
        super("look");
    }

    @Override
    public String execute(String robotName) {
        Gson gson = new Gson();
        String[] arguments = {};
        RequestJson lookCommandJson = new RequestJson(robotName, "look", arguments);
        return gson.toJson(lookCommandJson);
    }
}
