package za.co.wethinkcode.robotworlds.commands;

public abstract class ClientCommands implements ClientCommandsInterface{

    private final String name;
    private String argument;
    private String argument2;

    public ClientCommands(String name){
        this.name = name.trim().toLowerCase();
    }

    public ClientCommands(String name, String argument){
        this(name);
        this.argument=argument.trim();
    }

    public ClientCommands(String name, String argument, String argument2){
        this(name);
        this.argument=argument.trim();
        this.argument2 = argument2.trim();
    }

    public String getName(){
        return name;
    }

    public String getArgument() {
        return argument;
    }

    public String getArgument2() {
        return argument2;
    }

    public abstract String execute(String robotName);

    public static ClientCommands create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");
        switch (args[0]) {
            case "launch":
                return new Launch(args[1], args[2]);
            case "look":
                return new Look();
            case "state":
                return new State();
            case "fire":
                return new Fire();
            case "forward":
                return new Forward(args[1]);
            case "back":
                return new Back(args[1]);
            case"turn":
                return new Turn(args[1]);
            case "reload":
                return new Reload();
            case "repair":
                return new Repair();
            case "quit":
                return new Quit();
            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }
}
