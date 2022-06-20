package za.co.wethinkcode.robotworlds.response;

import java.util.HashMap;

public class ErrorResponse extends ServerResponse {
    public ErrorResponse(String errorMessage) {
        super(
                "ERROR",
                new HashMap<String,Object>() {{put("message",errorMessage);}}
        );
    }
}
