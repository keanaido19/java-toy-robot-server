package za.co.wethinkcode.robotworlds.domain.console;

import za.co.wethinkcode.robotworlds.domain.console.commandhandler.ServerCommandHandler;
import za.co.wethinkcode.robotworlds.domain.console.commands.ConsoleCommand;

import java.util.Scanner;

public class Console extends Thread{

    public Console() {}

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        System.out.println("Console is ready for input:");

        while (continueLoop && scanner.hasNextLine()) {
            String userInput = scanner.nextLine();
            ConsoleCommand consoleCommand =
                    ServerCommandHandler.getServerCommand(userInput);
            continueLoop = consoleCommand.execute();
        }
    }
}
