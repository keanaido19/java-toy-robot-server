package za.co.wethinkcode.robotworlds;

import za.co.wethinkcode.robotworlds.CLIhandler.CommandLineArgumentHandler;
import za.co.wethinkcode.robotworlds.CLIhandler.arguments.ObstacleArgument;
import za.co.wethinkcode.robotworlds.CLIhandler.arguments.ServerPortArgument;
import za.co.wethinkcode.robotworlds.CLIhandler.arguments.SizeOfWorldArgument;
import za.co.wethinkcode.robotworlds.clienthandler.ClientHandler;
import za.co.wethinkcode.robotworlds.console.ServerConsole;
import za.co.wethinkcode.robotworlds.world.data.WorldConfigData;
import za.co.wethinkcode.robotworlds.world.data.WorldData;
import za.co.wethinkcode.robotworlds.world.objects.obstacles.Obstacle;
import za.co.wethinkcode.robotworlds.world.World;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.List;

public class RobotServer {

    private final ServerConsole serverConsole;
    private final ServerSocket serverSocket;
    private final World world;

    public RobotServer(ServerSocket serverSocket, World world){
        this.serverSocket = serverSocket;
        this.serverConsole = new ServerConsole(this);
        this.world = world;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public World getWorld() {
        return world;
    }

    public void startServer(){
        serverConsole.start();
        try{
            while(!serverSocket.isClosed()){
                ClientHandler clientHandler = new ClientHandler(this);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }

        }catch (IOException e) {
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
        int worldSize =
                (int) CLIHandler.getArgumentValue(new SizeOfWorldArgument());
        List<Obstacle> obstacles =
                (List<Obstacle>) CLIHandler
                        .getArgumentValue(new ObstacleArgument());

        WorldData worldData =
                new WorldData(
                        worldSize,
                        worldSize,
                        new WorldConfigData()
                );

        World world = new World(worldData);
        world.setObstacles(obstacles);

        ServerSocket serverSocket = new ServerSocket(serverPortNumber);

        RobotServer robotServer = new RobotServer(serverSocket, world);

        System.out.println(
                "Welcome to Robot Worlds Server!\n" +
                        "Server is listening on...\n" +
                        "\tIP Address :\t" + getServerIpAddress() +
                        "\n\tPort number:\t" + serverPortNumber
        );

        robotServer.startServer();
    }
}
