package za.co.wethinkcode.robotworlds;

import io.javalin.Javalin;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.ReDocOptions;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import org.slf4j.simple.SimpleLogger;
import za.co.wethinkcode.robotworlds.arguments.ServerPortArgument;
import za.co.wethinkcode.robotworlds.serverconsole.ServerConsole;

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
        System.out.printf(
                "Listening on http://localhost:%d/%n",
                port
        );
        System.out.printf(
                "Check out ReDoc docs at http://localhost:%d/redoc%n",
                port
        );
        System.out.printf(
                "Check out Swagger UI docs at http://localhost:%d/swagger-ui%n",
                port
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
                .reDoc(new ReDocOptions("/redoc"))
                .defaultDocumentation(doc -> {
                    doc.json("500", ErrorResponse.class);
                    doc.json("503", ErrorResponse.class);
                });
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
