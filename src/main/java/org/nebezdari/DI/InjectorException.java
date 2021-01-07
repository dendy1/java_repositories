package org.nebezdari.DI;

public class InjectorException extends Exception {

    public InjectorException(Throwable cause) {
        super(cause);
    }

    public InjectorException(String message) {
        super(message);
    }
}
