package br.com.iriscareapi.exception;

public class EntityRegisterException extends RuntimeException{

    public EntityRegisterException(String entityName, String excpMessage) {
        super("Error trying to register " + entityName + " entity: ".concat(excpMessage));
    }

    public EntityRegisterException(String message) {
        super(message);
    }
}
