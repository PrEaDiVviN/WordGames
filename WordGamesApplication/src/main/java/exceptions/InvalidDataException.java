package exceptions;

public class InvalidDataException extends Exception{
    public  InvalidDataException(String context) {
        super(context);
    }
}
