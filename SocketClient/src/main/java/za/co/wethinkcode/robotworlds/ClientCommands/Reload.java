package za.co.wethinkcode.robotworlds.ClientCommands;

import com.google.gson.Gson;

public class Reload extends ClientCommands{

    public Reload() {
        super("reload");
    }

    public String execute(String robotName) {
        Gson gson = new Gson();
        String[] arguments = {};
        RequestJson reloadCommandJson = new RequestJson(robotName, "reload", arguments);
        return gson.toJson(reloadCommandJson);
    }

}
