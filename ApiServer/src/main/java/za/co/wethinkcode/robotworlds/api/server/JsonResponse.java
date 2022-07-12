package za.co.wethinkcode.robotworlds.api.server;

import java.util.Map;

public class JsonResponse {
    private String result;
    private Map<String, Object> data;
    private Map<String, Object> state;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getState() {
        return state;
    }

    public void setState(Map<String, Object> state) {
        this.state = state;
    }
}
