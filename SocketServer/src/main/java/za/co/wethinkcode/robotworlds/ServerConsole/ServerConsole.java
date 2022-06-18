package za.co.wethinkcode.robotworlds.ServerConsole;

import za.co.wethinkcode.robotworlds.ClientHandler;
import za.co.wethinkcode.robotworlds.ServerConsole.ServerCommands.ServerCommand;

import java.util.Scanner;

public class ServerConsole extends Thread {
    Scanner scanner;
    ServerCommand command;

    public ServerConsole() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        super.run();
        while (scanner.hasNextLine()) {
            String serverCommand = scanner.nextLine();
            try {
                command = ServerCommand.create(serverCommand);
                command.execute(ClientHandler.users, ClientHandler.robots, ClientHandler.world);
            } catch (IllegalArgumentException e) {
                System.out.println("Command is unrecognised");
            }
        }
    }
}
