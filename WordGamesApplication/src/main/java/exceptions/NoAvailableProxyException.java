package exceptions;

public class NoAvailableProxyException extends Exception{
    public NoAvailableProxyException(String context) {
        super(context);
    }
}
