package exceptions;

public class NoWordException extends Exception{
    public NoWordException(String context) {
        super(context);
    }
}