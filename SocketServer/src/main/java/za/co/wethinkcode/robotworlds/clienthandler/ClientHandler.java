package za.co.wethinkcode.robotworlds.clienthandler;

import za.co.wethinkcode.robotworlds.RobotServer;
import za.co.wethinkcode.robotworlds.response.ServerResponseBuilder;
import za.co.wethinkcode.robotworlds.world.World;
import za.co.wethinkcode.robotworlds.world.objects.robots.Robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{
    private final BufferedReader inputFromClient;
    private final PrintStream outputToClient;
    private final ServerSocket serverSocket;
    private final String clientIpAddress;
    private final Socket socket;
    private final World world;

    private final List<Robot> robots = new ArrayList<>();
    private final ServerResponseBuilder responseBuilder =
            new ServerResponseBuilder(this);

    private Robot robot;

    public ClientHandler(RobotServer server) throws IOException {
        this.serverSocket = server.getServerSocket();
        this.socket = serverSocket.accept();

        clientIpAddress = socket.getInetAddress().getHostName();
        System.out.println("Client (" + clientIpAddress + ") Has Connected!");

        this.inputFromClient =
                new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
        this.outputToClient =
                new PrintStream(socket.getOutputStream(), true);

        this.world = server.getWorld();
    }

    public World getWorld() {
        return world;
    }

    public Robot getRobot(String robotName) {
        for (Robot r : robots) {
            if (robotName.equals(r.getName())) return r;
        }
        return robot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
        robots.add(robot);
    }

    public void closeEverything(
            Socket socket,
            BufferedReader inputFromClient,
            PrintStream outputToClient
    ) {
        for (Robot r : robots) {
            world.getRobots().remove(r);
        }
        System.out.println(
                "Client (" + clientIpAddress + ") Has Disconnected!"
        );
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

    @Override
    public void run() {
        String commandFromClient;
        try {
            while (
                    !serverSocket.isClosed() &&
                    (commandFromClient = inputFromClient.readLine()) != null
            ) {
                String jsonStringResponse =
                        responseBuilder
                                .getJsonStringResponse(commandFromClient);
                outputToClient.println(jsonStringResponse);
            }
            closeEverything(socket, inputFromClient, outputToClient);
        } catch (IOException e) {
            closeEverything(socket, inputFromClient, outputToClient);
        }
    }
}
