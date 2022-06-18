package za.co.wethinkcode.robotworlds.commands;

import com.google.gson.Gson;

public class Back extends ClientCommands{

    public Back(String steps) {
        super("back", steps);
    }

    @Override
    public String execute(String robotName) {
        String[] arguments = {getArgument()};
        RequestJson backCommandJson = new RequestJson(robotName, "back", arguments);
        Gson gson = new Gson();

        return gson.toJson(backCommandJson);
    }

}
