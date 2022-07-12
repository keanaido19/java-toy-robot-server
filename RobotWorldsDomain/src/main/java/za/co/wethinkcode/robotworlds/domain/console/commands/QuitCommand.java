package za.co.wethinkcode.robotworlds.domain.console.commands;

public class QuitCommand extends ConsoleCommand {
    @Override
    public boolean execute() {
        System.out.println("Shutting down...");
        return false;
    }
}
