package za.co.wethinkcode.robotworlds;

import io.javalin.Javalin;
import za.co.wethinkcode.robotworlds.arguments.ServerPortArgument;
import za.co.wethinkcode.robotworlds.serverconsole.ServerConsole;

public class ApiServer {
    private final Javalin server;
    private final int port;

    public ApiServer(int port) {
        this.port = port;
        this.server = Javalin.create(
                config -> config.defaultContentType = "application/json"
        );

        this.server.get("/world", ApiHandler::getWorld);

        this.server.get("/world/{worldName}", ApiHandler::getWorldByName);

        this.server.post("/robot", ApiHandler::getRobotResponse);
    }

    public void start(String[] args) {
        Play.start(args);
        this.server.start(port);
        new ServerConsole(this).start();
    }

    public void stop() {
        ApiHandler.stop();
        Play.setWorld(null);
        this.server.stop();
    }

    public static void main(String[] args) {

        CommandLineArgumentHandler CLIHandler =
                new CommandLineArgumentHandler(args);

        int serverPortNumber =
                (int) CLIHandler.getArgumentValue(new ServerPortArgument());

        ApiServer server = new ApiServer(serverPortNumber);

        server.start(args);
    }
}
