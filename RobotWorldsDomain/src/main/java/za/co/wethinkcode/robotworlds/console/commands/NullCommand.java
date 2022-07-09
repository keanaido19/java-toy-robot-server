package za.co.wethinkcode.robotworlds.console.commands;

public class NullCommand extends ConsoleCommand {
    @Override
    public boolean execute() {
        System.out.println("Invalid Command!");
        return true;
    }
}
