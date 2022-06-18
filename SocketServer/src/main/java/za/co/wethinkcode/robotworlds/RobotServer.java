package za.co.wethinkcode.robotworlds;

import com.google.gson.Gson;
import za.co.wethinkcode.robotworlds.CommandLineArgumentHandler.Arguments.ServerPortArgument;
import za.co.wethinkcode.robotworlds.CommandLineArgumentHandler.CommandLineArgumentHandler;
import za.co.wethinkcode.robotworlds.World.SquareObstacle;
import za.co.wethinkcode.robotworlds.ServerConsole.ServerConsole;

import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;

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
                Thread thread = new Thread(clientHandler);
                thread.start();
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

    public static void fileConfig(){
        ConfigFileJson.GridJson gridJson;
        Gson gson = new Gson();
        gridJson = mapSizeChooser();
        int visibility = 1;
        SquareObstacle[] obstaclesList = obstacleChooser(gridJson);
        int shieldRepairTime = 5;
        int reloadTime = 5;
        int maxShieldStrength = 5;
        String json = gson.toJson(new ConfigFileJson(gridJson,visibility,obstaclesList,
                shieldRepairTime,reloadTime,maxShieldStrength));
        System.out.println("Thank you server configuration being set up.");
        try (FileWriter file = new FileWriter("Config.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(json);
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static ConfigFileJson.GridJson mapSizeChooser(){
        return new ConfigFileJson.GridJson(0,0);
    }

    static SquareObstacle[] obstacleChooser(ConfigFileJson.GridJson mapSize){
        return obstaclesMaker(0, mapSize.x,mapSize.y);
    }

    static SquareObstacle[] obstaclesMaker(int obstaclesAmount , int xSize, int ySize){
        Random random = new Random();
        boolean sharesPosition;
        SquareObstacle[] obstacles = new SquareObstacle[obstaclesAmount];
        ArrayList<SquareObstacle> obstaclesArrayList = new ArrayList<>();
        while(obstaclesArrayList.size()!= obstaclesAmount){
            sharesPosition = false;
            SquareObstacle newObstacle = new SquareObstacle((random.nextInt(xSize - (-xSize)) + (-xSize)),
                    random.nextInt(ySize - (-ySize)) + (-ySize));
            for(SquareObstacle squareObstacle : obstaclesArrayList){
               sharesPosition = sharesPosition(squareObstacle, newObstacle);
            }
            if(sharesPosition){
                continue;
            }
            obstaclesArrayList.add(newObstacle);
        }
        obstaclesArrayList.toArray(obstacles);
        return obstacles;
    }

    static boolean sharesPosition(SquareObstacle existingObstacle , SquareObstacle newObstacle){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(newObstacle.getBottomLeftX() + i == existingObstacle.getBottomLeftX() + j) {
                    return true;
                }
                else if(newObstacle.getBottomLeftY() + i == existingObstacle.getBottomLeftY() + j){
                        return true;
                }
            }
        }
        return false;
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
        RobotServer server = new RobotServer(serverSocket);
        server.startServer();
    }
}
