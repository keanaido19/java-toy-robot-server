package za.co.wethinkcode.robotworlds.commands;

import com.google.gson.Gson;

public class Turn extends ClientCommands{

    public Turn(String turnDirection){
        super("turn",turnDirection);
    }

    @Override
    public String execute(String robotName) {
        String[] arguments = {getArgument()};
        RequestJson turnCommandJson = new RequestJson(robotName,"turn",arguments);
        Gson gson = new Gson();
        return gson.toJson(turnCommandJson);
    }
}
