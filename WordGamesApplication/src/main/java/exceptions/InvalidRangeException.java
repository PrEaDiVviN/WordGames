package exceptions;

public class InvalidRangeException extends Exception{
    public InvalidRangeException(String context) {
        super(context);
    }
}