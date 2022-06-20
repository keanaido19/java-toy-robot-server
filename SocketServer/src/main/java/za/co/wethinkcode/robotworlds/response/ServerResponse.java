package za.co.wethinkcode.robotworlds.response;

import java.util.Map;

public class ServerResponse {
    String result;
    Map<String, Object> data;

    public ServerResponse(String result, Map<String, Object> data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
