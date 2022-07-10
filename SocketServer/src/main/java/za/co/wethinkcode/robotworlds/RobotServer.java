package za.co.wethinkcode.robotworlds;

import za.co.wethinkcode.robotworlds.arguments.ServerPortArgument;
import za.co.wethinkcode.robotworlds.console.ServerConsole;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Enumeration;

public class RobotServer {
    private final ServerSocket serverSocket;
    private final ServerConsole serverConsole = new ServerConsole(this);

    public RobotServer(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    private void startServer(){
        serverConsole.start();
        try{
            while(!serverSocket.isClosed()){
                ClientHandler clientHandler =
                        new ClientHandler(serverSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }
        } catch (IOException e) {
            stopServer();
        }
    }

    public void stopServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Enumeration<InetAddress> getInetAddress()
            throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces =
                NetworkInterface.getNetworkInterfaces();

        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface =
                    networkInterfaces.nextElement();

            // filters out 127.0.0.1 and inactive interfaces
            if (networkInterface.isLoopback() || !networkInterface.isUp())
                continue;

            return networkInterface.getInetAddresses();
        }

        throw new SocketException();
    }

    public static String getServerIpAddress() throws SocketException {
        String serverIpAddress;

        Enumeration<InetAddress> inetAddresses = getInetAddress();

        while (inetAddresses.hasMoreElements()) {
            InetAddress inetAddress = inetAddresses.nextElement();
            serverIpAddress = inetAddress.getHostAddress();

            if (serverIpAddress.length() <= 15)
                return serverIpAddress;
        }
        return "localhost";
    }

    public static void main(String[] args) throws IOException {

        CommandLineArgumentHandler CLIHandler =
                new CommandLineArgumentHandler(args);

        int serverPortNumber =
                (int) CLIHandler.getArgumentValue(new ServerPortArgument());

        ServerSocket serverSocket = new ServerSocket(serverPortNumber);

        RobotServer robotServer = new RobotServer(serverSocket);

        System.out.println(
                "Welcome to Robot Worlds Server!\n" +
                        "Server is listening on...\n" +
                        "\tIP Address :\t" + getServerIpAddress() +
                        "\n\tPort number:\t" + serverPortNumber
        );

        Play.start(args);
        robotServer.startServer();
    }
}
