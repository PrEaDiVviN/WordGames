package exceptions;

public class NoSynonymException extends Exception{
    public NoSynonymException(String context) {
        super(context);
    }
}
