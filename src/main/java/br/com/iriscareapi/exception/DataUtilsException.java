package br.com.iriscareapi.exception;

public class DataUtilsException extends RuntimeException{

    public DataUtilsException(String methodName, Exception e) {
        super("Error trying to execute method " +
                methodName + " " +
                 e.getMessage());
    }

    public DataUtilsException(String message) {
        super(message);
    }
}
