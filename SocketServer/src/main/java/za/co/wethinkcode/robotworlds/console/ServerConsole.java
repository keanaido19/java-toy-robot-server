package za.co.wethinkcode.robotworlds.console;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.console.commands.ServerCommand;

import java.util.Scanner;

public class ServerConsole extends Thread {
    private final Scanner scanner = new Scanner(System.in);
    ServerCommand serverCommand;

    public ServerConsole() {}

    @Override
    public void run() {

        System.out.println("Console is ready for input:");

        while (scanner.hasNextLine()) {
            String userInput = scanner.nextLine();
            try {
                serverCommand = ServerCommand.create(userInput);

                serverCommand.execute(
                        ClientHandler.users,
                        ClientHandler.robots,
                        ClientHandler.world
                );

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Command!");
            }
        }
    }
}
