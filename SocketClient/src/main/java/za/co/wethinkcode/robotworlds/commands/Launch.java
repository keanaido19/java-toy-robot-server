package za.co.wethinkcode.robotworlds.commands;

import com.google.gson.Gson;
import za.co.wethinkcode.robotworlds.RobotClient;

public class Launch extends ClientCommands{

    public Launch(String make, String name) {
        super("launch",make,name);
    }

    @Override
    public String execute(String Name) {
        String commandName = getName();
        String robotMake = getArgument();
        String robotName = getArgument2();
        String[] arguments = {robotMake};
        RequestJson launchCommandJson = new RequestJson(robotName, commandName, arguments);
        Gson gson = new Gson();
        String json = gson.toJson(launchCommandJson);
        RobotClient.launchCheck = true;
        return json;
    }
}
