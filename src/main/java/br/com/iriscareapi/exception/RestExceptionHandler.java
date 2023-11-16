package br.com.iriscareapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
@ResponseStatus
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private String exceptionClassName;

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorMessage> objectNotFoundException(ObjectNotFoundException exception) {

        exceptionClassName = ObjectNotFoundException.class.getName();
        String exMsg = exception.getMessage();

        if(Objects.isNull(exMsg))  exMsg = "Can't find in data bank";

        ErrorMessage errorMessage = new ErrorMessage(exceptionClassName.
                substring(exceptionClassName.lastIndexOf(".") + 1),
                exMsg);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(EntityRegisterException.class)
    public ResponseEntity<ErrorMessage> entityRegisterException(EntityRegisterException exception) {

        exceptionClassName = ObjectNotFoundException.class.getName();
        String exMsg = exception.getMessage();

        if(Objects.isNull(exMsg))  exMsg = "Can't find in data bank";

        ErrorMessage errorMessage = new ErrorMessage(exceptionClassName.
                substring(exceptionClassName.lastIndexOf(".") + 1),
                exMsg);

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }
}
