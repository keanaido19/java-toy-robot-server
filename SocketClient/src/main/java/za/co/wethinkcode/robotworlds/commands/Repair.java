package za.co.wethinkcode.robotworlds.commands;

import com.google.gson.Gson;

public class Repair extends ClientCommands{

    public Repair() {
        super("repair");
    }

    public String execute(String robotName) {
        Gson gson = new Gson();
        String[] arguments = {};
        RequestJson repairCommandJson = new RequestJson(robotName, "repair", arguments);
        return gson.toJson(repairCommandJson);
    }

}