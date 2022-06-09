package za.co.wethinkcode.robotworlds.ClientCommands;

import com.google.gson.Gson;

public class Forward extends ClientCommands{

    public Forward(String steps) {
        super("forward", steps);
    }

    @Override
    public String execute(String robotName) {
        String[] arguments = {getArgument()};
        RequestJson forwardCommandJson = new RequestJson(robotName, "forward", arguments);
        Gson gson = new Gson();

        return gson.toJson(forwardCommandJson);
    }
}
