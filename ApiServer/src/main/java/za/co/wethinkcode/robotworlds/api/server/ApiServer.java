package za.co.wethinkcode.robotworlds.api.server;

import io.javalin.Javalin;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import org.slf4j.simple.SimpleLogger;
import za.co.wethinkcode.robotworlds.cli.CommandLineArgumentHandler;
import za.co.wethinkcode.robotworlds.cli.arguments.ServerPortArgument;
import za.co.wethinkcode.robotworlds.domain.Play;
import za.co.wethinkcode.robotworlds.api.server.console.ServerConsole;

import java.text.MessageFormat;

import static io.javalin.apibuilder.ApiBuilder.*;

public class ApiServer {
    private final Javalin server;
    private final int port;

    public ApiServer(int port) {
        System.setProperty(SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");
        this.port = port;
        this.server = Javalin.create(
                config -> {
                    config.registerPlugin(getConfiguredOpenApiPlugin());
                    config.defaultContentType = "application/json";
                }
        ).routes(
                () -> {
                    path(
                            "world",
                            () -> {
                                get(ApiHandler::getWorld);
                                path(
                                        "{worldName}",
                                        () -> get(ApiHandler::getWorldByName)
                                );
                            }
                    );
                    path(
                            "robot",
                            () -> post(ApiHandler::getRobotResponse)
                    );
                }
        );
    }

    public void start(String[] args) {
        Play.start(args);
        this.server.start(port);
        System.out.println(
                MessageFormat.format(
                        "Welcome to Robot Worlds Server!\n" +
                                "Server is listening on...\n" +
                                "\tAddress\t\t:\thttp://localhost:{0}/\n" +
                                "\tReDocs\t\t:\thttp://localhost:{0}/redoc\n" +
                                "\tSwagger UI\t:\thttp://localhost:{0}/" +
                                "swagger-ui",
                        port + ""
                )
        );
        new ServerConsole(this).start();
    }

    public void stop() {
        ApiHandler.stop();
        Play.setWorld(null);
        this.server.stop();
    }

    private static OpenApiPlugin getConfiguredOpenApiPlugin() {
        Info info =
                new Info()
                        .version("1.0")
                        .title("Robot Worlds API")
                        .description("...");

        OpenApiOptions options = new OpenApiOptions(info)
                .activateAnnotationScanningFor(
                        "za.co.wethinkcode.robotworlds"
                )
                .path("/swagger-docs")
                .swagger(new SwaggerOptions("/swagger-ui"))
                .reDoc(new ReDocOptions("/redoc"));
        return new OpenApiPlugin(options);
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
