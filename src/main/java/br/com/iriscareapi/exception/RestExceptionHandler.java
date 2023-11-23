package br.com.iriscareapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    private String exceptionClassName;

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorMessage> objectNotFoundException(ObjectNotFoundException exception) {

        exceptionClassName = ObjectNotFoundException.class.getName();
        String exMsg = exception.getMessage();

        if (Objects.isNull(exMsg)) exMsg = "Can't find in data bank";

        ErrorMessage errorMessage = new ErrorMessage(exceptionClassName.
                substring(exceptionClassName.lastIndexOf(".") + 1),
                exMsg);

        log.error(errorMessage.message());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(EntityRegisterException.class)
    public ResponseEntity<ErrorMessage> entityRegisterException(EntityRegisterException exception) {

        exceptionClassName = ObjectNotFoundException.class.getName();
        String exMsg = exception.getMessage();

        if (Objects.isNull(exMsg)) exMsg = "Can't find in data bank";

        ErrorMessage errorMessage = new ErrorMessage(exceptionClassName.
                substring(exceptionClassName.lastIndexOf(".") + 1),
                exMsg);

        log.error(errorMessage.message());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(DataUtilsException.class)
    public ResponseEntity<ErrorMessage> dataUtilsException(DataUtilsException exception) {

        exceptionClassName = ObjectNotFoundException.class.getName();
        String exMsg = exception.getMessage();

        if (Objects.isNull(exMsg)) exMsg = "Error trying to execute DataUtils method";

        ErrorMessage errorMessage = new ErrorMessage(exceptionClassName.
                substring(exceptionClassName.lastIndexOf(".") + 1),
                exMsg);

        log.error(errorMessage.message());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<List<ErrorMessage>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        List<ErrorMessage> errors = new ArrayList<>();
        e.getFieldErrors().forEach(v -> errors.add(new ErrorMessage(v.getField(), v.getDefaultMessage())));

        errors.forEach(ex -> log.error(ex.type() + " - " + ex.message()));
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(OAuth2AuthenticationProcessingException.class)
    public ResponseEntity<ErrorMessage> OAuth2AuthenticationProcessingHandler(OAuth2AuthenticationProcessingException e) {

        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorMessage("error", e.getMessage()));
    }

}
