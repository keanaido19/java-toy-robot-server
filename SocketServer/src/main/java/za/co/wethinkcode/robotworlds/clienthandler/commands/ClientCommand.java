package za.co.wethinkcode.robotworlds.clienthandler.commands;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.World;
import com.google.gson.Gson;

public abstract class ClientCommand implements CommandInterface {

    private String argument;
    private String argument2;

    public ClientCommand(String name){
    }

    public ClientCommand(String name, String argument){
        this(name);
        this.argument=argument.trim();
    }

    public ClientCommand(String name, String argument, String argument2){
        this(name);
        this.argument=argument.trim();
        this.argument2 = argument2.trim();
    }

    public String getArgument() {
        return argument;
    }

    public String getArgument2() {
        return argument2;
    }

    public abstract ServerResponse execute(
            World world,
            String[] arguments,
            ClientHandler clientHandler
    );

    public static ClientCommand create(String instruction) throws CommandNotFoundException {
        Gson gson = new Gson();
        RequestMessage requestMessage = gson.fromJson(instruction, RequestMessage.class);

        switch (requestMessage.command) {
            case "launch":
                return new Launch(requestMessage.arguments[0], requestMessage.robot);
            case "look":
                return new Look(requestMessage.robot);
            case "state":
                return new State(requestMessage.robot);
            case "fire":
                return new Fire(requestMessage.robot);
            case "forward":
                return new Forward(requestMessage.robot, Integer.parseInt(requestMessage.arguments[0]));
            case "back":
                return new Back(requestMessage.robot, Integer.parseInt(requestMessage.arguments[0]));
            case "turn":
                return new Turn(requestMessage.robot, requestMessage.arguments[0]);
            case "reload":
                return new Reload(requestMessage.robot);
            case "repair":
                return new Repair(requestMessage.robot);
            case "quit":
                return new Quit(requestMessage.robot);
            default:
                throw new CommandNotFoundException("Unsupported command: " + instruction);
        }
    }

    public static class CommandNotFoundException extends Exception {

        public CommandNotFoundException(String message) {
            super(message);
        }
    }
}
