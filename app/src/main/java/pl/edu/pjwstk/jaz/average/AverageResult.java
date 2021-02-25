package pl.edu.pjwstk.jaz.average;

public class AverageResult {
    public final String message;

    public AverageResult(String message){
        this.message = message;
    }

    public String getResult() {
        return message;
    }
}
