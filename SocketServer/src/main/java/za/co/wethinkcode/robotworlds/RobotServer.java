package za.co.wethinkcode.robotworlds;

import com.google.gson.Gson;
import za.co.wethinkcode.robotworlds.ServerCommands.ServerCommand;
import za.co.wethinkcode.robotworlds.World.SquareObstacle;

import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.Scanner;

public class RobotServer {

    private final ServerInput serverInput;
    private final ServerSocket serverSocket;

    public RobotServer(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        this.serverInput = new ServerInput();
    }

    public void startServer(){
        serverInput.start();
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

    public static int portChoice(){
        Scanner scanner = new Scanner(System.in);
        String portChosen;
        System.out.println("What Port would you like to use(4 digit number)");
        do {
            portChosen = scanner.nextLine();
        } while (!portCheck(portChosen));
        return Integer.parseInt(portChosen);
    }

    public static void myIp() {
        String ip;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iFace = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                if (iFace.isLoopback() || !iFace.isUp())
                    continue;
                System.out.println("Here are you ip addresses for users to connect to: ");
                Enumeration<InetAddress> addresses = iFace.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    ip = address.getHostAddress();
                    System.out.println(iFace.getDisplayName() + " " + ip);
                }
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean portCheck(String portChoice){
        try {
            Integer.parseInt(portChoice);
            if(portChoice.length() != 4){
                System.out.println("4 Digits are required please choose a valid port");
                return false;
            }

            return true;

        } catch (NumberFormatException e) {
            System.out.println("you need to input a 4 digit number.");
        }

        return false;
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

    static int integerChooser(String configTypeChoice){
        String answer;
        boolean passes = false;
        int steps = 5;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Next what "+ configTypeChoice + " would you like?");
        do{
            answer = scanner.nextLine();
            try{
                steps = Integer.parseInt(answer);
                passes = true;
            }catch(Exception e){
                System.out.println("Please input a digit.");
            }
        }while(!passes);
        return steps;
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

        System.out.println("Welcome to Robot Worlds Server Cpt18 please take a moment to configure server settings.");
        int port = 5000;
        myIp();
        fileConfig();
        ServerSocket serverSocket = new ServerSocket(port);
        RobotServer server = new RobotServer(serverSocket);
        System.out.println("Server configuration successful starting server :)");
        server.startServer();
    }

    private static class ServerInput extends Thread {
        Scanner scanner;
        ServerCommand command;
        public ServerInput() {
            this.scanner = new Scanner(System.in);
        }

        @Override
        public void run() {
            super.run();
            while (true) {
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
}
