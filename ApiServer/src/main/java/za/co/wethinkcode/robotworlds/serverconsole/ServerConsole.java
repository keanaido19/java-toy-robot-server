package za.co.wethinkcode.robotworlds.serverconsole;

import za.co.wethinkcode.robotworlds.ApiServer;
import za.co.wethinkcode.robotworlds.serverconsole.commandhandler.ServerCommandHandler;
import za.co.wethinkcode.robotworlds.serverconsole.commands.ServerCommand;

import java.util.Scanner;

public class ServerConsole extends Thread{
    private final ApiServer server;

    public ServerConsole(ApiServer server) {
        this.server = server;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;

        System.out.println("Console is ready for input:");

        while (continueLoop && scanner.hasNextLine()) {
            String userInput = scanner.nextLine();
            ServerCommand serverCommand =
                    ServerCommandHandler.getServerCommand(userInput);
            continueLoop = serverCommand.execute(server);
        }
    }
}
