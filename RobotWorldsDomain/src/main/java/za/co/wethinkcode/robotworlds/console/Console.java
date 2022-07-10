package za.co.wethinkcode.robotworlds.console;

import za.co.wethinkcode.robotworlds.Play;
import za.co.wethinkcode.robotworlds.console.commandhandler.ServerCommandHandler;
import za.co.wethinkcode.robotworlds.console.commands.ConsoleCommand;

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
        Play.setIsRunning(false);
        this.interrupt();
    }
}
