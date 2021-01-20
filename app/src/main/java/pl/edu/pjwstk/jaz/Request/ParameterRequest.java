package pl.edu.pjwstk.jaz.Request;

public class ParameterRequest {

    private String parameterKey;
    private String parameterValue;

    public ParameterRequest(String parameterKey, String parameterValue) {
        this.parameterKey = parameterKey;
        this.parameterValue = parameterValue;
    }

    public ParameterRequest() {
    }

    public String getParameterKey() {
        return parameterKey;
    }

    public String getParameterValue() {
        return parameterValue;
    }
}
