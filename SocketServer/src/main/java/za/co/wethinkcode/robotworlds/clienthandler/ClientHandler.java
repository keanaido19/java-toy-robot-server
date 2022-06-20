package za.co.wethinkcode.robotworlds.clienthandler;

import com.google.gson.Gson;
import za.co.wethinkcode.robotworlds.RobotServer;
import za.co.wethinkcode.robotworlds.response.ErrorResponse;
import za.co.wethinkcode.robotworlds.response.ServerResponse;
import za.co.wethinkcode.robotworlds.world.objects.robots.robot.Robot;
import za.co.wethinkcode.robotworlds.world.World;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    private final BufferedReader inputFromClient;
    private final PrintStream outputToClient;
    private final ServerSocket serverSocket;
    private final Socket socket;
    private final World world;

    private final Gson gson = new Gson();

    private ServerResponse serverResponse;


    public static ArrayList<ClientHandler> users = new ArrayList<>();
    public static ArrayList<Robot> robots = new ArrayList<>();
    public Robot robot;
    RequestMessage requestMessage;
    ClientCommand clientCommand;

    public ClientHandler(RobotServer server) throws IOException {
        this.serverSocket = server.getServerSocket();
        this.socket = serverSocket.accept();

        String clientIpAddress = socket.getInetAddress().getHostName();
        System.out.println("Client (" + clientIpAddress + ") Has Connected!");

        this.inputFromClient =
                new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
        this.outputToClient =
                new PrintStream(socket.getOutputStream(), true);

        this.world = server.getWorld();
        users.add(this);
    }

    public World getWorld() {
        return world;
    }

    public void closeEverything(
            Socket socket,
            BufferedReader inputFromClient,
            PrintStream outputToClient
    ) {
        world.getRobots().remove(robot);
        try {
            if (inputFromClient != null) {
                inputFromClient.close();
            }
            if (outputToClient != null) {
                outputToClient.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void outputToClient(ServerResponse serverResponse) {
        outputToClient.println(gson.toJson(serverResponse));
    }

    @Override
    public void run() {
        String commandFromClient;
        try {
            while (
                    !serverSocket.isClosed() &&
                    (commandFromClient = inputFromClient.readLine()) != null
            ) {

                try {
                    clientCommand = ClientCommand.create(commandFromClient);

                    requestMessage =
                            gson.fromJson(
                                    commandFromClient,
                                    RequestMessage.class
                            );
                    serverResponse =
                            clientCommand.execute(
                                    world,
                                    requestMessage.arguments,
                                    this
                            );

                    outputToClient(serverResponse);
                    if(clientCommand instanceof Quit){
                        this.closeEverything(socket, inputFromClient, outputToClient);
                    }
                } catch (IllegalArgumentException e) {
                    outputToClient(
                            new ErrorResponse(
                                    "Could not parse arguments"
                            )
                    );
                } catch (ClientCommand.CommandNotFoundException e) {
                    outputToClient(
                            new ErrorResponse(
                                    "Unsupported command"
                            )
                    );
                }
            }
            closeEverything(socket, inputFromClient, outputToClient);
        } catch (IOException e) {
            closeEverything(socket, inputFromClient, outputToClient);
        }
    }

    public static void broadcastMessage(String messageToBroadcast){
        for(ClientHandler clientHandler : users) {
            clientHandler.outputToClient.println(messageToBroadcast);
        }
    }
}
