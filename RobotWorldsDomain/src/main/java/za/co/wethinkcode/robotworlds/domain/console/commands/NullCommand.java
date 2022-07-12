package za.co.wethinkcode.robotworlds.domain.console.commands;

public class NullCommand extends ConsoleCommand {
    @Override
    public boolean execute() {
        System.out.println("Invalid Command!");
        return true;
    }
}
