package exceptions;

public class InvalidConfigurationException extends Exception{
    public  InvalidConfigurationException(String context) {
        super(context);
    }
}
