package za.co.wethinkcode.robotworlds;

import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.CLIhandler.arguments.ServerPortArgument;
import za.co.wethinkcode.robotworlds.CLIhandler.CommandLineArgumentHandler;
import za.co.wethinkcode.robotworlds.console.ServerConsole;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;

public class RobotServer {

    private final ServerConsole serverConsole;
    private final ServerSocket serverSocket;

    public RobotServer(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        this.serverConsole = new ServerConsole();
    }

    public void startServer(){
        serverConsole.start();
        try{
            while(!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("A new user has connected to the server.");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }

        }catch (IOException e) {
            closeServerSocket();
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getServerIpAddress() {

        String serverIpAddress;

        try {
            Enumeration<NetworkInterface> networkInterfaces =
                    NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface =
                        networkInterfaces.nextElement();

                // filters out 127.0.0.1 and inactive interfaces
                if (networkInterface.isLoopback() || !networkInterface.isUp())
                    continue;

                Enumeration<InetAddress> inetAddresses =
                        networkInterface.getInetAddresses();

                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    serverIpAddress = inetAddress.getHostAddress();

                    if (serverIpAddress.length() <= 15)
                        return serverIpAddress;
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return "localhost";
    }

    public static void main(String[] args) throws IOException {

        CommandLineArgumentHandler CLIHandler =
                new CommandLineArgumentHandler(args);

        int serverPortNumber =
                (int) CLIHandler.getArgumentValue(new ServerPortArgument());

        System.out.println(
                "Welcome to Robot Worlds Server!\n" +
                "Server is listening on...\n" +
                "\tIP Address :\t" + getServerIpAddress() +
                "\n\tPort number:\t" + serverPortNumber
        );

        ServerSocket serverSocket = new ServerSocket(serverPortNumber);

        RobotServer robotServer = new RobotServer(serverSocket);
        robotServer.startServer();
    }
}
