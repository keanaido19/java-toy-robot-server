package za.co.wethinkcode.robotworlds.ClientCommands;

import com.google.gson.Gson;

public class Quit extends ClientCommands{

    public Quit() {
        super("quit");
    }

    @Override
    public String execute(String robotName) {
        Gson gson = new Gson();
        String[] arguments = {};
        RequestJson quitCommandJson = new RequestJson(robotName, "quit", arguments);
        return gson.toJson(quitCommandJson);
    }

}

