package za.co.wethinkcode.robotworlds;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.plugin.openapi.annotations.*;
import za.co.wethinkcode.robotworlds.databaseconnectors.DbConnector;
import za.co.wethinkcode.robotworlds.databaseconnectors.ORMLiteDbConnector;
import za.co.wethinkcode.robotworlds.dbobjects.WorldDbObject;
import za.co.wethinkcode.robotworlds.world.WorldDbObjectConverter;

import java.sql.SQLException;

public class ApiHandler {
    private static final DbConnector dbConnector;
    private static final Play play = new Play();

    static {
        try {
            dbConnector = new ORMLiteDbConnector();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @OpenApi(
            summary = "Get the current world",
            operationId = "getWorld",
            path = "/world",
            method = HttpMethod.GET,
            tags = {"World"},
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            content = {
                                    @OpenApiContent(
                                            from = WorldDbObject.class
                                    )
                            }
                    )
            }
    )
    public static void getWorld(Context context) {
        context.json(WorldDbObjectConverter.getWorldDbObject(Play.getWorld()));
    }

    @OpenApi(
            summary = "Get world by name",
            operationId = "getWorldByName",
            path = "/world/:worldName",
            method = HttpMethod.GET,
            pathParams = {
                    @OpenApiParam(
                            name = "worldName",
                            description = "The name of the world"
                    )
            },
            tags = {"World"},
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            content = {
                                    @OpenApiContent(
                                            from = WorldDbObject.class
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "404",
                            content = {
                                    @OpenApiContent(
                                            from = ErrorResponse.class
                                    )
                            }
                    )
            }
    )
    public static void getWorldByName(Context context) {
        String worldName =
                context.pathParamAsClass("worldName", String.class).get();
        try {
            WorldDbObject worldDb = dbConnector.restoreWorld(worldName);
            Play.setWorld(WorldDbObjectConverter.getWorld(worldDb));
            getWorld(context);
        } catch (SQLException e) {
            throw new NotFoundResponse(
                    "World not found: '" + worldName + "'");
        }
    }

    @OpenApi(
            summary = "Operate a robot",
            operationId = "operateRobot",
            path = "/robot",
            method = HttpMethod.POST,
            tags = {"Robot"},
            requestBody = @OpenApiRequestBody(
                    content = {@OpenApiContent(from = JsonRequest.class)}
            ),
            responses = {
                    @OpenApiResponse(
                            status = "200",
                            content = {
                                    @OpenApiContent(
                                            from = JsonResponse.class
                                    )
                            }
                    ),
                    @OpenApiResponse(
                            status = "400",
                            content = {
                                    @OpenApiContent(
                                            from = ErrorResponse.class
                                    )
                            }
                    )
            }
    )
    public static void getRobotResponse(Context context) {

        try {
            JsonRequest jsonRequest = context.bodyAsClass(JsonRequest.class);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStringRequest =
                    objectMapper.writeValueAsString(jsonRequest);

            String jsonStringResponse =
                    play.getJsonStringResponse(
                            objectMapper.readTree(jsonStringRequest)
                    );

            context.header("Location", "/robot");
            context.json(jsonStringResponse);

        } catch (Exception e) {
            throw new BadRequestResponse();
        }
    }

    public static void stop() {
        play.stop();
    }
}
