package za.co.wethinkcode.robotworlds.commands;

import com.google.gson.Gson;

public class Fire extends ClientCommands{

    public Fire() {
        super("fire");
    }

    @Override
    public String execute(String robotName){
        Gson gson = new Gson();
        String[] arguments = {};
        RequestJson fireCommandJson = new RequestJson(robotName, "fire", arguments);
        return gson.toJson(fireCommandJson);
    }

}
