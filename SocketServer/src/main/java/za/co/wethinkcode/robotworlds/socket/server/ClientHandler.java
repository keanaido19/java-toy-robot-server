package za.co.wethinkcode.robotworlds.socket.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import za.co.wethinkcode.robotworlds.domain.Play;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final BufferedReader inputFromClient;
    private final PrintStream outputToClient;
    private final ServerSocket serverSocket;
    private final String clientIpAddress;
    private final Socket socket;
    private final Play play;

    public ClientHandler(ServerSocket serverSocket)
            throws IOException {
        this.serverSocket = serverSocket;
        this.socket = serverSocket.accept();

        clientIpAddress = socket.getInetAddress().getHostName();
        System.out.println("Client (" + clientIpAddress + ") Has Connected!");

        this.inputFromClient =
                new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
        this.outputToClient =
                new PrintStream(socket.getOutputStream(), true);

        play = new Play();
    }

    public void closeEverything(
            Socket socket,
            BufferedReader inputFromClient,
            PrintStream outputToClient
    ) {
        play.stop();
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
                JsonNode jsonRequest = objectMapper.readTree(commandFromClient);
                String jsonStringResponse =
                        play.getJsonStringResponse(jsonRequest);
                outputToClient.println(jsonStringResponse);
            }
            closeEverything(socket, inputFromClient, outputToClient);
        } catch (IOException e) {
            closeEverything(socket, inputFromClient, outputToClient);
        }
    }
}
