package client.validation;

public class IllegalBookArgumentsException extends Exception{
    private final String message;
    public IllegalBookArgumentsException(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }
}
