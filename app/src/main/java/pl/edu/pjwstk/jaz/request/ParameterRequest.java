package pl.edu.pjwstk.jaz.request;

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
